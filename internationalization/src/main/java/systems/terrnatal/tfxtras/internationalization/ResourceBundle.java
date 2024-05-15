package systems.terrnatal.omnijfx.internationalization;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Supplier;

public abstract class ResourceBundle {

  protected final Locale locale;
  protected final Map<String, String> resources;

  public ResourceBundle(Map<String, String> resources, Locale locale) {
    this.locale = locale;
    this.resources = resources;
  }

  /**
   * Retrieves the locale for this resource bundle
   * @return
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
   * Gets a localized message for the given resource key. This method is not null-safe
   * @param key the resource key
   * @return the message
   * @throws {@link NoSuchElementException} when no string is found for the given key
   * or the key parameter is null
   */
  public String stringFor(String key) {
    return optionalString(key).orElseThrow(supplyException(key));
  }

  private Supplier<RuntimeException> supplyException(String key) {
    return () -> new NoSuchElementException("Key %s not found".formatted(key));
  }

  public static class PropertyResourceBundle extends ResourceBundle {
    public PropertyResourceBundle(Reader reader, Locale locale) throws IOException {
      super(new HashMap<>(), locale);
      var p = new Properties();
      p.load(reader);
      p.forEach((key, value) -> resources.put(key.toString(), value.toString()));
    }
  }
}
