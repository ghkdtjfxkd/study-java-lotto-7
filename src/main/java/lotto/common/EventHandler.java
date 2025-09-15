package lotto.common;

@FunctionalInterface
public interface EventHandler<T> {
    void handle(T event);
}
