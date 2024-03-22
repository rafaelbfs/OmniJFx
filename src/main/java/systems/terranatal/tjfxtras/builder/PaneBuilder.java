package systems.terranatal.tjfxtras.builder;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.function.Supplier;

public class PaneBuilder<P extends Pane> extends Builder<P> {
    public PaneBuilder(P instance) {
        super(instance);
    }

    public PaneBuilder(Supplier<P> supplier) {
        super(supplier);
    }

    public PaneBuilder<P> addChild(Node child) {
        instance.getChildren().add(child);
        return this;
    }

    public <N extends Node> PaneBuilder<P> addChild(Supplier<N> child) {
        return addChild(child.get());
    }
}
