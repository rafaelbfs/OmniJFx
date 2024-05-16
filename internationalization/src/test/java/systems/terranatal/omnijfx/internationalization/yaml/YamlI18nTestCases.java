package systems.terranatal.omnijfx.internationalization.yaml;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

public class YamlI18nTestCases implements ArgumentsProvider {
  public enum Fields {
    GOOD_MORNING, GOOD_AFTERNOON, GOOD_EVENING, HELLO, FORMAL, LANGUAGE;
  }

  private static EnumMap<Fields, String> JAPANESE = new EnumMap(Map.of(
      Fields.GOOD_MORNING, "\u304A\u65E9\u3046",
      Fields.GOOD_AFTERNOON, "\u4ECA\u65E5\u306F",
      Fields.GOOD_EVENING, "\u3053\u3093\u3070\u3093\u306F",
      Fields.HELLO, "\u3053\u3093\u306B\u3061\u306F",
      Fields.FORMAL, "\u3053\u3093\u306B\u3061\u306F",
      Fields.LANGUAGE, "\u65E5\u672C\u8A9E")
  );

  private static EnumMap<Fields, String> SWEDISH = new EnumMap(Map.of(
      Fields.GOOD_MORNING, "God morgon",
      Fields.GOOD_AFTERNOON, "God eftermiddag",
      Fields.GOOD_EVENING, "God kv\u00E4ll",
      Fields.HELLO, "Hall\u00E5!",
      Fields.FORMAL, "H\u00E4lsningar",
      Fields.LANGUAGE, "Svenska (Sverige)")
  );

  @Override
  public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
    return Stream.of(Arguments.of("UTF-16/ja_JP.yaml", StandardCharsets.UTF_16BE, JAPANESE),
        Arguments.of("sv_SE.yaml", StandardCharsets.UTF_8, SWEDISH));
  }
}
