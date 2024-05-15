package systems.terrnatal.omnijfx.internationalization.yaml;

import org.yaml.snakeyaml.Yaml;
import systems.terrnatal.omnijfx.internationalization.ResourceBundle;

import java.io.Reader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class YamlResourceBundle extends ResourceBundle {
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

  public YamlResourceBundle(Reader reader, Locale locale, Charset charset) {
    super(new HashMap<>(), locale, charset);

    var yml = new Yaml();
    var it = yml.loadAll(reader);
    for (var node: it) {
      resources.putAll(flatten(node, ""));
    }
  }
}
