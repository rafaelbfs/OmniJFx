package systems.terranatal.omnijfx.internationalization;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestBundles {
  public static Reader retrieveResource(String fileLocation, Charset charset) {
    var loader = new Loader.Classpath(TestBundles.class.getClassLoader());

    return loader.loadResource(fileLocation, charset);
  }

  @Test
  public void testLocaleNames() {
    var classloader = Thread.currentThread().getContextClassLoader();
    var url = classloader.getResource("bundle.en_US.properties");
    try (var reader = new Loader.LocalFile().loadResource(url, Charset.defaultCharset())) {
      var properties = new Properties();
      properties.load(reader);
      assertEquals("English (United States)", properties.getProperty("app_1.language"));
      assertEquals("Hello World!", properties.getProperty("app_1.hello"));
    } catch (Exception e) {
      Assertions.fail(e);
    }
  }

  @ParameterizedTest
  @ArgumentsSource(BundleTestArgsProvider.class)
  public void testInternationalization(String filePath, Locale locale, String expectedLanguageValue,
                                           String expectedHelloValue) {
    var charset = filePath.startsWith("UTF-16/") ? StandardCharsets.UTF_16BE : StandardCharsets.UTF_8;

    try (var reader = retrieveResource(filePath, charset)) {
      var bundle = new ResourceBundle.PropertyResourceBundle(reader, locale, charset);
      var optStr = bundle.optionalString("app_1.language");
      assertTrue(optStr.isPresent());
      assertEquals(expectedLanguageValue, optStr.get());
      assertEquals(expectedHelloValue, bundle.stringFor("app_1.hello"));
    } catch (Exception e) {
        Assertions.fail(e);
    }
  }

}
