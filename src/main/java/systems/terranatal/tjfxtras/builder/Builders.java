package systems.terranatal.tjfxtras.builder;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public interface Builders {
    static <N extends Node> Builder<N> node(N node) {
        return new Builder<>(node);
    }

    static <P extends Pane> PaneBuilder<P> pane(P pane) {
        return new PaneBuilder<>(pane);
    }

    static Builder<Label> label(String lbl) {
        return new Builder<>(new Label(lbl));
    }

    static Builder<TextField> textField(String initialText) {
        return new Builder<>(new TextField(initialText));
    }

    static Builder<Button> button(String btnText) {
        return node(new Button(btnText));
    }

    static Builder<Slider> slider(double minimum, double maximum, double initial) {
        return node(new Slider(minimum, maximum, initial));
    }

    static PaneBuilder<HBox> hBox() {
        return pane(new HBox());
    }

    static PaneBuilder<HBox> labeledRow(String lblText) {
        return pane(new HBox()).addChild(new Label(lblText));
    }

    static PaneBuilder<VBox> vBox() {
        return pane(new VBox());
    }
}
