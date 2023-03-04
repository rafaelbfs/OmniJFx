package systems.terranatal.jfxtras;

import javafx.scene.layout.Pane;

import java.util.function.Consumer;

public interface PaneInit<T extends Pane> extends Init<T> {
    @Override
    default PaneAggregator<T> aggregator() {
        return new PaneAggregator<>(get());
    }

    @Override
    default PaneAggregator<T> aggregateWith(Consumer<T> consumer) {
        return new PaneAggregator<>(this.with(consumer));
    }

}
