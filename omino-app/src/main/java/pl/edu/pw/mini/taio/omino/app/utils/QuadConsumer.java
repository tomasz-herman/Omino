package pl.edu.pw.mini.taio.omino.app.utils;

import java.util.Objects;

@FunctionalInterface
public interface QuadConsumer<T, U, V, W> {
    void accept(T t, U u, V v, W w);

    default QuadConsumer<T, U, V, W> andThen(QuadConsumer<? super T, ? super U, ? super V, ? super W> after) {
        Objects.requireNonNull(after);

        return (l, r, m, n) -> {
            accept(l, r, m, n);
            after.accept(l, r, m, n);
        };
    }
}
