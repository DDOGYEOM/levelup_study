# RDBMS 스터디

안녕하세요 김도겸입니다.  
이번 달 2주차는 데이터 복구 작업을 위한 RDBMS의 여러가지 기능들을 설명드리겠습니다.

<br>

## 데이터를 돌려주세요..

개발을 하다보면 실수, 오류 등으로 인해 의도치 않게 다양한 문제가 발생합니다.  
다양한 결함들과 문제들을 마주하다 보면 별 거 아닌 간단히 해결하는 경우들도 있지만, 마주하였을 때 멘붕이 와버리고 등에 식은땀이 흐를 정도의 큰 문제들을 몇 번 경험해보셨을 겁니다.  
그 중 자주 접할 수 있는 것이 바로 DB 데이터와 관련된 문제들입니다.  
잘못된 코드, 기능의 오류 등으로 인해 잘못된 형태의 데이터가 삽입, 수정, 삭제가 될 경우 보통은 이미 Commit으로 트랜잭션이 종료가 됩니다.  
트랜잭션이 종료가 되었을 경우 트랜잭션의 특성에 의해 데이터가 보장되어 있기 때문에 고정된 데이터가 아닌 이상 계속해서 관리가 되고 갱신이 되는 데이터들은 사라지면 단순한 쿼리로는 복구가 매우 어렵습니다.  
하지만 이러한 문제를 해결하기 위해 주요 RDBMS에선 특정 시점으로 돌아가 데이터를 복구해주는 기능들이 있습니다.  
그 중 대표적인 RDBMS인 Oracle과 MySQL의 데이터 복구 기능을 알아보도록 하겠습니다.

## ✅ **Oracle에서의 데이터 복구 기능**

Oracle에서는 **Flashback** 기능을 활용하여 특정 시점으로 돌아가거나 조회해줍니다.

## 🛠️ **Flashback 작동 방식**

## ✅ **MySQL에서의 데이터 복구 기능**

MySQL에는 **Oracle의 Flashback Query와 완전히 동일한 기능**은 없지만, 유사한 기능을 구현할 수 있는 몇 가지 방법이 있습니다.

1. **BINLOG (Binary Log) 활용**
2. **Point-in-Time Recovery (PITR)**
3. **Temporal Tables (InnoDB의 SYSTEM VERSIONING)**
4. **Trigger & Audit Log 활용**

## 🔹 **1. Binary Log을 이용한 데이터 복구**

MySQL의 **Binary Log (BINLOG)**는 데이터 변경 사항을 기록하는 로그 파일입니다. 특정 시점의 데이터를 복구하려면 Binary Log를 사용하여 **과거 시점까지 복구**할 수 있습니다.

### **📌 실행 방법**

1. **Binary Log 활성화 확인**

   ```sql
   SHOW VARIABLES LIKE 'log_bin';
   ```

   `ON`이면 활성화된 상태입니다.

2. **특정 시점까지의 데이터 복구 (Point-in-Time Recovery, PITR)**
   ```sh
   mysqlbinlog --stop-datetime="2024-01-31 12:00:00" /var/log/mysql/binlog.000001 | mysql -u root -p
   ```
   - 이 명령은 **2024-01-31 12:00:00 이전의 상태**로 데이터베이스를 복원합니다.

**💡 한계점:**

- Binary Log가 활성화되어 있어야 함
- 세밀한 Flashback Query처럼 단순 조회만 하는 기능은 아님 (복구를 위해 DB에 반영해야 함)

---

## 🔹 **2. Point-in-Time Recovery (PITR)**

MySQL의 **백업 + Binary Log**를 조합하여 특정 시점으로 복원하는 방법입니다.

### **📌 실행 방법**

1. **백업을 먼저 생성**
   ```sh
   mysqldump -u root -p --single-transaction --routines --triggers --all-databases > backup.sql
   ```
2. **이후 특정 시점까지의 로그를 적용하여 복구**
   ```sh
   mysqlbinlog --start-datetime="2024-01-30 00:00:00" --stop-datetime="2024-01-31 12:00:00" binlog.000001 | mysql -u root -p
   ```

**💡 한계점:**

- 사전 백업이 필요함
- Flashback Query처럼 간단히 조회하는 방식이 아님 (복구 과정 필요)

