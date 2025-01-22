class ArrayCircularQueue2 {
    private String[] queue;
    private int front;
    private int size;
    private int capacity;


    public ArrayCircularQueue2(int c) {
        // 포화상태 여부 판단을 위해 1칸을 비워놓으므로 선언한 크기만큼의 Queue를 사용하기 위해선
        // size + 1 크기의 queue를 만든다.
        queue = new String[c];
        capacity = c;
        front = 0;
        size = 0;
    }

    // 포화상태 파악 메서드
    public boolean isFull() {
        return size == capacity;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getFrontIndex() {
        return front;
    }

    public int getRearIndex() {
        return (front + size) % capacity;
    }


    // enqueue 메서드
    public void enqueue(String data) {
        if (isFull()) {
            System.out.println("큐가 꽉 차있어 삽입이 불가능합니다!");
            return;
        }

        System.out.println("enqueue! ==> " + data);

        int rear = (front + size) % capacity;

        queue[rear] = data;
        size++;
    }

    // dequeue 메서드
    public void dequeue() {
        if (isEmpty()) {
            System.out.println("큐가 비어있어 삭제가 불가능합니다!");
            return;
        }

        System.out.println("dequeue! ==> " + queue[front]);
        queue[front] = null;

        front = (front + 1) % capacity;
        size--;
    }


}



public class CircularQueueTest2 {
    public static void main(String[] args) {
        ArrayCircularQueue2 circularQueue = new ArrayCircularQueue2(5);

        // 삽입용 테스트 데이터
        String[] dataArr = {"짱구", "철수", "유리", "훈이", "맹구", "흰둥이"};

        for (int i = 0; i < dataArr.length; i++) {
            circularQueue.enqueue(dataArr[i]); // 6번째 enqueue 시 포화 상태로 삽입 불가
        }

        System.out.println("circular queue is Full : " + circularQueue.isFull());

        for (int i = 0; i < dataArr.length; i++) {
            circularQueue.dequeue(); // 6번째 dequeue 시 공백 상태로 제거 불가

            if (i == 3) {
                System.out.println("front index: " + circularQueue.getFrontIndex());
                System.out.println("rear index: " + circularQueue.getRearIndex());
            }
        }

        System.out.println("circular queue is Empty : " + circularQueue.isEmpty());
    }
}
