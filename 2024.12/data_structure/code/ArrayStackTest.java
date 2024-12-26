class ArrayStack<T> {
    int top; // 인덱스
    int size; // 스택 배열의 크기
    Object[] stack;

    public ArrayStack(int size) {
        this.size = size;
        stack = new Object[size];
        top = -1;
    }

    public void push(T item) {
        stack[++top] = item;
    }

    public Object pop() {
        Object pop = stack[top];
        stack[top--] = null;

        return pop;
    }

    public Object peek() {
        return stack[top];

    }

    public int size() {
        int itemSize = top + 1;

        return itemSize;
    }
}


public class ArrayStackTest {
    public static void main(String[] args) {
        ArrayStack<Integer> arrStack = new ArrayStack<Integer>(5); // 크기가 5인 정수형 스택 선언

        arrStack.push(1);
        arrStack.push(2);
        arrStack.push(3);
        arrStack.push(4);
        arrStack.push(5);


        System.out.println(arrStack.pop() + " Pop!");
        System.out.println(arrStack.peek() + " Peek!");
        System.out.println(arrStack.pop() + " Pop!");
        System.out.println(arrStack.pop() + " Pop!");

        System.out.println("stack item size : " + arrStack.size());
    }
}
