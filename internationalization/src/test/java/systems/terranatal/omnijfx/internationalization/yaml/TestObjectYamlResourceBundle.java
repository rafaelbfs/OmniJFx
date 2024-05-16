package systems.terranatal.omnijfx.internationalization.yaml;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.yaml.snakeyaml.LoaderOptions;
import systems.terranatal.omnijfx.internationalization.TestBundles;

import java.nio.charset.Charset;
import java.util.EnumMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static systems.terranatal.omnijfx.internationalization.yaml.YamlI18nTestCases.Fields.*;

public class TestObjectYamlResourceBundle {

  @ParameterizedTest
  @ArgumentsSource(YamlI18nTestCases.class)
  public void testResourceToObjectLoading(String file, Charset charset,
                                          EnumMap<YamlI18nTestCases.Fields, String> references) {
    try (var reader = TestBundles.retrieveResource(file, charset)) {
      var deserializer = new ObjectYamlResourceBundle<Sample>(Sample.makeConstructor(new LoaderOptions()));

      var obj = deserializer.loadObject(reader);
      Assertions.assertNotNull(obj.getApplication().getGreetings());
      var greetings = obj.getApplication().getGreetings();

      assertEquals(references.get(GOOD_MORNING), greetings.getGoodMorning());
      assertEquals(references.get(GOOD_AFTERNOON), greetings.getGoodAfternoon());
      assertEquals(references.get(GOOD_EVENING), greetings.getGoodEvening());
      assertEquals(references.get(HELLO), greetings.getHello());
      assertEquals(references.get(FORMAL), greetings.getFormal());
      assertEquals(references.get(LANGUAGE), obj.getApplication().getLanguage());
    } catch (Exception e) {
      Assertions.fail(e);
    }
  }
}
