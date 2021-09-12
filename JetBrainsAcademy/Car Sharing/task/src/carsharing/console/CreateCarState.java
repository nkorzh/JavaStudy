package carsharing.console;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.function.Consumer;

import static carsharing.console.utils.TextUtils.NEWLINE;

public class CreateCarState extends AbstractState {
    Consumer<String> postMethod;

    public CreateCarState(String name, State parent) {
        super(name, parent);
    }

    @Override
    public State switchState(BufferedWriter writer, BufferedReader reader) throws IOException {
        print(writer);
        postMethod.accept(reader.readLine());
        writer.write("The car was added!" + NEWLINE);
        return afterPrint;
    }

    @Override
    protected State move(String msg) {
        return null;
    }

    void setPostMethod(Consumer<String> consumer) {
        postMethod = consumer;
    }
}
