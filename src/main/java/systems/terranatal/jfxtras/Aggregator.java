package systems.terranatal.jfxtras;

import javafx.scene.Node;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.isNull;

public sealed class Aggregator<T extends Node> permits PaneAggregator {
    protected T node;

    protected Aggregator(@NotNull T node) {
        this.node = node;
    }

    public <U extends Node> Aggregator<U> with(Function<T, U> aggregator) {
        validateState();
        return new Aggregator<>(aggregator.apply(node));
    }

    public Aggregator<T> with(Consumer<T> consumer) {
        validateState();
        consumer.accept(node);
        return this;
    }

    public <U extends Node> Aggregator<T> with(U element, BiConsumer<T, U> consumer) {
        validateState();
        consumer.accept(node, element);
        return this;
    }

    public <U extends Node> Aggregator<T> with(Supplier<U> element, BiConsumer<T, U> consumer) {
        return with(element.get(), consumer);
    }

    /**
     * Retrieves the contained value once all its initialization has been performed. This method can be called only ONCE
     * and any subsequent call will throw an Exception
     * @return the held value
     */
    public T get() {
        validateState();
        var value = node;
        node = null;
        return value;
    }

    protected void validateState() {
        if (isNull(node)) {
            throw new IllegalStateException("The contained value can only be retrieved once.");
        }
    }
}
