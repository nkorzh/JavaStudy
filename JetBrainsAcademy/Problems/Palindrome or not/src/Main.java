import java.util.*;
import java.util.function.Predicate;

import static java.util.stream.Collectors.*;


class CollectorPalindrome {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String[] words = scanner.nextLine().split(" ");

        Predicate<String> isPalindrome = word ->
                new StringBuilder(word)
                        .reverse()
                        .toString()
                        .equalsIgnoreCase(word);

        Map<Boolean, List<String>> palindromeOrNo = Arrays.stream(words)
                .collect(partitioningBy(isPalindrome));

        palindromeOrNo = new LinkedHashMap<>(palindromeOrNo);

        System.out.println(palindromeOrNo);
    }
}