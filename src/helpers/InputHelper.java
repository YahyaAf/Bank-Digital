package helpers;
import java.util.Scanner;
import java.math.BigDecimal;

public class InputHelper {

        private static Scanner sc = new Scanner(System.in);

        public static String readInput(String message) {
            System.out.print(message);
            return sc.nextLine().trim();
        }

        public static int readInt(String message) {
            while (true) {
                String input = readInput(message);
                if (input.matches("-?\\d+")) {
                    return Integer.parseInt(input);
                } else {
                    System.out.println("Error: Please enter a valid integer!");
                }
            }
        }

        public static BigDecimal readBigDecimal(String message) {
            while (true) {
                String input = readInput(message);
                try {
                    return new BigDecimal(input);
                } catch (NumberFormatException e) {
                    System.out.println("Error: Please enter a valid decimal number!");
                }
            }
        }
}


