package systems.terranatal.tfxtras.kfx

import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import systems.terranatal.tfxtras.kfx.extensions.apply
import systems.terranatal.tfxtras.kfx.extensions.child

/**
 * Object with helper methods to instantiate common components
 */
object Helpers
{
  /**
   * Generic function to create any JavaFX component and initialize it
   * @param n the {@link Node} instance
   * @param init the initializer function
   *
   * @return the node initialized wit the passed function
   */
  fun <N: Node> node(n: N, init: N.() -> Unit): N = n.apply(init)

  /**
   * Creates a button with a text and custom initialization
   * @param label the text on the button
   * @param init the initializer function
   *
   * @return a [Button] with the text and with [init] logic applied
   */
  fun button(label: String, init: Button.() -> Unit): Button = Button(label).apply(init)
}

object FxTextField {
  /**
   * Creates a text box with the [initialText]
   * @param initialText the initial text in the component
   * @param init the initializer function
   *
   * @return a [TextField] with the initial text set and [init] applied to it
   */
  fun withInitialText(initialText: String, init: TextField.() -> Unit) =
    TextField().apply(init)

  /**
   * Creates a text box with a label on its left and executes some logic
   * @param title the initial text in the component
   * @param init the initializer function
   *
   * @return an [HBox] with the label and the text box with [init] applied to it
   */
  fun withLabelOnTheLeft(title: String, init: TextField.() -> Unit): HBox =
    Layouts.hbox { child(Label(title)); child(TextField().apply(init)) }

  /**
   * Creates a text box with a label on its left and executes some logic
   * @param title the initial text in the component
   * @param init the initializer function
   *
   * @return a [VBox] with the label on top and the text box with [init] applied to it below
   */
  fun withLabelAbove(title: String, init: TextField.() -> Unit): VBox =
    Layouts.vbox { child(Label(title)); child(TextField().apply(init)) }
}

object Layouts {
  fun <P: Pane> pane(pane: P, init: P.() -> Unit): P =
    pane.apply(init)

  fun hbox(init: HBox.() -> Unit): HBox = pane(HBox(), init)

  fun vbox(init: VBox.() -> Unit): VBox = pane(VBox(), init)
}
