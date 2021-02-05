import java.util.function.*;
// TODO: make static and copy
public class MailMessage extends AbstractSendable<String> {
    public MailMessage(String from, String to, String content) {
        super(from, to, content);
    }
}