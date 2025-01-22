class ArrayCircularQueue {
    private String[] queue;
    private int front;
    private int rear;


    public ArrayCircularQueue(int size) {
        // 포화상태 여부 판단을 위해 1칸을 비워놓으므로 선언한 크기만큼의 Queue를 사용하기 위해선
        // size + 1 크기의 queue를 만든다.
        queue = new String[size + 1];
        front = 0;
        rear = 0;
    }

    // 포화상태 파악 메서드
    public boolean isFull() {
        return front == (rear + 1) % queue.length;
    }

    public boolean isEmpty() {
        return front == rear;
    }


    // enqueue 메서드
    public void enqueue(String data) {
        if (isFull()) {
            System.out.println("큐가 꽉 차있어 삽입이 불가능합니다!");
            return;
        }

        System.out.println("enqueue! ==> " + data);

        queue[rear] = data;

        rear++;
        rear %= queue.length;
    }

    // dequeue 메서드
    public void dequeue() {
        if (isEmpty()) {
            System.out.println("큐가 비어있어 삭제가 불가능합니다!");
            return;
        }

        System.out.println("dequeue! ==> " + queue[front]);
        queue[front] = null;
        front++;
        front %= queue.length;
    }


}



public class CircularQueueTest {
    public static void main(String[] args) {
        ArrayCircularQueue circularQueue = new ArrayCircularQueue(5);

        // 삽입용 테스트 데이터
        String[] dataArr = {"짱구", "철수", "유리", "훈이", "맹구", "흰둥이"};

        for (int i = 0; i < 6; i++) {
            circularQueue.enqueue(dataArr[i]); // 6번째 enqueue 시 포화 상태로 삽입 불가
        }

        System.out.println("circular queue is Full : " + circularQueue.isFull());

        for (int i = 1; i <= 6; i++) {
            circularQueue.dequeue(); // 6번째 dequeue 시 공백 상태로 제거 불가
        }

        System.out.println("circular queue is Empty : " + circularQueue.isEmpty());
    }
}
