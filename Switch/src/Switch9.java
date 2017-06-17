import java.util.Scanner;

public class Switch9 {

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        System.out.println("9. Даны два целых числа: D (день) и M (месяц), определяющие правиль-\n" +
                "ную дату невисокосного года. Вывести значения D и M для даты, следую-\n" +
                "щей за указанной.");

        System.out.print("Введите день: ");
        int d = s.nextInt();

        System.out.print("Введите номер месяца: ");
        int m = s.nextInt();

        if (d > 0 && d < 32 && m > 0 && m < 13) {
            int days = 0;
            switch (m) {
                case 2:
                    days = 28;
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    days = 30;
                    break;
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    days = 31;
                    break;
            }
            if (++d > days) {
                d = 1;
                if (++m > 12) m = 1;
            }
            System.out.println("След. дата: " + d + "-" + m);
        } else {
            System.out.println("Некорректная дата!");
        }

    }
}

