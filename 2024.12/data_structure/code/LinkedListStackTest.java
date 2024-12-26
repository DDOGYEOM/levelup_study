class Node<T> {
    private T item;
    private Node<T> node;

    public Node(T item) {
        this.item = item;
        this.node = null;
    }

    protected void linkNode(Node<T> node) {// 가르킬 노드를 지정
        this.node = node;
    }

    protected T getData() {
        return this.item;
    }

    protected Node<T> getNextNode() { // 다음 노드를 리턴
        return this.node;
    }
}


// LinkedListStack을 관리하는 클래스
class StackNodeManager<T> {
    Node<T> top; // 가장 최근에 들어온 노드를 가리킴
    int size = 0;

    public StackNodeManager() {
        this.top = null;
    }

    public void push(T data) {
        Node<T> node = new Node<T>(data); // 노드를 생성
        node.linkNode(top); // 새로 생성된 노드가 top이 가르키고 있는 노드를 링크로 연결하게 함
        top = node; // top의 값을 가장 최근에 생성된 node로 바꿈
        size++;
    }

    public T pop() {
        T data = top.getData();
        top = top.getNextNode(); // 현재 top이 가리키고 있는 노드를 가리키게 함
        size--;
        return data;
    }

    public T peek() {
        return top.getData();
    }


    public int size() {
        return size;
    }
}


public class LinkedListStackTest {
    public static void main(String[] args) {
        StackNodeManager<Integer> linkedListStack = new StackNodeManager<Integer>();

        linkedListStack.push(1);
        linkedListStack.push(2);
        linkedListStack.push(3);
        linkedListStack.push(4);
        linkedListStack.push(5);

        System.out.println(linkedListStack.pop() + " Pop!");
        System.out.println(linkedListStack.pop() + " Pop!");
        System.out.println(linkedListStack.pop() + " Pop!");

        System.out.println(linkedListStack.peek() + " Peek!");

        System.out.println("stack item size : " + linkedListStack.size());
    }
}
