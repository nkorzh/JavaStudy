package carsharing.console;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public abstract class AbstractState implements State {
    boolean awaitInput = true;
    boolean skipNextInput = false;

    protected String name;
    protected State parent;
    protected State afterPrint;
    protected Supplier<String> textPrinter;
    protected List<State> children;

    AbstractState(String name, State parent) {
        this(name, parent, parent);
    }

    AbstractState(String name, State parent, State afterPrint) {
        this.name = name;
        this.parent = parent;
        this.afterPrint = afterPrint;
        this.children = Collections.emptyList();
    }

    @Override
    public State switchState(BufferedWriter writer, BufferedReader reader) throws IOException {
        print(writer);
        return move(awaitInput && !skipNextInput ? reader.readLine() : "0");
    }

    protected abstract State move(String msg);

    public State print(final BufferedWriter writer) {
        try {
            if (textPrinter == null) {
                textPrinter = defaultTextPrinter();
            }
            writer.write(textPrinter.get());
            writer.flush();
        } catch (final IOException e) {
            System.err.println("IO error while printing menu: " + e.getMessage());
        }
        return afterPrint;   // TODO what if null
    }

    public void setAwaitInput(boolean awaitInput) {
        this.awaitInput = awaitInput;
    }
    
    public void setAfterPrint(State afterPrint) {
        this.afterPrint = afterPrint;
    }

    protected Supplier<String> defaultTextPrinter() {
        return () -> "this is default text!";
    }

    public String getName() {
        return name;
    }

    void setParent(State parent) {
        this.parent = parent;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
