public class ArrayStack {
    int top;    //인덱스
    int size;    //스택 배열의 크기
    int [] stack;
    public ArrayStack(int size) {
        this.size = size;
        stack = new int[size];
        top = -1;
    }

    public void push(int item) {
        stack[++top] = item;
        System.out.println(stack[top] + " Push!");
    }
    public void pop() {
        System.out.println(stack[top] + " Pop!");
        int pop = stack[top];
        stack[top--] = 0;
    }
    public void peek() {
        System.out.println(stack[top] + " Peek!");
    }
}