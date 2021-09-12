import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> list = new ArrayList<>();
        int next = scanner.nextInt();
        while (next != 0) {
            list.add(next);
            next = scanner.nextInt();
        }
        System.out.println(list.stream()
                .max(Comparator.naturalOrder())
                .orElse(0));
    }
}