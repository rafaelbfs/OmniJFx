package systems.terranatal.tfxtras.jfx.builder;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.function.Supplier;

/**
 * Class deriving {@link Initializer} with specializations for {@link Pane}s
 * @param <P> a subtype of {@link Pane}
 */
public class PaneInitializer<P extends Pane> extends Initializer<P> {
    /**
     * Constructs an initializer with the given instance
     * @param instance the instance to be initialized
     */
    public PaneInitializer(P instance) {
        super(instance);
    }

    /**
     * Does the same as {@link PaneInitializer#PaneInitializer(Pane)} but with a
     * {@link Supplier} instead
     * @param supplier the {@link Supplier}, which can even be an object of this class
     */
    public PaneInitializer(Supplier<P> supplier) {
        super(supplier);
    }

    /**
     * Adds a child to the {@link Initializer#instance}
     * @param child the node to be added
     * @return this initializer
     */
    public PaneInitializer<P> addChild(Node child) {
        instance.getChildren().add(child);
        return this;
    }

    /**
     * Adds the child returned by the given {@link Supplier} to the {@link Initializer#instance}
     * @param child the child {@link Supplier} which can be even an {@link Initializer} since it
     *              implements that interface
     * @return this {@link PaneInitializer}
     * @param <N> any JavaFX {@link Node} type
     */
    public <N extends Node> PaneInitializer<P> addChild(Supplier<N> child) {
        return addChild(child.get());
    }
}
