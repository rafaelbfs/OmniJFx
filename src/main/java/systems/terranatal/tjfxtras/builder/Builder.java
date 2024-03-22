package systems.terranatal.tjfxtras.builder;

import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Builder<N extends Node> implements Supplier<N> {
    protected final N instance;

    public Builder(N instance) {
        this.instance = instance;
    }

    public Builder(Supplier<N> supplier) {
        this.instance = supplier.get();
    }

    public Builder<N> with(Consumer<N> consumer) {
        consumer.accept(instance);
        return this;
    }

    public N init(Consumer<N> consumer) {
        return with(consumer).get();
    }

    public Builder<N> withId(String id) {
        instance.setId(id);
        return this;
    }

    public <V> Builder<N> bind(ObservableValue<V> source, Function<N, Property<V>> targetGetter) {
        var property = targetGetter.apply(instance);
        property.bind(source);
        return this;
    }

    public <V, R> Builder<N> bind(ObservableValue<V> source, Function<N, Property<R>> targetGetter,
                               Function<V,R> mapping) {
        var property = targetGetter.apply(instance);
        property.bind(source.map(mapping));
        return this;
    }

    @Override
    public N get() {
        return instance;
    }
}
