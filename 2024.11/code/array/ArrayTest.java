package code.array;

public class ArrayTest {
    public static void main(String[] args) {
        // 배열 생성
        int[] numbers = new int[] {1, 2, 3, 4, 5};

        // 배열의 요소에 액세스
        int firstNum = numbers[0]; // 1
        int secondNum = numbers[1]; // 2

        System.out.println("first number: " + firstNum);
        System.out.println("second number: " + secondNum);

        // 배열의 요소에 값 할당
        numbers[2] = 8; // 2번째 인덱스의 값인 3이 8로 변경됨.

        // 반복문 활용으로 모든 값 참조
        for (int i = 0; i < numbers.length; i++) {
            int number = numbers[i];
            System.out.println(number);
        }
    }
}
