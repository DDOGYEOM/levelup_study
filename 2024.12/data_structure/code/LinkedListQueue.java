public class QueueNode {
    int value; //값을 넣음
    QueueNode queueNode; //다음 노드를 가리킴
    public QueueNode(int value) {
        this.value = value;
        queueNode = null;
    }
    public int getValue() {
        return value;
    }
    public QueueNode getNextNode() {
        return queueNode;
    }
    public void setNextNode(QueueNode queueNode) {
        this.queueNode = queueNode;
    }
}

public class QueueNodeManager{ //큐의 기능을 만들 클래스
    QueueNode front,rear;
    public QueueNodeManager() {
        front = rear = null;
    }
    public boolean queueisEmpty() {
        if(front == null  && rear == null) {
            return true;
        }else {
            return false;
        }
    }
    public void push(int value) {
        QueueNode queueNode = new QueueNode(value);
        if(queueisEmpty()) {    //큐안에 데이터가 없으면 첫번째 Node에 front와 rear를 연결
            front = rear = queueNode;
        }else {
            front.setNextNode(queueNode); //큐 안에 데이터가 있으면 front를 다음 노드에 연결 후 front의 값을 마지막 노드로 삽입
            front = queueNode;
        }
    }
    public QueueNode pop() {
        if(queueisEmpty()) {
            System.out.println("Queue is Empty");
            return null;
        }else {
            QueueNode popNode = rear;
            rear = rear.getNextNode();
            return popNode;
        }
    }
    public QueueNode peek() {
        if(queueisEmpty()) {
            System.out.println("Queue is Empty");
            return null;
        }else {
            return rear;
        }
    }
    public int size() {
        QueueNode front2 = front;
        QueueNode rear2 = rear;
        int count = 0;
        while(front2 != rear2 && rear2 !=null) { //큐가 비어있는 경우가 있을수도 있을때도 생각해야함
            count++;
            rear2 = rear2.getNextNode();
        }
        return count;
    }
}