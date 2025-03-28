class LinkedListDeque<T> {
    private Node<T> front;
    private Node<T> rear;
    private int size;

    // 이중 연결 리스트의 노드 클래스 구현
    private static class Node<T> {
        T data;
        Node<T> prev;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.prev = null;
            this.next = null;
        }
    }

    public LinkedListDeque() {
        front = null;
        rear = null;
        size = 0;
    }

    // 덱의 앞에 삽입
    public void insertFront(T item) {
        Node<T> newNode = new Node<T>(item);
        if (front == null) {
            front = rear = newNode;
        } else {
            // 기존 front 노드와 새 노드의 관계를 연결해줌
            newNode.next = front;
            front.prev = newNode;
            front = newNode;
        }

        System.out.println("insert front --> " + item);

        size++;
    }

    // 덱의 뒤에 삽입
    public void insertRear(T item) {
        Node<T> newNode = new Node<T>(item);
        if (rear == null) {
            front = rear = newNode;
        } else {
            // 기존 rear 노드와 새 노드의 관계를 연결해줌
            newNode.prev = rear;
            rear.next = newNode;
            rear = newNode;
        }

        System.out.println("insert rear --> " + item);

        size++;
    }

    // 덱의 앞에서 삭제
    public T popFront() {
        if (front == null) {
            throw new IllegalStateException("Deque is empty!");
        }

        T data = front.data; // front의 데이터를 변수에 저장

        if (front == rear) {
            front = rear = null; // 데이터가 1개만 있을 경우 삭제
        } else {
            front = front.next; // 데이터가 2개 이상일 경우 기존 front 노드를 지우고, front를 다음 노드로 설정
            front.prev = null;
        }

        size--;

        return data;
    }

    // 덱의 뒤에서 삭제
    public T popRear() {
        if (rear == null) {
            throw new IllegalStateException("Deque is empty!");
        }

        T data = rear.data; // front의 데이터를 변수에 저장

        if (front == rear) {
            front = rear = null; // 데이터가 1개만 있을 경우 삭제
        } else {
            rear = rear.prev; // 데이터가 2개 이상일 경우 기존 front 노드를 지우고, front를 다음 노드로 설정
            rear.next = null;
        }

        size--;

        return data;
    }

    // 덱이 비었는지 확인
    public boolean isEmpty() {
        return size == 0;
    }

    // 덱의 크기 반환
    public int size() {
        return size;
    }

    public String toString() {
        if (front == null) {
            return "deque is empty";
        }

        String valueStr = "Deque: [";

        Node<T> curNode = front;

        while (curNode != null) {
            valueStr += curNode.data;
            if (curNode.next != null) {
                valueStr += ", ";
            }
            curNode = curNode.next;
        }

        valueStr += "]";

        return valueStr;
    }

}


public class LinkedListDequeTest {
    public static void main(String[] args) {
        LinkedListDeque<Integer> deque = new LinkedListDeque<Integer>();

        deque.insertFront(10);
        deque.insertRear(20);
        deque.insertFront(30);
        deque.insertRear(40);
        deque.insertRear(50);

        System.out.println(deque.toString());

        System.out.println("pop front --> " + deque.popFront());
        System.out.println("pop rear --> " + deque.popRear());

        System.out.println(deque.toString());
    }
}
