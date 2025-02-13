# RDBMS 스터디

안녕하세요 김도겸입니다.  
당월 2주차는 RDBMS에서의 데이터 복원 작업에 대해 설명드리겠습니다.

<br>

## 🙏🏻 데이터를 돌려주세요..

개발을 하다보면 실수, 오류 등으로 인해 의도치 않게 다양한 문제가 발생합니다.  
다양한 결함들과 문제들을 마주하다 보면 별 거 아닌 간단히 해결하는 경우들도 있지만, 마주하였을 때 멘붕과 식은땀이 흐를 정도의 큰 문제들을 몇 번 경험해보셨을 겁니다.  
그 중 자주 접할 수 있는 것이 바로 DB 데이터와 관련된 문제들입니다.

<br>
<img src='./images/db_mistake.jpg'/>
<img src='./images/db_delete_meme.jpg'/>

~~정말 끔찍한 순간입니다.~~

<br>
잘못된 코드, 기능의 오류 등으로 인해 잘못된 형태의 데이터가 삽입, 수정, 삭제가 될 경우 보통은 이미 Commit으로 트랜잭션이 종료가 됩니다.  
트랜잭션이 종료가 되었을 경우 트랜잭션의 특성에 의해 데이터가 보장되어 있기 때문에 고정된 데이터가 아닌 이상 계속해서 관리가 되고 갱신이 되는 데이터들은 사라지면 단순한 쿼리로는 복구가 매우 어렵습니다.  
하지만 이러한 문제를 해결하기 위해 주요 RDBMS에선 특정 시점으로 돌아가 데이터를 복구해주는 기능들이 있습니다.  
<br>
그 중 대표적인 RDBMS인 Oracle과 MySQL에서 자주 사용되는 데이터 복구 기능을 알아보도록 하겠습니다.

<br><br>

## ✅ **Oracle에서의 데이터 복구 기능**

Oracle에서는 **Flashback** 기능을 활용하여 특정 과거 시점으로 돌아가서 데이터를 조회할 수 있으며 이를 통해 데이터를 복구하거나 변경 사항을 분석할 수 있습니다.

### 🛠️ **Flashback 작동 방식**

그렇다면 Oracle Database엔 해당 복구 데이터를 어디에, 어떻게 보존했을까요?  
답은 생각보다 간단합니다.

Oracle Database는 트랜잭션이 변경을 수행할 때 UNDO 데이터를 생성하여 이전 상태를 임시 저장소에 저장합니다. &nbsp;Flashback Query는 이러한 UNDO 데이터를 참조하여 과거 데이터를 조회하게 되는 것이죠.  
하지만 당연히 모든 데이터를 복원 가능한 것은 아닙니다. &nbsp;어디까지나 임시 저장소에 가지고 있는 데이터에 한해서 복구가 가능한 것이며 시스템의 설정, 버퍼의 크기에 따라 복구할 수 있는 시간은 제한적입니다.

> 기본값은 900초(15분)로 설정되어 있습니다.

### 📌 **실행 방법**

1. **테이블 데이터 복구 (과거 시점 조회)**

   삭제된 데이터를 특정 시점의 상태로 조회할 수 있습니다.

   ```sql
   SELECT * FROM employees AS OF TIMESTAMP (SYSTIMESTAMP - INTERVAL '10' MINUTE);
   ```

   TIMESTAMP를 활용하여 10분 전의 데이터를 조회합니다.

   ```sql
   SELECT * FROM employees AS OF SCN 12345678;
   ```

   특정 SCN을 가져와 과거 데이터를 조회합니다.

   ***

2. **삭제된 데이터 복원 (INSERT INTO 활용)**

   과거 데이터를 현재 테이블로 복원할 수 있습니다.

   ```sql
   INSERT INTO employees
   SELECT * FROM employees AS OF TIMESTAMP (SYSTIMESTAMP - INTERVAL '10' MINUTE)
   WHERE employee_id = 101;
   ```

   10분 전 데이터 중 특정 행을 복원합니다.

   ***

3. **변경된 데이터 비교 (MINUS 연산 활용)**

   과거 데이터와 현재 데이터를 비교하여 변경된 사항을 확인할 수 있습니다.

   ```sql
   SELECT * FROM employees
   MINUS
   SELECT * FROM employees AS OF TIMESTAMP (SYSTIMESTAMP - INTERVAL '10' MINUTE');
   ```

   10분 동안 변경된 데이터를 확인합니다.

   ***

4. **Flashback Version Query (변경 이력 확인)**

   데이터의 변경 이력을 추적할 수 있습니다.

   ```sql
   SELECT versions_starttime, versions_endtime, versions_xid, versions_operation, employee_id, salary
   FROM employees VERSIONS BETWEEN TIMESTAMP (SYSTIMESTAMP - INTERVAL '30' MINUTE') AND SYSTIMESTAMP
   WHERE employee_id = 101;
   ```

   특정 행의 30분 동안의 변경 내역을 확인할 수 있습니다.

   ***

5. **Flashback Transaction Query (트랜잭션 로그 확인)**

   과거 트랜잭션을 조회하여 원인을 분석할 수 있습니다.

   ```sql
   SELECT start_scn, commit_scn, operation, logon_user, undo_sql
   FROM flashback_transaction_query
   WHERE xid = '0008000A00000001';
   ```

   특정 트랜잭션 ID에 대한 상세 정보를 확인합니다.

   ***

<br><br>

## ✅ **MySQL에서의 데이터 복구 기능**

MySQL에는 flashback 기능은 없지만, **바이너리 로그**를 활용하여 데이터 복구를 진행할 수 있습니다.  
MySQL의 **Binary Log (BINLOG)**는 데이터 변경 사항을 기록하는 로그 파일입니다. 특정 시점의 데이터를 복구하려면 Binary Log를 사용하여 **과거 시점까지 복구**할 수 있습니다.

> 데이터 변경사항은 트랜잭션이 있는 경우엔 트랜잭션이 커밋된 시점, 없는 경우엔 DML 문이 실행될 때 마다 즉시 바이너리 로그에 기록됩니다.

<br>

### 📌 **실행 방법**

1. **Binary Log 활성화 확인**

   ```sql
   SHOW VARIABLES LIKE 'log_bin';
   ```

   `ON`이면 활성화된 상태입니다.

2. **특정 시점까지의 데이터 복구 (Point-in-Time Recovery, PITR)**

   ```sh
   mysqlbinlog --stop-datetime="2024-01-31 12:00:00" /var/log/mysql/binlog.000001 | mysql -u root -p
   ```

   서버 상에서 위 커맨드를 통해 **2024-01-31 12:00:00 이전의 상태**로 데이터베이스를 복원합니다.

💡 **한계점:**

- Binary Log가 활성화되어 있어야 함
- Flashback Query처럼 단순히 쿼리 호출하여 복구가 불가능함. (복구를 위해 DB에 반영해야 함)

---

<br>

### 🌟 Temporary Table 등장!

MySQL 8.0부터 **Temporary Tables**를 사용하여 특정 시점의 데이터를 조회할 수 있습니다.  
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

<br>

### **💡 마무리 **

지금까지 데이터를 복원하는 방법을 알아보았습니다.  
실수를 한 개발자들의 맘을 비교적 가볍게 해주는 유용한 기능들을 소개시켜드렸는데, 제일 중요한 것은 위 기능들을 되도록이면 사용하지 않는 것이 가장 좋은 것 같습니다 😅

감사합니다.
