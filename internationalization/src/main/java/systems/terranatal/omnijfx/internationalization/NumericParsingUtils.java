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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public interface NumericParsingUtils {

  /**
   * Set containing the most common grouping characters in the world
   */
  Set<String> NUMERIC_GROUPING_TOKENS = Set.of("\\.", "'", "\\,", "\\s", "_");

  /**
   * Removes all non-numeric characters, except the {@link java.util.Locale}'s decimal separator.
   * It will not care if the grouping has the correct decimal places according to the locale
   * e.g. 3 places for Western countries and 4 in East Asian countries.
   *
   * @param text the text to be processed
   * @param decimalSeparator the decimal separator which will be kept
   *
   * @return the processed string with all special characters, except the decimal separator, removed
   */
  static String stripGroupingSymbols(String text, String decimalSeparator) {
    var others = NUMERIC_GROUPING_TOKENS.stream().filter(t -> !t.equals("\\" + decimalSeparator));
    var regEx = others.collect(Collectors.joining("|"));

    return text.replaceAll(regEx, "");
  }

  /**
   * Checks if the given text can be parsed to a number
   *
   * @param text the string to be tested
   * @param locale the {@link Locale} of which the decimal separator is retrieved
   * @return true if the string can be parsed to a number, false otherwise
   */
  static boolean isParseable(String text, Locale locale) {
    var symbols = DecimalFormatSymbols.getInstance(locale);
    var separator = Character.toString(symbols.getDecimalSeparator());
    var grouping = Character.toString(DecimalFormatSymbols.getInstance(locale).getGroupingSeparator());
    final var scientific = "%s([Ee]%s)?";

    if (!text.contains(grouping)) {
      var localizedRational = "[\\+\\-]?\\d+(\\%s\\d+)?".formatted(separator);
      var finalRegex = scientific.formatted(localizedRational, localizedRational);
      return text.matches(finalRegex);
    }
    var gsize = ((DecimalFormat) DecimalFormat.getInstance(locale)).getGroupingSize();
    var rationalWithGrouping = "[\\+\\-]?\\d{1,%d}(\\%s\\d{%d})*(\\%s\\d+)?"
            .formatted(gsize, grouping, gsize, separator);
    var finalRegex = scientific.formatted(rationalWithGrouping, rationalWithGrouping);
    return text.matches(finalRegex);
  }

  /**
   * Checks if the given text can be parsed to a number, using the default platform locale
   *
   * @param text the string to be tested
   * @return true if the string can be parsed to a number, false otherwise
   */
  static boolean isParseable(String text) {
    return isParseable(text, Locale.getDefault());
  }

  /**
   * Removes all non-numeric characters, except the platform's default decimal separator
   * @param text the text to be processed
   *
   * @return the processed string with all special characters, except the decimal separator, removed
   */
  static String stripGroupingSymbols(String text) {
    return stripGroupingSymbols(text, Character.toString(DecimalFormatSymbols.getInstance().getDecimalSeparator()));
  }

  static boolean hasGrouping(String text, Locale locale) {
    var symbols = DecimalFormatSymbols.getInstance(locale);
    var grouping = Character.toString(symbols.getGroupingSeparator());

    return text.contains(grouping);
  }

  static boolean hasGrouping(String text) {
    return hasGrouping(text, Locale.getDefault());
  }

  static Number parseUnchecked(NumberFormat formatter, String text) {
      try {
          return formatter.parse(text);
      } catch (ParseException e) {
          throw new IllegalArgumentException("Could not parse " + text + " as a number.", e);
      }
  }
}
