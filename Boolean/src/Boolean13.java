import java.util.Scanner;

public class Boolean13 {

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        System.out.println("13. Даны три целых числа: A, B, C. Проверить истинность высказыва-\n" +
                "ния: «Хотя бы одно из чисел A, B, C положительное».");

        System.out.println("Введите число a:");
        int a = s.nextInt();

        System.out.println("Введите число b:");
        int b = s.nextInt();

        System.out.println("Введите число c:");
        int c = s.nextInt();

        boolean result = (a > 0) || (b > 0) || (c > 0);

        System.out.println(result);

    }
}
