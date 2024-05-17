package systems.terranatal.omnijfx.internationalization.yaml;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

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
    return yaml.load(reader);
  }
}
