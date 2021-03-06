import java.util.Scanner;

public class MinOfTwo {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.print("Enter amount of tests: ");

        int amount = Integer.parseInt(s.nextLine());

        if (amount > 0) {

            String[] inputText = new String[amount];
            String output = "";

            for (int i = 0; i < amount; i++) inputText[i] = s.nextLine();

            for (String line : inputText) {

                String[] parameters = line.split(" ");

                if (parameters.length < 2) throw new IllegalArgumentException("Lack of arguments");

                int x = Integer.parseInt(parameters[0]);
                int y = Integer.parseInt(parameters[1]);

                output = output + Math.min(x, y) + " ";
            }
            System.out.println(output);
        }
    }

}
