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

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Supplier;

/**
 * Abstract class which holds internationalized resources that can be accessed by their associated keys.
 */
public abstract class ResourceBundle {

  /**
   * Locale of the original resource file
   */
  protected final Locale locale;
  /**
   * Map containing the internationalized resources
   */
  protected final Map<String, String> resources;

  /**
   * The {@link Charset} in which the original file was encoded
   */
  protected final Charset charset;

  /**
   * The main constructor
   *
   * @param resources the final {@link Map} with which this class is initialized
   * @param locale the locale
   * @param charset the charset
   */
  public ResourceBundle(Map<String, String> resources, Locale locale, Charset charset) {
    this.locale = locale;
    this.resources = resources;
    this.charset = charset;
  }

  /**
   * Retrieves the locale for this resource bundle
   * @return the locale
   */
  public Locale getLocale() {
    return locale;
  }

  /**
   * Null-safe retrieval of a localized message
   * @param key the search key
   * @return an {@link Optional} containing the localized message found for the key
   * or an empty {@link Optional} if the key is null or no message is found
   */
  public Optional<String> optionalString(String key) {
    return Objects.nonNull(key) ? Optional.ofNullable(resources.get(key)) : Optional.empty();
  }

  /**
   * Gets a localized message for the given resource key. This method is not null-safe it will
   * throw a {@link NoSuchElementException} when no string is found for the given key
   * or the key parameter is null
   * @param key the resource key.
   * @return the message
   *
   */
  public String stringFor(String key) {
    return optionalString(key).orElseThrow(supplyException(key));
  }

  private Supplier<RuntimeException> supplyException(String key) {
    return () -> new NoSuchElementException("Key %s not found".formatted(key));
  }

  /**
   * Class specialized in loading contents in the Java {@code properties} layout.
   */
  public static class PropertyResourceBundle extends ResourceBundle {
    /**
     * Parses the {@code .properties} content and adds each entry at once to the {@link ResourceBundle#resources}
     * map. Entries whose key parts are not in the Java identifier syntax are not added to {@link ResourceBundle#resources}
     *
     * @param reader the reader to the {@code .properties} file contents
     * @param locale the {@link Locale} passed to the superclass
     * @param charset he {@link Charset} passed to the superclass
     * @throws IOException when reading the file contents fails
     */
    public PropertyResourceBundle(Reader reader, Locale locale, Charset charset) throws IOException {
      super(new HashMap<>(), locale, charset);
      var p = new Properties();
      p.load(reader);
      p.forEach((key, value) -> {
        if (key.toString().matches("^[a-z]\\w*(\\.[a-z]\\w*)*")) {
          resources.put(key.toString(), value.toString());
        }
      });
    }
  }
}
