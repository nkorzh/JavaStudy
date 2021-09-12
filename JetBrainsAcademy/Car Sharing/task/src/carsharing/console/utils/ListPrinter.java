package carsharing.console.utils;

import java.util.List;
import java.util.function.Function;

import static carsharing.console.utils.TextUtils.NEWLINE;

public class ListPrinter {

    public static <T> StringBuilder printList(List<T> list) {
        return printList(list, true);
    }

    public static <T> StringBuilder printFormattedList(List<T> list, String header, String emptyText, boolean lastZero) {
        if (list.isEmpty()) {
            return new StringBuilder(NEWLINE + emptyText + NEWLINE);
        }
        return new StringBuilder(NEWLINE)
                .append(header)
                .append(NEWLINE)
                .append(printList(list, lastZero));
    }

    public static <T> StringBuilder printList(List<T> list, boolean lastZero) {
        return printList(list, Object::toString, String.format("%n"), lastZero);
    }

//    public static <T> StringBuilder printList(List<T> list, Function<T, String> toStr, String format) {
//        return printList(list, toStr, TextUtils.NEWLINE, false);
//    }

    public static <T> StringBuilder printList(List<T> list, Function<T, String> toStr, String lineSuf, boolean lastZero) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size() - 1; i++) {
            sb.append(i + 1)
                    .append(". ")
                    .append(toStr.apply(list.get(i)))
                    .append(lineSuf);
        }
        return sb.append(lastZero ? 0 : list.size())
                .append(". ")
                .append(toStr.apply(list.get(list.size() - 1)))
                .append(lineSuf);
    }
}
