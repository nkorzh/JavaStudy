import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Scanner;

public class Main {

    private static Collection<Integer> readOddPositions(InputStream inputStream) {
        Deque<Integer> list = new ArrayDeque<>();
        boolean isEven = true;

        try (Scanner scanner = new Scanner(inputStream)) {
            while (scanner.hasNextInt()) {
                if (isEven) {
                    scanner.nextInt();
                } else {
                    list.addFirst(scanner.nextInt());
                }
                isEven = !isEven;
            }
        }

        return list;
    }

    public static void main(String[] args) {
        Collection<Integer> list = readOddPositions(System.in);
        list.forEach(i -> System.out.print(i + " "));
        System.out.flush();
    }
}
