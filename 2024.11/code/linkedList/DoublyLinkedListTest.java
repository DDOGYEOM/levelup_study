package code.linkedList;

// Node 클래스 구현
class dNode<E> {
    E data;
    dNode<E> next; // 다음 노드를 가리키는 참조변수 (포인터 역할)
    dNode<E> prev; // 이전 노드를 가리키는 참조변수 (포인터 역할)

    dNode(E data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }

    public String toString() { // 노드의 내용 출력
        return String.valueOf(this.data);
    }
}


class DLinkedList<E> {
    private dNode<E> head;
    private dNode<E> tail;
    private int size;

    // 생성자
    public DLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void addFirst(E input) {
        dNode<E> newNode = new dNode<>(input); // 노드 생성

        newNode.next = head; // 새로운 노드의 다음 노드로 헤드를 지정

        if (head != null) {
            head.prev = newNode; // 기존에 노드가 있었다면 현재 헤드의 이전 노드로 새로운 노드를 지정
        }

        head = newNode; // head를 새로운 노드로 지정
        size++;

        if (head.next == null) {
            tail = head;
        }

    }

    public void addLast(E input) {
        dNode<E> newNode = new dNode<>(input); // 노드 생성

        if (size == 0) { // 리스트의 노드가 없는 경우, 첫번째 노드로 추가하는 메소드 사용
            addFirst(input);
        } else {
            tail.next = newNode; // 마지막 노드의 다음 노드로 생성한 노드를 지정
            newNode.prev = tail; // 새로운 노드의 이전 노드를 tail로 지정

            tail = newNode; // 마지막 노드를 갱신
            size++;
        }
    }


    // 중간 노드 가져오기
    private dNode<E> getNode(int index) {


        if (index < size / 2) {
            dNode<E> x = head;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            dNode<E> x = tail;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }

    public void add(int k, E input) {
        // k = 0이면 첫 번째 노드에 추가하는 것, addFirst 사용
        if (k == 0)
            addFirst(input);
        else {
            dNode<E> temp1 = getNode(k - 1); // k-1번째 노드를 temp1로 지정
            dNode<E> temp2 = temp1.next; // k번째 노드를 temp2로 지정

            dNode<E> newNode = new dNode<>(input);

            temp1.next = newNode; // temp1의 다음 노드로 새로운 노드를 지정
            newNode.next = temp2; // 새로운 노드 다음 노드로 temp2 지정

            if (temp2 != null) {
                temp2.prev = newNode;
            }

            newNode.prev = temp1;

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
        dNode<E> modifyNode = getNode(k);
        modifyNode.data = data;
    }


    public String toString() {
        // 노드가 없다면 []를 리턴합니다.
        if (head == null) {
            return "[]";
        }
        // 탐색을 시작합니다.
        dNode<E> temp = head;
        String str = "[";
        // 다음 노드가 없을 때까지 반복문을 실행합니다.
        // 마지막 노드는 다음 노드가 없기 때문에 아래의 구문은 마지막 노드는 제외됩니다.
        while (temp.next != null) {
            str += temp.data + ",";
            temp = temp.next;
        }
        // 마지막 노드를 출력결과에 포함시킵니다.
        str += temp.data;
        return str + "]";
    }

    public E removeFirst() {
        dNode<E> temp = head; // 첫번째 노드를 head로 지정
        head = temp.next; // head의 값을 두번째 노드로 변경

        E returnData = temp.data; // 데이터 삭제 전 리턴할 값을 임시 변수에 담아둔다.
        temp = null;

        // 리스트 내에 노드가 있다면 head의 이전 노드를 null로 지정
        if (head != null) {
            head.prev = null;
        }

        size--;
        return returnData;
    }

    public E remove(int k) {
        if (k == 0) {
            return removeFirst();
        }

        dNode<E> temp = getNode(k - 1); // k-1번째 node를 temp의 값으로 지정

        dNode<E> todoDeleted = temp.next;
        // 삭제할 노드를 todoDeleted에 기록
        // 삭제 노드를 지금 제거하면 삭제 앞 노드와 삭제 뒤 노드를 연결할 수 없다.

        temp.next = temp.next.next;
        // 삭제 앞 노드의 다음 노드로 삭제 뒤 노드를 지정한다.

        if (temp.next != null) {
            // 삭제할 노드의 전후 노드를 연결
            temp.next.prev = temp;
        }

        E returnData = todoDeleted.data;
        // 삭제된 데이터를 리턴하기 위해서 returnData에 저장한다.

        if (todoDeleted == tail) {
            tail = temp;
        }

        todoDeleted = null;
        size--;
        return returnData;
    }

    public E removeLast() {
        return remove(size - 1);
    }

    public int size() {
        return size;
    }

    public E get(int k) {
        dNode<E> temp = getNode(k);
        return temp.data;
    }


    public int indexOf(E data) {
        dNode<E> temp = head; // 탐색 대상이 되는 노드를 temp로 지정한다.
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


public class DoublyLinkedListTest {
    public static void main(String[] args) {
        DLinkedList<String> dList = new DLinkedList<String>();

        dList.add("A"); // A
        dList.addFirst("B"); // B-A
        dList.addLast("C"); // B-A-C
        System.out.println(dList);
        dList.add("D"); // B-A-C-D
        System.out.println(dList);
        dList.add(3, "E"); // B-A-C-E-D
        System.out.println(dList);
        dList.set(2, "F"); // B-A-F-E-D
        System.out.println(dList);
        dList.removeFirst(); // A-F-E-D
        dList.remove(1); // A-E-D

        System.out.println(dList); // [A,E,D]
        System.out.println(dList.size()); // 3
        System.out.println(dList.get(1)); // E
        System.out.println(dList.indexOf("E")); // 1
    }
}
