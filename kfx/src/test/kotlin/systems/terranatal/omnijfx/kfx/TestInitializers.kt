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

package systems.terranatal.omnijfx.kfx

import javafx.beans.value.ChangeListener
import javafx.scene.Scene
import javafx.scene.control.RadioButton
import javafx.scene.control.TextField
import javafx.scene.layout.GridPane
import javafx.stage.Stage
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.testfx.framework.junit5.ApplicationExtension
import org.testfx.framework.junit5.Start
import systems.terranatal.omnijfx.internationalization.NumericParsingUtils
import systems.terranatal.omnijfx.kfx.extensions.RadioButtons.radioButton
import systems.terranatal.omnijfx.kfx.extensions.addRadioButton
import systems.terranatal.omnijfx.kfx.extensions.apply
import systems.terranatal.omnijfx.kfx.extensions.toggleGroup
import kotlin.math.pow

@ExtendWith(ApplicationExtension::class)
class TestInitializers {

  lateinit var monthly: RadioButton
  lateinit var yearly: RadioButton

  lateinit var monthlyValue: TextField
  lateinit var yearlyValue: TextField

  private fun makeScene(): Scene {
    monthlyValue.textProperty().addListener(ChangeListener { observable, oldValue, newValue ->
      if (monthly.isSelected && NumericParsingUtils.isParseable(newValue)) {
        var rate = newValue.toDouble()
        rate = ((1 + rate/100).pow(12) - 1) * 100
        yearlyValue.text = "%.2f".format(rate)
      }
    })

    yearlyValue.textProperty().addListener(ChangeListener { observable, oldValue, newValue ->
      if (yearly.isSelected && NumericParsingUtils.isParseable(newValue)) {
        var rate = newValue.toDouble()
        rate = ((1 + rate/100).pow(1.0/12.0) - 1) * 100
        monthlyValue.text = "%.2f".format(rate)
      }
    })

    val toggleGroup = toggleGroup { addRadioButton(monthly); addRadioButton(yearly); monthly.isSelected = true }

    val grid = GridPane().apply {
      addColumn(0, monthly, monthlyValue)
      addColumn(1, yearly, yearlyValue)
      hgap = 6.0
      vgap = 4.0
    }

    return Scene(grid, grid.width, grid.height)
  }

  @Start
  fun start(stage: Stage) {
    monthly = radioButton("Monthly Interest (%)")
    yearly = radioButton("Yearly Interest (%)")
    monthlyValue = TextField().apply {
      disableProperty().bind(monthly.selectedProperty().not())
    }
    yearlyValue = TextField().apply {
      disableProperty().bind(yearly.selectedProperty().not())
    }

    stage.scene = makeScene()
    stage.isResizable = true
    stage.show()
  }

  @Test
  fun testComponentsInitialization() {
    Assertions.assertTrue(monthly.isSelected)
    monthlyValue.text = "1.05"
    Assertions.assertEquals("13.35", yearlyValue.text)

    yearly.isSelected = true

    yearlyValue.text = "6.18"
    Assertions.assertEquals("0.50", monthlyValue.text)
  }
}