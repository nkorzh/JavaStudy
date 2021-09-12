package carsharing.console;

import carsharing.console.utils.ListPrinter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

class ListState<T> extends AbstractState {
    boolean lastZero = true;
    boolean printChildren;
    boolean printBack = true;

    AbstractState back;

    String emptyText = "";
    String header = "";
    List<Object> items;
    Supplier<List<T>> listUpdater;
    Function<Object, State> toState;

    public ListState(String name, State parent) {
        this(name, parent, "Back");
    }

    public ListState(String name, State parent, String backName) {
        super(name, parent, parent);
        listUpdater = Collections::emptyList;
        back = new AbstractState(backName, null) {
            @Override
            protected State move(String msg) {
                return null;
            }
        };
        printChildren = false;
        toState = e -> this;
    }

    @Override
    public State print(BufferedWriter writer) {
        try {
            if (textPrinter == null) {
                textPrinter = defaultTextPrinter();
            }
            updateItems();
            writer.write(textPrinter.get());
            writer.flush();
        } catch (final IOException e) {
            System.err.println("IO error while printing menu: " + e.getMessage());
        }
        return afterPrint;
    }

    public void setEmptyText(String emptyText) {
        this.emptyText = emptyText;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public State move(String input) {
        int pointNum = Integer.parseInt(input);
        if (pointNum == 0) {
            return parent;
        }
        try {
            return printChildren ? children.get(pointNum - 1) : toState.apply(items.get(pointNum  - 1));
        } catch (final NumberFormatException e) {
            System.err.println("Number expected: " + e.getMessage());
        } catch (final IndexOutOfBoundsException e) {
            int maxOption = (printChildren ? children.size() : items.size()) - 1;
            System.err.println("Number in range [0; " + maxOption + "] expected!");
        }
        return this;
    }

    public void setListUpdater(Supplier<List<T>> listUpdater) {
        this.listUpdater = listUpdater;
    }

    protected void updateItems() {
        if (printChildren) {
            return;
        }
        items = listUpdater.get()
            .stream()
            .map(e -> (Object) e)
            .collect(Collectors.toList());
        skipNextInput = items.isEmpty();
        if (awaitInput && !items.isEmpty()) {
            items.add(back);
        }
    }

    protected Supplier<String> defaultTextPrinter() {
        return () ->
            ListPrinter.printFormattedList(
                (List<?>) (printChildren ? children : items),
                header,
                emptyText,
                lastZero
            ).toString();
    }

    public void setTextPrinter(Supplier<String> s) {
        textPrinter = s;
    }

    public void setPrintChildren(boolean printChildren) {
        this.printChildren = printChildren;
    }

    void setChildren(List<State> children) {
        this.children = children;
        if (lastZero) {
            List<State> newChildren = new ArrayList<>(children);
            newChildren.add(back);
            this.children = newChildren;
        }
    }

    public void setLastZero(boolean lastZero) {
        this.lastZero = lastZero;
    }

    public void setToState(Function<Object, State> toState) {
        this.toState = toState;
    }
}
