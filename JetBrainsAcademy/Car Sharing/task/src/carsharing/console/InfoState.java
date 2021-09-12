package carsharing.console;

/**
 * State that always prints empty text
 */
public class InfoState extends ListState<Object> {

    public InfoState(String name, State parent) {
        super(name, parent);
    }

    public void setMessage(String msg) {
        setEmptyText(msg);
    }
}
