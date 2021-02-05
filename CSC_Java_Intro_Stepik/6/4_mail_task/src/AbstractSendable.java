public abstract class AbstractSendable<T> implements Sendable {

    private final String from;
    private final String to;
    private final T content;

    public AbstractSendable(String from, String to, T content) {
        this.from = from;
        this.to = to;
        this.content = content;
    }

    @Override
    public String getFrom() {
        return from;
    }

    @Override
    public String getTo() {
        return to;
    }

    public T getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractSendable<?> that = (AbstractSendable<?>) o;

        return from.equals(that.from) && to.equals(that.to);
    }
}
