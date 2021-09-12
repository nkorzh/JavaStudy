package carsharing.console;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public interface State {
    String BACK = "Back";
    String EXIT = "Exit";

    State switchState(BufferedWriter writer, BufferedReader reader) throws IOException;

    String getName();
}
