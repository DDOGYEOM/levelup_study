package code.array;

import java.util.ArrayList;

public class ArrayListTest {
    public static void main(String[] args) {
        // 리스트 생성 (정수형)
        ArrayList<Integer> arrList = new ArrayList<Integer>();


        arrList.add(1);
        arrList.add(2);
        arrList.add(3);
        arrList.add(4);
        arrList.add(5);
        System.out.println("array list size: " + arrList.size()); // 삽입 후 길이 확인 => 5

        // 배열의 요소에 액세스
        int firstNum = arrList.get(0); // 1
        System.out.println("first number: " + firstNum);

        // 요소 삭제
        arrList.remove(3); // 3번째 인덱스의 값인 4 삭제

        System.out.println("array list size: " + arrList.size()); // 삭제 후 길이 확인 => 4

        // 배열의 요소에 값 할당
        arrList.set(2, 8); // 2번째 인덱스의 값인 3이 8로 변경됨.


        arrList.add(3, 9); // 3번째 인덱스 위치에 9 삽입.

        // 반복문 활용으로 모든 값 참조
        for (int i = 0; i < arrList.size(); i++) {
            int number = arrList.get(i);
            System.out.println(number);
        }
    }
}
