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

package systems.terranatal.omnijfx.internationalization;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

public class TestParsingUtils {

  @ParameterizedTest
  @ArgumentsSource(ParsingStringsProvider.class)
  public void testNumericParsingUtils(Optional<Locale> maybeLocale, String parameter, String expectedResult,
                                      boolean expectedParseability) {
    var result = "";
    var isParseable = false;

    if (maybeLocale.isPresent()) {
      result = NumericParsingUtils.stripGroupingSymbols(parameter,
          Character.toString(DecimalFormatSymbols.getInstance(maybeLocale.get()).getDecimalSeparator()));
      isParseable = NumericParsingUtils.isParseable(parameter, maybeLocale.get());
    } else {
      result = NumericParsingUtils.stripGroupingSymbols(parameter);
      isParseable = NumericParsingUtils.isParseable(parameter);
    }

    Assertions.assertEquals(expectedResult, result);
    Assertions.assertEquals(expectedParseability, isParseable);
  }

  public static class ParsingStringsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
      var platformDecimalSeparator =  Character.toString(DecimalFormatSymbols.getInstance().getDecimalSeparator());
      var sampleNr1 = "123" + platformDecimalSeparator + "456";
      var badNr1 = "123" + platformDecimalSeparator + "456" + platformDecimalSeparator + "789";
      var scientificNr = "456%s321E+0%s987".formatted(platformDecimalSeparator, platformDecimalSeparator);
      return Stream.of(
          Arguments.of(Optional.of(Locale.GERMANY), "123.123,004", "123123,004", true),
          Arguments.of(Optional.empty(), "123 456 789", "123456789", true),
          Arguments.of(Optional.empty(), sampleNr1, sampleNr1, true),
          Arguments.of(Optional.empty(), badNr1, badNr1, false),
          Arguments.of(Optional.of(Locale.US), "123,456,789.002", "123456789.002", true),
          Arguments.of(Optional.of(Locale.US), "123'456'789", "123456789", true),
          Arguments.of(Optional.empty(), scientificNr, scientificNr, true),
          Arguments.of(Optional.empty(), scientificNr.toLowerCase(), scientificNr.toLowerCase(), true),
          Arguments.of(Optional.of(Locale.GERMANY), "123.986,009E-3,14", "123986,009E-3,14", true),
          Arguments.of(Optional.of(Locale.US), "123,986.009e-3.14", "123986.009e-3.14", true),
          Arguments.of(Optional.of(Locale.GERMANY), "123,123,123e-3,55,5", "123,123,123e-3,55,5", false),
          Arguments.of(Optional.of(Locale.US), "123.123.123e-3.55.5", "123.123.123e-3.55.5", false)
      );
    }
  }

}
