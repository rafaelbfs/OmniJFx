/*
 * Copyright © 2024, Rafael Barros Felix de Sousa @ Terranatal Systems
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice,
 *       this list of conditions and the following disclaimer in the documentation
 *       and/or other materials provided with the distribution.
 *     * Neither the name of {{ project }} nor the names of its contributors
 *       may be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package systems.terranatal.omnijfx.jfx.builder;

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
    /**
     * The instance that this initializer holds
     */
    protected final N instance;

    /**
     * Constructor to that takes an instance of {@link Node}
     * @param instance the instance of {@link Node} to be initialized
     */
    public Initializer(N instance) {
        this.instance = instance;
    }

    /**
     * Allows the user to pass a {@link Supplier} of {@link Node}s
     * @param supplier the {@link Supplier} that this initializer will call
     */
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

    /**
     * Implementation which allows this class to be used as a {@link Supplier}
     * @return the JavaFX component instance that this {@link Initializer} holds
     */
    @Override
    public N get() {
        return instance;
    }
}
