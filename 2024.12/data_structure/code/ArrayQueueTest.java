class ArrayQueue<T> {
    int MAX = 6;
    int front; // 머리 쪽에 위치할 index값, pop할때 참조하는 index
    int rear; // 꼬리 쪽에 위치할 index값, push할때 참조하는 index
    Object[] queue;

    public ArrayQueue() {
        front = rear = 0; // 초기값 0
        queue = new Object[MAX]; // 배열 생성
    }

    public boolean queueIsEmpty() { // queue에 아무것도 들어있지 않은지 판단하는 함수
        return front == rear;
    }

    public boolean queueIsFull() { // queue가 가득 차 공간이 없는지 판단하는 함수
        if (rear == MAX - 1) {
            return true;
        } else
            return false;
    }

    public int size() { // queue에 현재 들어가 있는 데이터의 개수를 return
        return rear - front;
    }

    public void push(T value) {
        if (queueIsFull()) {
            System.out.println("Queue is Full");
            return;
        }
        queue[rear++] = value; // rear가 위치한 곳에 값을 넣어주고 rear를 증가시킨다.
    }

    public Object pop() {
        if (queueIsEmpty()) {
            System.out.println("Queue is Empty");
            return -1;
        }
        Object popValue = queue[front++];
        return popValue;
    }

    public Object peek() {
        if (queueIsEmpty()) {
            System.out.println("Queue is Empty");
            return -1;
        }
        Object popValue = queue[front];
        return popValue;
    }
}


public class ArrayQueueTest {
    public static void main(String[] args) {
        ArrayQueue<Integer> arrQueue = new ArrayQueue<Integer>();

        arrQueue.push(1);
        arrQueue.push(2);
        arrQueue.push(3);
        arrQueue.push(4);
        arrQueue.push(5);

        System.out.println("queue isFull : " + arrQueue.queueIsFull());

        System.out.println(arrQueue.pop() + " Pop!");
        System.out.println(arrQueue.pop() + " Pop!");
        System.out.println(arrQueue.pop() + " Pop!");

        System.out.println(arrQueue.peek() + " Peek!");

        System.out.println(arrQueue.pop() + " Pop!");
        System.out.println(arrQueue.pop() + " Pop!");

        System.out.println("queue item size : " + arrQueue.size());
        System.out.println("queue isEmpty : " + arrQueue.queueIsEmpty());
    }
}


