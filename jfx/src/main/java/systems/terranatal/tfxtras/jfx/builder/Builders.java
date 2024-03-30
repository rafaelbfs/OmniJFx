package systems.terranatal.tfxtras.jfx.builder;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Utility class containing methods to create Node and Pane builders
  */

public interface Builders {

    /**
     * The base method to create Node builder where the node instance is given and a builder is returned.
     * Do not use it to build any instance of {@link Pane}, for that end there is {@link Builders#pane(Pane)}.
     *
     * @param node the node instance
     * @return a builder containing the node
     * @param <N> the Node type
     */
    static <N extends Node> Initializer<N> node(N node) {
        return new Initializer<>(node);
    }

    /**
     * The general method to create a Pane initializer, accepting any instance of {@link Pane} and returning
     * an initializer for it.
     *
     * @param pane the Pane instance
     * @return the initializer for the given pane
     * @param <P> the specific type of the Pane
     */
    static <P extends Pane> PaneInitializer<P> pane(P pane) {
        return new PaneInitializer<>(pane);
    }

    /**
     * Initializes a label
     * @param lbl the label text
     * @return an initializer for the label
     */
    static Initializer<Label> label(String lbl) {
        return new Initializer<>(new Label(lbl));
    }

    /**
     * Creates a text field with an initial text
     * @param initialText an initial text
     * @return an {@link Initializer} with the text field
     */
    static Initializer<TextField> textField(String initialText) {
        return new Initializer<>(new TextField(initialText));
    }

    /**
     * Creates an initializer containing a {@link Button} with a text
     * @param btnText the button text
     * @return an {@link Initializer} with the {@link Button}
     */
    static Initializer<Button> button(String btnText) {
        return node(new Button(btnText));
    }

    /**
     * Creates an {@link Initializer} containing a {@link Slider} with some presets
     * that can be passed as parameters
     * @param minimum the lowest value the {@link Slider} can represent
     * @param maximum the highest values this {@link Slider}
     * @param initial the initial value which must be between the 'minimum' and 'maximum' values
     * @return an {@link Initializer} with the {@link Slider}
     */
    static Initializer<Slider> slider(double minimum, double maximum, double initial) {
        return node(new Slider(minimum, maximum, initial));
    }

    /**
     * Specialized method from {@link Builders#pane(Pane)} to create a {@link HBox} initializer
     * @return an initializer for {@link HBox}
     */
    static PaneInitializer<HBox> hBox() {
        return pane(new HBox());
    }

    /**
     * Creates an initializer for {@link HBox} with a label already added as child.
     *
     * @param lblText the {@link String} containing the text for the label
     * @return the {@link HBox} initializer with a {@link Label} already added
     */
    static PaneInitializer<HBox> labeledRow(String lblText) {
        return hBox().addChild(new Label(lblText));
    }

    /**
     * Specialized method from {@link Builders#pane(Pane)} to create a {@link VBox} initializer
     * @return an initializer for {@link VBox}
     */
    static PaneInitializer<VBox> vBox() {
        return pane(new VBox());
    }
}
