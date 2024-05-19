package systems.terranatal.omnijfx.internationalization.yaml;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.yaml.snakeyaml.LoaderOptions;
import systems.terranatal.omnijfx.internationalization.TestBundles;
import systems.terranatal.omnijfx.internationalization.yaml.testdata.Sample;
import systems.terranatal.omnijfx.internationalization.yaml.testdata.SampleWithFormattedMessage;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.EnumMap;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
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
      fail(e);
    }
  }

  @ParameterizedTest
  @ArgumentsSource(FormattedMessageArgumentsSource.class)
  public void testFormattedMessages(String file, Charset charset, String helloWorld,
                                    String noneRemaining, String oneRemaining,
                                    String tenRemaining) {
    try (var reader = TestBundles.retrieveResource(file, charset)) {
      var deserializer = new ObjectYamlResourceBundle<SampleWithFormattedMessage>
          (SampleWithFormattedMessage.makeConstructor(new LoaderOptions()));

      var obj = deserializer.loadObject(reader);

      var pattern = obj.getMessages().getFormatted().getUnitsRemaining();

      assertEquals(helloWorld, obj.getMessages().getRaw().getHelloWorld());
      assertEquals(noneRemaining, MessageFormat.format(pattern, 0));
      assertEquals(oneRemaining, MessageFormat.format(pattern, 1));
      assertEquals(tenRemaining, MessageFormat.format(pattern, 10));

    } catch (IOException e) {
      fail(e);
    }

  }

  public static class FormattedMessageArgumentsSource implements ArgumentsProvider {
    private static final String YOU_HAVE_PT_BR = "Voc\u00EA tem %s.";

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
      return Stream.of(Arguments.of("pt_BR.formatted.yaml", StandardCharsets.UTF_8,
          "Ol\u00E1 Mundo!", YOU_HAVE_PT_BR.formatted("nenhuma unidade mais"),
          YOU_HAVE_PT_BR.formatted("uma unidade restante"),
          YOU_HAVE_PT_BR.formatted("10 unidades restantes")),
          Arguments.of("UTF-16/uk_UA.formatted.yaml", StandardCharsets.UTF_16BE,
              "\u041F\u0440\u0438\u0432\u0456\u0442 \u0421\u0432\u0456\u0442!",
              "\u0443\u0020\u0442\u0435\u0431\u0435 \u043D\u0456\u0447\u043E\u0433\u043E \u043D\u0435\u043C\u0430\u0454.",
              "\u0423\u0020\u0432\u0430\u0441 \u0437\u0430\u043B\u0438\u0448\u0438\u043B\u0430\u0441\u044F \u043E\u0434\u043D\u0430 \u043E\u0434\u0438\u043D\u0438\u0446\u044F.",
              "\u0423 \u0432\u0430\u0441 \u0437\u0430\u043B\u0438\u0448\u0438\u043B\u043E\u0441\u044F 10 \u043E\u0434\u0438\u043D\u0438\u0446\u044C."
              ));
    }
  }
}