---

## 🔹 **3. InnoDB Temporal Tables 활용 (MySQL 8.0+ 지원)**

MySQL 8.0부터 **Temporal Tables**를 사용하여 특정 시점의 데이터를 조회할 수 있습니다.  
이 기능을 사용하면 Oracle의 Flashback Query처럼 **과거 데이터를 조회할 수 있음**.

### **📌 실행 방법**

1. **시스템 버전 테이블 생성**
   ```sql
   CREATE TABLE employees (
       id INT PRIMARY KEY,
       name VARCHAR(50),
       salary INT,
       start_time TIMESTAMP(6) GENERATED ALWAYS AS ROW START,
       end_time TIMESTAMP(6) GENERATED ALWAYS AS ROW END,
       PERIOD FOR SYSTEM_TIME(start_time, end_time)
   ) WITH SYSTEM VERSIONING;
   ```
2. **과거 시점의 데이터 조회**
   ```sql
   SELECT * FROM employees FOR SYSTEM_TIME AS OF TIMESTAMP '2024-01-31 12:00:00';
   ```

**💡 장점:**

- Flashback Query와 유사한 방식으로 과거 데이터 조회 가능
- 특정 시점 데이터를 손쉽게 복구할 수 있음

**💡 한계점:**

- 테이블을 처음부터 **SYSTEM VERSIONING**이 활성화된 상태로 만들어야 함
- 기존 테이블에는 적용하기 어려움

---

## 🔹 **4. 트리거 & 감사 로그(Audit Log) 활용**

테이블 변경 사항을 별도의 **Audit Log 테이블**에 저장해두고, 필요할 때 과거 데이터를 조회하는 방식입니다.

### **📌 실행 방법**

1. **감사 로그 테이블 생성**
   ```sql
   CREATE TABLE employees_audit (
       id INT,
       name VARCHAR(50),
       salary INT,
       action_type VARCHAR(10),
       action_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
   );
   ```
2. **트리거를 사용하여 변경 사항 기록**
   ```sql
   CREATE TRIGGER employees_after_update
   AFTER UPDATE ON employees
   FOR EACH ROW
   INSERT INTO employees_audit (id, name, salary, action_type)
   VALUES (OLD.id, OLD.name, OLD.salary, 'UPDATE');
   ```
3. **과거 데이터 조회**
   ```sql
   SELECT * FROM employees_audit WHERE action_time <= '2024-01-31 12:00:00';
   ```

**💡 장점:**

- 특정 테이블에서 변경된 데이터를 직접 확인 가능
- Flashback Query처럼 특정 시점의 데이터 조회 가능

**💡 한계점:**

- 트리거를 설정해야 하며, 기존 데이터는 보호되지 않음
- 성능 이슈 발생 가능 (Audit Log 크기 증가)

---

## 🚀 **정리: MySQL에서 Flashback과 유사한 기능**

| 방법                                    | Flashback Query처럼 조회 가능? | 특징                                            |
| --------------------------------------- | ------------------------------ | ----------------------------------------------- |
| **Binary Log**                          | ❌ (복구는 가능)               | 과거 상태로 복원할 수 있지만 단순 조회는 불가능 |
| **Point-in-Time Recovery (PITR)**       | ❌ (복구는 가능)               | 백업 + Binary Log로 특정 시점으로 복구 가능     |
| **Temporal Tables (SYSTEM VERSIONING)** | ✅                             | MySQL 8.0 이상에서 특정 시점 데이터 조회 가능   |
| **Trigger & Audit Log**                 | ✅                             | 직접 변경 사항을 기록하여 조회 가능             |

---

### **💡 결론**

- **Flashback Query와 가장 유사한 기능은 MySQL 8.0의 SYSTEM VERSIONING (Temporal Tables)**
- **MySQL 5.x에서는 Binary Log + 백업(PITR) 방식이 대안**
- **트리거 & Audit Log를 활용하여 데이터 변경 이력을 수동으로 관리하는 방법도 가능**

👉 **MySQL에서 Oracle Flashback Query처럼 특정 시점의 데이터를 조회하고 싶다면, MySQL 8.0의 SYSTEM VERSIONING을 사용하는 것이 가장 효과적입니다!** 🚀
