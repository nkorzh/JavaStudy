import java.util.*;
import java.util.function.Consumer;

public class MailService<T> implements Consumer<AbstractSendable<T>> {
    Map<String, List<T>> mailBox = new HashMap<>() {
        @Override
        public LinkedList<T> get(Object key) {
            return (LinkedList<T>) super.getOrDefault(key, new LinkedList<>());
        }
    };

    @Override
    public void accept(AbstractSendable<T> message) {
        List<T> newValue = new LinkedList<>();
        newValue.add(message.getContent());
        mailBox.merge(message.getTo(),
                newValue,
                (o, n) -> {
                    o.addAll(n);
                    return o;
                });
    }

    public Map<String, List<T>> getMailBox() {
        return mailBox;
    }
}
