import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        final int leftBorder;
        final int rightBorder;
        if (a > b) {
            int tmp = a;
            leftBorder = b;
            rightBorder = tmp;
        } else {
            leftBorder = a;
            rightBorder = b;
        }
        int pairsAmount = scanner.nextInt();
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < pairsAmount; i++) {
            map.putIfAbsent(scanner.nextInt(), scanner.next());
        }
        map.entrySet()
                .stream()
                .filter(e -> e.getKey() >= leftBorder && e.getKey() <= rightBorder)
                .sorted()
                .forEach(e -> System.out.println(e.getKey() + " " + e.getValue()));
    }
}