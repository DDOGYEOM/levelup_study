class CircularDeque {
    public Integer[] deque;
    private int front, rear, size;

    public CircularDeque(int size) {
        this.size = size;
        deque = new Integer[size];
        front = -1;
        rear = -1;
    }

    public boolean isFull() {
        return (rear + 1) % size == front;
    }

    public boolean isEmpty() {
        return front == -1;
    }



    // 앞에서 삽입
    public void insertFront(int value) {
        if (isFull()) {
            System.out.println("덱이 꽉 찼습니다.");
            return;
        }

        if (isEmpty()) {
            front = 0;
            rear = 0;
        } else {
            front = (front - 1 + size) % size; // 현재 front index를 기준으로 한 칸 앞 index로 설정
        }

        System.out.println("insert front --> " + value);

        deque[front] = value;
    }

    // 뒤에서 삽입
    public void insertRear(int value) {
        if (isFull()) {
            System.out.println("덱이 꽉 찼습니다.");
            return;
        }

        if (isEmpty()) {
            front = 0;
            rear = 0;
        } else {
            rear = (rear + 1) % size;
        }

        System.out.println("insert rear --> " + value);
        deque[rear] = value;
    }

    // 앞에서 삭제
    public void popFront() {
        if (isEmpty()) {
            System.out.println("덱이 비어 있습니다.");
            return;
        }

        deque[front] = null;

        System.out.println("pop front --> " + deque[front]);

        if (front == rear) { // 마지막 요소 삭제
            front = -1;
            rear = -1;
        } else {
            front = (front + 1) % size;
        }
    }

    // 뒤에서 삭제
    public void popRear() {
        if (isEmpty()) {
            System.out.println("덱이 비어 있습니다.");
            return;
        }


        deque[rear] = null;

        System.out.println("pop rear --> " + deque[rear]);

        if (front == rear) { // 마지막 요소 삭제
            front = -1;
            rear = -1;
        } else {
            rear = (rear - 1 + size) % size; // 현재 rear index를 기준으로 한 칸 뒤 index를 설정
        }

    }


    public String toString() {
        String valueStr = "[ ";

        for (int i = 0; i < size; i++) {
            Integer value = deque[i];

            valueStr += value;

            if (i != size - 1) {
                valueStr += ", ";
            }
        }

        valueStr += " ]";

        return valueStr;
    }

}


public class CircularDequeTest {
    public static void main(String[] args) {
        CircularDeque deque = new CircularDeque(5);

        deque.insertFront(10);
        deque.insertRear(20);
        deque.insertFront(30);
        deque.insertRear(40);
        deque.insertRear(50);

        deque.insertRear(60);

        System.out.println(deque.toString());

        deque.popFront();
        deque.popFront();
        deque.popFront();

        deque.popRear();

        System.out.println(deque.toString());
    }
}
