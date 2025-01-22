import java.util.Arrays;

class ArrayPriorityQueue {
    private int[] queue;
    private int size;
    private int capacity;

    public ArrayPriorityQueue(int c) {
        queue = new int[c];
        capacity = c;
        size = 0;
    }

    // 단순 삽입
    public void enqueue(int value) {
        if (size == capacity) {
            System.out.println("Priority Queue is full");
            return;
        }

        queue[size++] = value;
    }

    // 삭제
    public void dequeue() {
        if (size == 0) {
            System.out.println("Priority Queue is empty");
            return;
        }

        int maxIndex = 0;
        for (int i = 1; i < size; i++) {
            if (queue[i] > queue[maxIndex]) {
                maxIndex = i;
            }
        }

        int max = queue[maxIndex];

        for (int i = maxIndex; i < size - 1; i++) {
            queue[i] = queue[i + 1];
        }

        size--;

        System.out.println("dequeue ==> " + max);
    }

    public String toString() {
        String valueStr = "";
        for (int i = 0; i < size; i++) {
            valueStr += queue[i];
            if (i != size - 1) {
                valueStr += ",";
            }
        }
        return "[" + valueStr + "]";
    }
}


public class ArrayPriorityQueueTest {
    public static void main(String[] args) {
        // 최댓값이 기준인 우선순위 큐 생성
        ArrayPriorityQueue priorityQueue = new ArrayPriorityQueue(5);

        priorityQueue.enqueue(3);
        priorityQueue.enqueue(7);
        priorityQueue.enqueue(2);
        priorityQueue.enqueue(8);
        priorityQueue.enqueue(5);

        priorityQueue.dequeue(); // 8
        System.out.println(priorityQueue.toString()); // [3,7,2,5]
        priorityQueue.dequeue(); // 7
        System.out.println(priorityQueue.toString()); // [3,2,5]
        priorityQueue.dequeue(); // 5
        System.out.println(priorityQueue.toString()); // [3,2]
    }
}
