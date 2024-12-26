class QueueNode<T> {
    T value; // 값을 넣음
    QueueNode<T> queueNode; // 다음 노드를 가리킴

    public QueueNode(T value) {
        this.value = value;
        queueNode = null;
    }

    public T getValue() {
        return value;
    }

    public QueueNode<T> getNextNode() {
        return queueNode;
    }

    public void setNextNode(QueueNode<T> queueNode) {
        this.queueNode = queueNode;
    }
}


class QueueNodeManager<T> { // 큐의 기능을 만들 클래스
    QueueNode<T> front, rear;

    public QueueNodeManager() {
        front = rear = null;
    }

    public boolean queueIsEmpty() {
        if (front == null && rear == null) {
            return true;
        } else {
            return false;
        }
    }

    public void push(T value) {
        QueueNode<T> queueNode = new QueueNode<T>(value);
        if (queueIsEmpty()) { // 큐안에 데이터가 없으면 첫번째 Node에 front와 rear를 연결
            front = rear = queueNode;
        } else {
            front.setNextNode(queueNode); // 큐 안에 데이터가 있으면 front를 다음 노드에 연결 후 front의 값을 마지막 노드로 삽입
            front = queueNode;
        }
    }

    public T pop() {
        if (queueIsEmpty()) {
            System.out.println("Queue is Empty");
            return null;
        } else {
            QueueNode<T> popNode = rear;
            rear = rear.getNextNode();
            return popNode.getValue();
        }
    }

    public T peek() {
        if (queueIsEmpty()) {
            System.out.println("Queue is Empty");
            return null;
        } else {
            return rear.getValue();
        }
    }

    public int size() {
        QueueNode<T> front2 = front;
        QueueNode<T> rear2 = rear;
        int count = 0;
        while (front2 != rear2 && rear2 != null) { // 큐가 비어있는 경우가 있을수도 있을때도 생각해야함
            count++;
            rear2 = rear2.getNextNode();
        }

        // 마지막 큐 개수까지 계산해야함
        if (front2 == rear2) {
            count++;
        }

        return count;
    }
}


public class LinkedListQueueTest {
    public static void main(String[] args) {
        QueueNodeManager<Integer> linkedListQueue = new QueueNodeManager<Integer>();

        linkedListQueue.push(1);
        linkedListQueue.push(2);
        linkedListQueue.push(3);
        linkedListQueue.push(4);
        linkedListQueue.push(5);

        System.out.println("queue item size : " + linkedListQueue.size());

        System.out.println(linkedListQueue.pop() + " Pop!");
        System.out.println(linkedListQueue.pop() + " Pop!");
        System.out.println(linkedListQueue.pop() + " Pop!");

        System.out.println(linkedListQueue.peek() + " Peek!");

        System.out.println("queue item size : " + linkedListQueue.size());
    }
}
