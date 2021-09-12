package carsharing.console;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.function.Function;
import java.util.function.Supplier;

import static carsharing.console.utils.TextUtils.NEWLINE;

public class PostRequestState extends AbstractState {
    Function<String, Boolean> postMethod;
    String successMsg = "";
    String errorMsg = "Unknown error";
    String textPrompt;
    State success;
    State error;

    public PostRequestState(String name, State parent) {
        this(name, parent, "");
    }

    public PostRequestState(String name, State parent, String textPrompt) {
        super(name, parent);
        this.textPrompt = textPrompt;
        success = error = parent;
    }

    @Override
    public State switchState(BufferedWriter writer, BufferedReader reader) throws IOException {
        print(writer);
        boolean okResult = postMethod.apply(awaitInput ? reader.readLine() : "");
        writer.write(NEWLINE + (okResult ? successMsg : errorMsg) + NEWLINE);
        return okResult ? success : error;
    }

    @Override
    protected State move(String msg) {
        return null;
    }

    @Override
    protected Supplier<String> defaultTextPrinter() {
        return () -> textPrompt.isEmpty() ? "" : NEWLINE + textPrompt + NEWLINE;
    }

    public void setTextPrompt(String textPrompt) {
        this.textPrompt = textPrompt;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    void setPostMethod(Function<String, Boolean> consumer) {
        postMethod = consumer;
    }

    public void setSuccess(State success) {
        this.success = success;
    }

    public void setError(State error) {
        this.error = error;
    }
}
