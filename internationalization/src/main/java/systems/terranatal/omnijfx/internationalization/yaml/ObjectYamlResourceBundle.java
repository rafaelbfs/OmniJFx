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
 *     * Neither the name of com.maddyhome.idea.copyright.pattern.ProjectInfo@c6da320 nor the names of its contributors
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
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.BeanAccess;

import java.io.Reader;
import java.util.function.Supplier;

/**
 * Class that loads YAML resources as a plain-old Java object
 *
 * @param <T> the type of the object to which the file contents will be assigned
 */
public class ObjectYamlResourceBundle<T> {
  /**
   * Represents the object on which SnakeYAML will assign the parsed contents.
   */
  private final Constructor constructor;

  /**
   * The main constructor.
   * Initializes this class with a {@link Constructor} that describes the target type T of this class
   *
   * @param constructor the description of the type on which SnakeYAML will load the parsed contents.
   */
  public ObjectYamlResourceBundle(Constructor constructor) {
    this.constructor = constructor;
  }

  /**
   * Does the same as the previous constructor but receives a {@link Constructor} {@link Supplier}, this gives
   * the user a convenience for initializing all the configuration and type descriptors in a single place, ie.
   * the {@link Supplier} block.
   *
   * @param constructor a supplier that returns the SnakeYAML {@link Constructor}
   */
  public ObjectYamlResourceBundle(Supplier<Constructor> constructor) {
    this.constructor = constructor.get();
  }

  /**
   * Loads the YAML content into an object of type {@code T}.
   * This method rethrows all exceptions thrown by SnakeYAML's loader.
   *
   * @param reader to the resource file
   * @return the object with the parsed contents
   */
  public T loadObject(Reader reader) {
    var yaml = new Yaml(constructor);
    yaml.setBeanAccess(BeanAccess.FIELD);
    return yaml.load(reader);
  }
}
