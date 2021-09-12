import java.util.Scanner;

public class Main {
    private static int clampToInt(long val) {
        if (val > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        if (val < Integer.MIN_VALUE) { 
            return Integer.MIN_VALUE;
        }
        return (int) val;
    }

    public static int convert(Long val) {
        return val == null ? 0 : clampToInt(val);
    }

    /* Do not change code below */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String val = scanner.nextLine();
        Long longVal = "null".equals(val) ? null : Long.parseLong(val);
        System.out.println(convert(longVal));
    }
}
