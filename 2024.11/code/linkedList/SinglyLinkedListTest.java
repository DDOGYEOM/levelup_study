package code.linkedList;

// sNode 클래스 구현
class sNode<E> {
    E data;
    sNode<E> next; // 다음 노드를 가리키는 참조변수 (포인터 역할)

    sNode(E data) {
        this.data = data;
        this.next = null;
    }

    public String toString() { // 노드의 내용 출력
        return String.valueOf(this.data);
    }
}


class SLinkedList<E> {
    private sNode<E> head;
    private sNode<E> tail;
    private int size;

    // 생성자
    public SLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void addFirst(E input) {
        sNode<E> newNode = new sNode<>(input); // 노드 생성

        newNode.next = head; // 새로운 노드의 다음 노드로 헤드를 지정

        head = newNode; // head를 새로운 노드로 지정
        size++;

        if (head.next == null) {
            tail = head;
        }

    }

    public void addLast(E input) {
        sNode<E> newNode = new sNode<>(input); // 노드 생성

        if (size == 0) { // 리스트의 노드가 없는 경우, 첫번째 노드로 추가하는 메소드 사용
            addFirst(input);
        } else {
            tail.next = newNode; // 마지막 노드의 다음 노드로 생성한 노드를 지정
            tail = newNode; // 마지막 노드를 갱신
            size++;
        }
    }


    // 중간 노드 가져오기
    private sNode<E> getNode(int index) {
        sNode<E> x = head;
        for (int i = 0; i < index; i++)
            x = x.next;
        return x;
    }

    public void add(int k, E input) {
        // k = 0이면 첫 번째 노드에 추가하는 것, addFirst 사용
        if (k == 0) {
            addFirst(input);
        } else {
            sNode<E> temp1 = getNode(k - 1); // k-1번째 노드를 temp1로 지정
            sNode<E> temp2 = temp1.next; // k번째 노드를 temp2로 지정

            sNode<E> newNode = new sNode<>(input);
            temp1.next = newNode; // temp1의 다음 노드로 새로운 노드를 지정
            newNode.next = temp2; // 새로운 노드 다음 노드로 temp2 지정
            size++;

            if (newNode.next == null)
                tail = newNode;
            // 새로운 노드의 다음 노드가 없다면
            // 새로운 노드가 마지막 노드이기 때문에
        }
    }

    public void add(E input) {
        // 리스트의 노드가 없는 경우 addFirst, 존재할경우 addLast 메서드 사용
        if (size == 0) {
            addFirst(input);
        } else {
            addLast(input);
        }
    }

    public void set(int k, E data) {
        sNode<E> modifyNode = getNode(k);
        modifyNode.data = data;
    }



    public String toString() {
        if (head == null) {
            return "-1"; // 노드가 없다면 -1을 출력
        }

        // 탐색 시작
        sNode<E> temp = head;
        String str = " ";

        // 다음 노드가 없을 때 까지 반복문을 실행한다.
        // tail은 다음 노드가 없기 때문에, 마지막 노드는 제외된다.

        while (temp.next != null) {
            str += temp.data + ", ";
            temp = temp.next;
        }

        // 마지막 노드 출력결과에 포함
        str += temp.data;
        return str;
    }

    public Object removeFirst() {
        sNode<E> temp = head; // 첫번째 노드를 head로 지정
        head = temp.next; // head의 값을 두번째 노드로 변경

        Object returnData = temp.data; // 데이터 삭제 전 리턴할 값을 임시 변수에 담아둔다.
        temp = null;
        size--;
        return returnData;
    }

    public Object remove(int k) {
        if (k == 0)
            return removeFirst();

        sNode<E> temp = getNode(k - 1); // k-1번째 node를 temp의 값으로 지정

        sNode<E> todoDeleted = temp.next;
        // 삭제할 노드를 todoDeleted에 기록
        // 삭제 노드를 지금 제거하면 삭제 앞 노드와 삭제 뒤 노드를 연결할 수 없다.

        temp.next = temp.next.next;
        // 삭제 앞 노드의 다음 노드로 삭제 뒤 노드를 지정한다.

        Object returnData = todoDeleted.data;
        // 삭제된 데이터를 리턴하기 위해서 returnData에 저장한다.

        if (todoDeleted == tail)
            tail = temp;

        todoDeleted = null;
        size--;
        return returnData;
    }

    public int size() {
        return size;
    }

    public Object get(int k) {
        sNode<E> temp = getNode(k);
        return temp.data;
    }


    public int indexOf(E data) {
        sNode<E> temp = head; // 탐색 대상이 되는 노드를 temp로 지정한다.
        int index = 0; // 탐색 대상의 엘리먼트

        while (temp.data != data) { // 탐색 값과 탐색 대상의 값을 비교
            temp = temp.next;
            index++;

            if (temp == null) // 더 이상 탐색할 대상이 없다는 것
                return -1;
        }

        return index;
    }
}


public class SinglyLinkedListTest {
    public static void main(String[] args) {
        SLinkedList<Integer> list = new SLinkedList<>();

        list.add(4); // 4
        list.addFirst(1); // 1-4
        list.addLast(2); // 1-4-2
        list.add(3); // 1-4-2-3
        list.add(3, 5); // 1-4-2-5-3
        list.set(3, 6); // 1-4-2-6-3
        list.removeFirst(); // 4-2-6-3
        list.remove(1); // 4-6-3

        System.out.println(list); // 4,6,3
        System.out.println(list.get(1)); // 6
        System.out.println(list.indexOf(6)); // 1
        System.out.println(list.size()); // 3

    }
}
