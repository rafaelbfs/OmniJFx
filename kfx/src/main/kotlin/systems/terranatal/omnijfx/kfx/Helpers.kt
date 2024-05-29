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

package systems.terranatal.omnijfx.kfx

import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import systems.terranatal.omnijfx.kfx.Helpers.node
import systems.terranatal.omnijfx.kfx.extensions.apply
import systems.terranatal.omnijfx.kfx.extensions.child

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
  fun button(label: String, init: Button.() -> Unit): Button = node(Button(label), init)

  /**
   * Creates a label with a text
   * @param lbl the text on the label
   * @return a [Label] with the given text
   */
  fun label(lbl: String): Label = Label(lbl)

  /**
   * Creates a label with a text and extra init logic
   * @param lbl the text on the label
   * @param init the extra logic in the label initialization
   * @return a [Label] with the given text
   */
  fun label(lbl: String, init: Label.() -> Unit): Label = node(Label(lbl), init)
}

object FxTextField {
  /**
   * Creates a text box with the [initialText]
   * @param initialText the initial text in the component
   * @param init the initializer function
   *
   * @return a [TextField] with the initial text set and [init] applied to it
   */
  fun withInitialText(initialText: String, init: TextField.() -> Unit) = node(TextField(initialText), init)

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

/**
 * Object to initialize a [Pane] and any of its subclasses
 */
object Layouts {
  /**
   * Initializes any subclass of [Pane].
   * @param pane the instance of [Pane] to be initialized
   * @param init the initializing function
   *
   * @return the same [pane] passed as argument with its fields initialized by the [init] function
   */
  fun <P: Pane> pane(pane: P, init: P.() -> Unit): P = pane.apply(init)

  /**
   * Specialized method to initialize an [HBox]
   * @param init the initializer function
   *
   * @return an [HBox] initialized by [init]
   */
  fun hbox(init: HBox.() -> Unit): HBox = pane(HBox(), init)

  /**
   * Specialized method to initialize a [VBox]
   * @param init the initializer function
   *
   * @return a [VBox] initialized by [init]
   */
  fun vbox(init: VBox.() -> Unit): VBox = pane(VBox(), init)

  /**
   * Specialized method to initialize a [StackPane]
   * @param init the initializer function
   *
   * @return a [StackPane] initialized by [init]
   */
  fun stackPane(init: StackPane.() -> Unit): StackPane = pane(StackPane(), init)
}
