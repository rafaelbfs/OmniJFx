package systems.terranatal.tfxtras.jfx.builder;

import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * An initializer containing an instance of {@link Node}, ie. any JavaFX component, whose properties can
 * be conveniently initialized through with a {@link Consumer} lambda expression.
 * It implements {@link Supplier} interface, which means it can conveniently be passed to any method that
 * expects a parameter of {@link Supplier} type
 * @param <N> any instance of {@link Node}
 */
public class Initializer<N extends Node> implements Supplier<N> {
    protected final N instance;

    public Initializer(N instance) {
        this.instance = instance;
    }

    public Initializer(Supplier<N> supplier) {
        this.instance = supplier.get();
    }

    /**
     * Initializes the {@link Initializer#instance}'s properties by passing it as parameter to
     * {@link Consumer#accept(Object)} method
     *
     * @param consumer the consumer which will accept thew instance to be initialized
     * @return this {@link Initializer}
     */
    public Initializer<N> with(Consumer<N> consumer) {
        consumer.accept(instance);
        return this;
    }

    /**
     * Does the same as {@link Initializer#with(Consumer)} method but returns the {@link Initializer#instance}
     * instead
     * @param consumer the consumer which will accept thew instance to be initialized
     * @return the {@link Initializer#instance}
     */
    public N init(Consumer<N> consumer) {
        return with(consumer).get();
    }

    public Initializer<N> withId(String id) {
        instance.setId(id);
        return this;
    }

    /**
     * Binds one of the {@link Initializer#instance}'s properties to the source {@link ObservableValue}
     * @param source the {@link ObservableValue} which one of the {@link Initializer#instance}'s properties will bind to
     * @param targetGetter a method of the {@link Initializer#instance} which will return the target property which will bind to the source {@link ObservableValue}
     * @return this {@link Initializer}
     * @param <V> the type of the values held by both the source {@link ObservableValue} and the target {@link Property}
     */
    public <V> Initializer<N> bind(ObservableValue<V> source, Function<N, Property<V>> targetGetter) {
        var property = targetGetter.apply(instance);
        property.bind(source);
        return this;
    }

    /**
     * Does the same as {@link Initializer#bind(ObservableValue, Function)} but takes a mapping {@link Function} to convert
     * from the source value, thus allowing the values within the source {@link ObservableValue} and
     * target {@link Property} to be of different types.
     *
     * @param source the {@link ObservableValue} which one of the {@link Initializer#instance}'s properties will bind to
     * @param targetGetter a method of the {@link Initializer#instance} which will return the target property which will bind to the source {@link ObservableValue}
     * @param mapping a {@link Function} which will convert the value inside {@link ObservableValue}
     *                to one instance accepted by target {@link Property}
     * @return this {@link Initializer}
     * @param <V> the type of the value held by the source {@link ObservableValue}
     * @param <R> the type of the value held by both the target {@link Property}
     */
    public <V, R> Initializer<N> bind(ObservableValue<V> source, Function<N, Property<R>> targetGetter,
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
