import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(
                Arrays.stream(scanner.nextLine().split("\\s+"))
                        .collect(Collectors.toList()));
    }
}