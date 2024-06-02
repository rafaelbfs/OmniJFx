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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

public class TestParsingUtils {
  private static final Random RANDOM = new Random();

  @ParameterizedTest
  @ArgumentsSource(ParsingStringsProvider.class)
  public void testNumericParsingUtils(Optional<Locale> maybeLocale, String parameter, boolean hasGrouping,
                                      boolean expectedParseability) {
    var result = false;
    var isParseable = false;

    if (maybeLocale.isPresent()) {
      result = NumericParsingUtils.hasGrouping(parameter, maybeLocale.get());
      isParseable = NumericParsingUtils.isParseable(parameter, maybeLocale.get());
    } else {
      result = NumericParsingUtils.hasGrouping(parameter);
      isParseable = NumericParsingUtils.isParseable(parameter);
    }

    Assertions.assertEquals(hasGrouping, result);
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
          Arguments.of(Optional.of(Locale.GERMANY), "123.123,004", true, true),
          Arguments.of(Optional.of(Locale.CHINA), generateRandom(Locale.CHINA, 3), true, true),
          Arguments.of(Optional.empty(), "123 456 789", false, false),
          Arguments.of(Optional.empty(), sampleNr1, false, true),
          Arguments.of(Optional.empty(), badNr1, false, false),
          Arguments.of(Optional.of(Locale.US), "123,456,789.002", true, true),
          Arguments.of(Optional.of(Locale.US), "123'456'789", false, false),
          Arguments.of(Optional.empty(), scientificNr, false, true),
          Arguments.of(Optional.empty(), scientificNr.toLowerCase(), false, true),
          Arguments.of(Optional.of(Locale.GERMANY), "123.986,009E-3,14", true, true),
          Arguments.of(Optional.of(Locale.US), "12,986.009e-3.14", true, true),
          Arguments.of(Optional.of(Locale.GERMANY), "123,123,123e-3,55,5", false, false),
          Arguments.of(Optional.of(Locale.US), "123.123.123e-3.55.5", false, false),
          Arguments.of(Optional.of(Locale.GERMANY), "12.45.89", true, false)
      );
    }
  }


  public static int randomGroup(Locale locale) {
    var platformGSize = ((DecimalFormat) DecimalFormat.getInstance(locale)).getGroupingSize();
    var number = Double.valueOf(Math.pow(10.0, platformGSize)).intValue();

    return RANDOM.nextInt(number/10, number);
  }

  public static String generateRandom(Locale locale, int numberOfGroups) {
    if (numberOfGroups < 1) {
      return String.valueOf(RANDOM.nextInt(1, 10000000));
    }

    var sb = new StringBuilder();
    sb.append(String.valueOf(randomGroup(locale)));
    var grouping = String.valueOf(DecimalFormatSymbols.getInstance(locale).getGroupingSeparator());

    for (int i = 1; i < numberOfGroups; i++) {
      sb.append(grouping + randomGroup(locale));
    }
    return sb.toString();
  }
}
