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

package systems.terranatal.omnijfx.kfx.extensions

import javafx.scene.Node
import javafx.scene.layout.Pane

/**
 * Applies [initializer] to the [Node] receiver.
 *
 * @param initializer the initializer
 *
 * @return the receiving [Node] itself so this function can be chained just after a constructor call
 */
fun <N: Node> N.apply(initializer: N.() -> Unit): N {
  initializer(this)
  return this
}

/**
 * Extension method for any subclass of [Pane] to add a child to itself
 *
 * @param node the child [Node] to be added
 *
 * @return the receiver [Pane] itself
 */
fun <P: Pane, N: Node> P.child(node: N): P {
  children.add(node)
  return this
}

/**
 * Applies [initializer] to the [Node] receiver, since it overloads the invocation operator
 * the user can simply do `nodeInstance {initialization logic}`.
 *
 * @param initializer the initializer
 */
operator fun <N: Node> N.invoke(initializer: N.() -> Unit) = initializer(this)