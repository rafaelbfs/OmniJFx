package systems.terranatal.omnijfx.internationalization.yaml;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import systems.terranatal.omnijfx.internationalization.TestBundles;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestYamlResBundle {

  @Test
  public void testLoading() {
    try (var reader = TestBundles.retrieveResource("pt_BR.yaml", StandardCharsets.UTF_8)) {
      var yrb = new YamlResourceBundle(reader, Locale.of("pt", "BR"), StandardCharsets.UTF_8);

      Assertions.assertEquals("Bom dia", yrb.stringFor("application.greetings.goodMorning"));
    } catch (Exception e) {
      Assertions.fail(e);
    }
  }
}
