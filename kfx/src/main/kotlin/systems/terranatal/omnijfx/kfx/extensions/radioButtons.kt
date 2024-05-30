/*
 * Copyright (c) 2024, Rafael Barros Felix de Sousa @ Terranatal Systems
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
 *     * Neither the name of omnijfx nor the names of its contributors
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

import javafx.scene.control.RadioButton
import javafx.scene.control.ToggleGroup

/**
 * Instantiates a [ToggleGroup] and applies logic in the [init] block where the [RadioButton]s can
 * be added to it by using [addRadioButton]
 *
 * @param init the initialization block
 *
 * @return the instantiated [ToggleGroup] with [init] applied to it
 */
fun toggleGroup(init: ToggleGroup.() -> Unit): ToggleGroup {
  val tg = ToggleGroup()
  init(tg)
  return tg
}

/**
 * Extension method which allows the user to add a [RadioButton] directly into the receiving [ToggleGroup].
 *
 * @param rb the [RadioButton] to be added to **this** [ToggleGroup]
 */
fun ToggleGroup.addRadioButton(rb: RadioButton) {
  rb.toggleGroup = this
}

object RadioButtons {

  fun radioButton(text: String) = RadioButton(text)

  fun radioButton(text: String, init: RadioButton.() -> Unit): RadioButton {
    val rb = radioButton(text)
    init(rb)
    return rb
  }

  operator fun invoke(init: RadioButton.() -> Unit): RadioButton {
    val rb = RadioButton()
    init(rb)
    return rb
  }
}
