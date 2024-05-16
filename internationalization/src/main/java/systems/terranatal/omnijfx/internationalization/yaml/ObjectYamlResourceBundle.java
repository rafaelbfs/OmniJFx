package systems.terranatal.omnijfx.internationalization.yaml;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.Reader;
import java.util.function.Supplier;

public class ObjectYamlResourceBundle<T> {
  private final Constructor constructor;

  public ObjectYamlResourceBundle(Constructor constructor) {
    this.constructor = constructor;
  }

  public ObjectYamlResourceBundle(Supplier<Constructor> constructor) {
    this.constructor = constructor.get();
  }

  public T loadObject(Reader reader) {
    var yaml = new Yaml(constructor);
    return yaml.load(reader);
  }
}
