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

package systems.terranatal.omnijfx.jfx.builder;

import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import systems.terranatal.omnijfx.internationalization.NumericParsingUtils;
import systems.terranatal.omnijfx.jfx.datautils.Converters;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.function.Consumer;
import java.util.function.Function;

@ExtendWith(ApplicationExtension.class)
public class TestGridAndRadioGroups {

  private RadioButton monthly, yearly;
  private TextField monthlyRate, yearlyRate;
  private final NumberFormat decimalFormat = NumberFormat.getInstance();

  private final StringConverter<Double> stringToDouble = Converters.makeStrConverter(
      str -> NumericParsingUtils.parseUnchecked(decimalFormat, str).doubleValue(),
          decimalFormat::format);
  private final Function<Double, Double> monthlyToYearly = mRate ->
      (Math.pow(mRate/100.0 + 1.0, 12.0) - 1.0) * 100.0;
  private final Function<Double, Double> yearlyToMonthly = yRate ->
      (Math.pow(yRate/100.0 + 1.0, 1.0/12.0) - 1.0) * 100.0;

  private ChangeListener<String> makeChangeListener(
      TextField target, RadioButton expected,
      Function<Double, Double> conversion) {
    return (obs, oldVal, newVal) -> {
      if (expected.isSelected() && NumericParsingUtils.isParseable(newVal)) {
        target.setText(stringToDouble.toString(conversion.apply(
            stringToDouble.fromString(newVal))));
      }
    };
  }

  @Start
  public void start(Stage stage) {
    decimalFormat.setMaximumFractionDigits(2);
    decimalFormat.setMinimumFractionDigits(2);
    monthly = Builders.radioButton("Monthly Rate (%)").get();
    yearly = Builders.radioButton("Yearly Rate (%)").get();
    monthlyRate = Builders.textField("")
        .bind(monthly.selectedProperty().not(), TextInputControl::disableProperty).get();
    yearlyRate = Builders.textField("")
        .bind(monthly.selectedProperty().not(), TextInputControl::disableProperty).get();
    monthlyRate.textProperty().addListener(
        makeChangeListener(yearlyRate, monthly, monthlyToYearly));
    yearlyRate.textProperty().addListener(
        makeChangeListener(monthlyRate, yearly, yearlyToMonthly));
    var radioGroup = new ToggleGroupBuilder<>()
        .addToggle(monthly)
        .addToggle(yearly).get();

    stage.setScene(makeTestScene());
    stage.show();
  }

  private Scene makeTestScene() {

    var monthIntzr = Builders.node(monthly);
    var monthlyRateIntzr = Builders.node(monthlyRate);
    var grid = Builders.gridPane()
            .addColumn(0, monthIntzr, monthlyRateIntzr)
            .addColumn(1, yearly, yearlyRate).get();

    return new Scene(grid, grid.getWidth(), grid.getHeight());
  }

  @Test
  public void testValues(FxRobot robot) {

    var separator = Character.toString(DecimalFormatSymbols.getInstance().getDecimalSeparator());
    monthly.setSelected(true);
    monthlyRate.setText(stringToDouble.toString(1.05d));
    Assertions.assertEquals(stringToDouble.toString(13.35d), yearlyRate.getText());

    yearly.setSelected(true);
    yearlyRate.setText(stringToDouble.toString(6.18d));
    Assertions.assertFalse(monthly.isSelected());
    Assertions.assertEquals(stringToDouble.toString(0.50d), monthlyRate.getText());
  }
}
