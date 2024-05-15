package systems.terrnatal.omnijfx.internationalization.yaml;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.Yaml;
import systems.terrnatal.omnijfx.internationalization.TestBundles;
import systems.terrnatal.omnijfx.internationalization.yaml.YamlResourceBundle;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestYamlResBundle {

  @Test
  public void testLoading() {
    try (var reader = TestBundles.retrieveResource("pt_BR.yaml", StandardCharsets.UTF_8)) {
      var yrb = new YamlResourceBundle(reader, Locale.of("pt", "BR"), StandardCharsets.UTF_8);

      assertEquals("Bom dia", yrb.stringFor("application.greetings.goodMorning"));
    } catch (Exception e) {
      Assertions.fail(e);
    }
  }
}
