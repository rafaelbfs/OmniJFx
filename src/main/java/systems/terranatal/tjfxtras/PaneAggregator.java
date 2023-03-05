package systems.terranatal.tjfxtras;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public sealed class PaneAggregator<T extends Pane> extends Aggregator<T> permits VBoxAggregator {
    protected PaneAggregator(@NotNull T node) {
        super(node);
    }

    private PaneAggregator<T> addChildUnsafe(Node child) {
        node.getChildren().add(child);
        return this;
    }

    public PaneAggregator<T> addChild(@NotNull Node child) {
        validateState();
        return addChildUnsafe(child);
    }

    public <U extends Node> PaneAggregator<T> addChild(Supplier<U> childSupplier) {
        validateState();
        return addChildUnsafe(childSupplier.get());
    }


}
