# 자료구조 개념 스터디

안녕하세요 김도겸입니다.  
이번 달 스터디는 해싱(Hashing)과 해시 테이블(Hash Table)를 공부하고 간단한 코드 예제로 구현해본 내용을 정리해보았습니다.

- **해싱(Hashing)**
  - 개념 및 구조
  - 장점 및 단점
  - 예제
- **해시 테이블(Hash Table)**
  - 개념 및 구조
  - 장점 및 단점
  - 예제

<br>

## 1. 해싱(Hashing) 개념 및 원리

해싱(Hashing)은 데이터를 일정한 규칙에 따라 변환하여, 효율적으로 저장하고 검색할 수 있도록 하는 기법이다. 해싱의 핵심 개념은 **해시 함수(Hash Function)**를 이용하여 키(key)를 고정된 크기의 해시 값(hash value)으로 변환하는 것이다.

### 1.1 해싱 구조적 특징

- **해시 함수(Hash Function)**: 키를 해시 값으로 변환하는 함수.
- **해시 테이블(Hash Table)**: 해시 함수를 이용해 저장된 데이터를 담는 자료구조.
- **충돌(Collision)**: 서로 다른 키가 같은 해시 값을 가질 때 발생.
- **충돌 해결 방법**: 체이닝(Chaining), 개방 주소법(Open Addressing) 등.

## 2. 해시 테이블(Hash Table)의 개념, 구조, 원리

### 2.1 해시 테이블 개념

해시 테이블은 **키(Key)**를 해시 함수로 변환하여, 배열의 인덱스로 매핑하는 자료구조이다. 이를 통해 데이터의 저장과 검색 속도를 획기적으로 향상시킬 수 있다.

### 2.2 해시 테이블의 구조

- **버킷(Bucket)**: 데이터를 저장하는 공간.
- **슬롯(Slot)**: 버킷 내 개별 저장 위치.
- **해시 함수(Hash Function)**: 키를 특정 인덱스로 매핑하는 함수.
- **충돌 해결 전략(Collision Resolution)**: 같은 해시 값을 가지는 키들의 처리 방법.

### 2.3 해시 테이블 원리

1. 키를 해시 함수에 적용하여 해시 값을 얻음.
2. 해시 값을 이용해 배열(버킷) 내 저장 위치 결정.
3. 동일한 해시 값이 존재하면 충돌 해결 기법 적용.

## 3. 해시 테이블 사용 예시

### 3.1 해시 테이블이 사용되는 곳

- **데이터베이스 인덱싱**: 빠른 조회를 위해 사용됨.
- **캐싱 시스템**: 웹 페이지, DNS 캐싱 등에 활용됨.
- **컴파일러**: 식별자(Symbol Table) 저장.
- **암호화 및 보안**: 해시 기반 데이터 저장 및 인증.

## 4. 장단점

### 4.1 장점

- **빠른 검색 속도(O(1))**: 대부분의 경우 일정한 시간 내 검색 가능.
- **효율적인 공간 활용**: 적절한 해시 함수와 충돌 해결 기법 적용 시 효율적.
- **유연한 키 저장**: 다양한 키 형식 지원.

### 4.2 단점

- **충돌 가능성**: 해시 함수에 따라 충돌 발생 가능.
- **메모리 사용량**: 큰 해시 테이블은 메모리를 많이 차지할 수 있음.
- **해시 함수 성능 의존성**: 좋은 해시 함수를 설계하는 것이 중요.

## 5. 해시 테이블 Java 구현 예제

```java
import java.util.LinkedList;

class HashTable<K, V> {
    private static class Entry<K, V> {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private LinkedList<Entry<K, V>>[] table;
    private int size;

    public HashTable(int capacity) {
        table = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new LinkedList<>();
        }
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % table.length;
    }

    public void put(K key, V value) {
        int index = hash(key);
        for (Entry<K, V> entry : table[index]) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }
        table[index].add(new Entry<>(key, value));
        size++;
    }

    public V get(K key) {
        int index = hash(key);
        for (Entry<K, V> entry : table[index]) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    public void remove(K key) {
        int index = hash(key);
        table[index].removeIf(entry -> entry.key.equals(key));
    }

    public int size() {
        return size;
    }
}

public class HashTableExample {
    public static void main(String[] args) {
        HashTable<String, Integer> hashTable = new HashTable<>(10);
        hashTable.put("apple", 10);
        hashTable.put("banana", 20);
        hashTable.put("orange", 30);

        System.out.println("apple: " + hashTable.get("apple")); // 10
        System.out.println("banana: " + hashTable.get("banana")); // 20

        hashTable.remove("banana");
        System.out.println("banana: " + hashTable.get("banana")); // null
    }
}
```

### 5.1 코드 설명

- `hash()` 메서드: 키의 해시 값을 계산하여 인덱스를 결정.
- `put()` 메서드: 키-값을 저장하며, 충돌 시 체이닝(LinkedList) 기법 사용.
- `get()` 메서드: 키를 이용해 값을 검색.
- `remove()` 메서드: 특정 키를 삭제.

## 6. 결론

해싱과 해시 테이블은 데이터 저장 및 검색의 효율성을 극대화하는 핵심 자료구조이다. 다양한 충돌 해결 기법과 해시 함수 최적화를 통해 성능을 높일 수 있으며, 여러 분야에서 광범위하게 활용되고 있다.
