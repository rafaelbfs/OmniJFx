package systems.terranatal.omnijfx.kfx.extensions

import javafx.scene.Node
import javafx.scene.layout.Pane

fun <N: Node> N.apply(initializer: N.() -> Unit): N {
  initializer(this)
  return this
}

fun <P: Pane, N: Node> P.child(node: N): P {
  children.add(node)
  return this
}
