package systems.terrnatal.omnijfx.internationalization;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBundles {

  @Test
  public void testLocaleNames() {
    var classloader = Thread.currentThread().getContextClassLoader();
    var url = classloader.getResource("bundle.en_US.properties");
    try (var reader = new Loader.LocalFile().loadResource(url, Charset.defaultCharset())) {
      var properties = new Properties();
      properties.load(reader);
      assertEquals("English (United States)", properties.getProperty("language"));
      assertEquals("Hello World!", properties.getProperty("hello"));
    } catch (Exception e) {
      Assertions.fail(e);
    }
  }

}
