package systems.terranatal.tfxtras.jfx.datautils;

import javafx.util.StringConverter;

import java.util.function.Function;

public class Converters {
    private Converters() {

    }

    public static <T> StringConverter<T> makeStrConverter(Function<String, T> fromString,
            Function<T, String> toString) {
        return new StringConverter<T>() {
            @Override
            public String toString(T object) {
                return toString.apply(object);
            }

            @Override
            public T fromString(String string) {
                return fromString.apply(string);
            }
        };
    }
}
