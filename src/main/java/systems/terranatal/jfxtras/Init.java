package systems.terranatal.jfxtras;

import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Init<T extends Node> extends Supplier<T> {
    default T with(Consumer<T> initializer) {
        var instance = get();
        initializer.accept(instance);
        return instance;
    }

    default Aggregator<T> aggregator() {
        return new Aggregator<>(get());
    }

    default Aggregator<T> aggregateWith(Consumer<T> consumer) {
        return new Aggregator<>(this.with(consumer));
    }

    static PaneInit<HBox> hbox() {
        return HBox::new;
    }

    static HBox hbox(Consumer<HBox> initializer) {
        return hbox().with(initializer);
    }

    static PaneInit<VBox> vbox() {
        return VBox::new;
    }

    static VBox vbox(Consumer<VBox> initializer) {
        return vbox().with(initializer);
    }

    static PaneAggregator<HBox> withLabelOnLeft(Label label) {
        return new PaneAggregator<>(new HBox()).addChild(label);
    }

    static PaneAggregator<HBox>  withLabelOnLeft(String labelText) {
        return withLabelOnLeft(new Label(labelText));
    }

    static Init<Slider> slider(double minimum, double maximum, double initial) {
        return () -> new Slider(minimum, maximum, initial);
    }

    static Init<TextField> textField() {
        return TextField::new;
    }

    default  <V, P> Aggregator<T> listeningTo(ObservableValue<V> source, Function<T, Property<P>> target,
                                                              Function<V, P> map) {
        var node = this.get();
        source.addListener((src, old, newv) -> target.apply(node).setValue(map.apply(newv)));
        return new Aggregator<>(node);
    }

    static <U extends Pane> PaneAggregator<U> layout(Supplier<U> supplier) {
        return new PaneAggregator<>(supplier.get());
    }

}
