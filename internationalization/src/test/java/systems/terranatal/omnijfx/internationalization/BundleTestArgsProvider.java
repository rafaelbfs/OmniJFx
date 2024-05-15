package systems.terranatal.omnijfx.internationalization;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.Locale;
import java.util.stream.Stream;

public class BundleTestArgsProvider implements ArgumentsProvider {
  @Override
  public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
    return Stream.of(
        Arguments.of("bundle.pt_PT.properties", Locale.of("pt", "PT"),
            "Portugu\u00EAs Europeu", "Ol\u00E1 Mundo!"),
        Arguments.of("UTF-16/ru_RU.bundle", Locale.of("ru", "RU"),
            "\u0440\u0443\u0441\u0441\u043A\u0438\u0439",
            "\u041F\u0440\u0438\u0432\u0435\u0442, \u043C\u0438\u0440!")

    );
  }
}
