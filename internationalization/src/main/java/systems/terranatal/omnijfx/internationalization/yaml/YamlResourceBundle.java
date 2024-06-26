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

package systems.terranatal.omnijfx.internationalization.yaml;

import org.yaml.snakeyaml.Yaml;
import systems.terranatal.omnijfx.internationalization.ResourceBundle;

import java.io.Reader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Loads contents of a YAML file into the {@link Map} in {@link ResourceBundle#resources}.
 * To work in the same way expected from its ancestor class, the hierarchical structure of the yaml contents
 * are flattened by fully qualifying each key with dots `.` similarly to Java's package naming.
 * A YAML content like the one below
 * <pre>
 *   keys:
 *     level1:
 *       nested:
 *         key121: foo
 *         key122: bar
 *       key11: baz
 *     key1: buz
 * </pre>
 * will become single {@link Map} with the following {@code key -> value} pairs, quotes were omitted.
 * <pre>
 *   {@code keys.level1.nested.key121 -> foo}
 *   {@code keys.level1.nested.key122 -> bar}
 *   {@code keys.level1.key11 -> baz}
 *   {@code keys.key1 -> buz}
 * </pre>
 */
public class YamlResourceBundle extends ResourceBundle {
  /**
   * SnakeYAML emits the parsed content as nested {@link java.util.Collection}s and/or {@link Map}
   * to make it compatible with our specification, this recursive method will flatten whatever Map it
   * finds until it reaches scalar attributes by doing as follows, according to the actual type of the
   * <b>node</b> parameter:
   * <ul>
   *   <li>{@code node} is a {@link String} {@code ->} returns a {@link Map} with a single entry whose
   *      key is {@code parent} and value is {@code node}
   *   </li>
   *   <li>
   *     {@code node} is a {@link Map} {@code ->} iterates over all its entries ({@code key -> value })
   *     calling {@code flatten} with the entry value as {@code node} parameter, and passing next
   *     {@code parent} parameter as {@code currentParent.[key]} if and only if the current invocation's
   *     {@code parent} does not end with the current {@code key}, otherwise it passes the current
   *     {@code parent} verbatim
   *   </li>
   *   <li>
   *     {@code node} is an {@link Iterable} {@code ->} iterate over each of its elements {@code el} calling
   *     {@code flatten} with the element value and the current parent
   *   </li>
   *   <li>otherwise {@code ->} returns an empty {@link Map}</li>
   * </ul>
   *
   * @param node the current node being processed
   * @param parent key in the current invocation
   * @return a {@link Map} populated according to the specification above
   */
  protected static Map<String, String> flatten(Object node, String parent) {
    var map = new HashMap<String, String>();
    if (node instanceof String value) {
      map.put(parent, value);
      return map;
    }
    if (node instanceof Map<?,?> children) {
      children.forEach((k, v) -> {
        if (parent.endsWith(k.toString())) {
          map.putAll(flatten(v, parent));
        } else {
          var dot = !parent.isBlank() ? "." : "";
          map.putAll(flatten(v, parent + dot + k));
        }
      });
    } else if (node instanceof Iterable<?> it) {
      it.forEach(el -> map.putAll(flatten(el, parent)));
    }
    return map;
  }

  /**
   * Constructs a {@link YamlResourceBundle} with all the items laid out as a single flat {@link Map}
   *
   * @param reader {@link Reader} to the YAML resource bundle
   * @param locale used in the superclass
   * @param charset used in the superclass
   */
  public YamlResourceBundle(Reader reader, Locale locale, Charset charset) {
    super(new HashMap<>(), locale, charset);

    var yml = new Yaml();
    var it = yml.loadAll(reader);
    for (var node: it) {
      resources.putAll(flatten(node, ""));
    }
  }
}
