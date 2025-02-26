/*
 * 배열로 만든 덱 기본 형식 구현은 기초적인 배열 삽입/삭제 구조로 되어있으나 출력 시 위치를 변경하여 노출되도록 함.
 */

class ArrayDeque {
    private int[] deque;
    private int front, rear, size, capacity;

    // 생성자: 덱의 초기 크기를 설정
    public ArrayDeque(int capacity) {
        this.capacity = capacity;
        deque = new int[capacity];
        front = -1;
        rear = -1;
        size = 0;
    }

    // 덱이 비어있는지 확인
    public boolean isEmpty() {
        return size == 0;
    }

    // 덱이 꽉 찼는지 확인
    public boolean isFull() {
        return size == capacity;
    }

    // 덱의 앞에 삽입
    public void insertFront(int value) {
        if (isFull()) {
            System.out.println("Deque is full!");
            return;
        }
        if (front == -1) { // 처음 삽입 시
            front = rear = 0;
        } else if (front == 0) { // 앞이 배열의 처음이면
            front = capacity - 1;

            System.out.println("---------------" + capacity);
        } else {
            front--;
        }
        deque[front] = value;
        size++;
    }

    // 덱의 뒤에 삽입
    public void insertRear(int value) {
        if (isFull()) {
            System.out.println("Deque is full!");
            return;
        }
        if (rear == -1) { // 처음 삽입 시
            front = rear = 0;
        } else if (rear == capacity - 1) { // 뒤가 배열의 끝이면
            rear = 0;
        } else {
            rear++;
        }
        deque[rear] = value;
        size++;
    }

    // 덱의 앞에서 삭제
    public void deleteFront() {
        if (isEmpty()) {
            System.out.println("Deque is empty!");
            return;
        }
        if (front == rear) { // 단 하나의 요소만 있으면
            front = rear = -1;
        } else if (front == capacity - 1) { // 앞이 배열의 끝이면
            front = 0;
        } else {
            front++;
        }
        size--;
    }

    // 덱의 뒤에서 삭제
    public void deleteRear() {
        if (isEmpty()) {
            System.out.println("Deque is empty!");
            return;
        }
        if (front == rear) { // 단 하나의 요소만 있으면
            front = rear = -1;
        } else if (rear == 0) { // 뒤가 배열의 처음이면
            rear = capacity - 1;
        } else {
            rear--;
        }
        size--;
    }

    // 덱의 앞에 있는 요소 반환
    public int getFront() {
        if (isEmpty()) {
            System.out.println("Deque is empty!");
            return -1;
        }
        return deque[front];
    }

    // 덱의 뒤에 있는 요소 반환
    public int getRear() {
        if (isEmpty()) {
            System.out.println("Deque is empty!");
            return -1;
        }
        return deque[rear];
    }

    // 덱의 크기 반환
    public int getSize() {
        return size;
    }

    // 덱 출력 (배열에는 데이터 위치를 이동 시키는 것이 어렵기 때문에 출력 시 덱의 구조처럼 출력되게끔 처리한다.)
    public void display() {
        if (isEmpty()) {
            System.out.println("Deque is empty!");
            return;
        }
        System.out.print("Deque elements: ");
        if (front <= rear) {
            for (int i = front; i <= rear; i++) {
                System.out.print(deque[i] + " ");
            }
        } else {
            for (int i = front; i < capacity; i++) {
                System.out.print(deque[i] + " ");
            }
            for (int i = 0; i <= rear; i++) {
                System.out.print(deque[i] + " ");
            }
        }
        System.out.println();
    }
}


public class ArrayDequeTest {
    public static void main(String[] args) {
        ArrayDeque arrDeque = new ArrayDeque(5);


        arrDeque.insertFront(90);
        arrDeque.insertRear(10);
        arrDeque.insertRear(20);
        arrDeque.insertFront(5);
        arrDeque.insertRear(30);
        arrDeque.insertFront(2);

        arrDeque.display(); // 출력: 2 5 10 20 30

        arrDeque.deleteFront();
        arrDeque.deleteRear();

        arrDeque.display(); // 출력: 5 10 2

    }
}

