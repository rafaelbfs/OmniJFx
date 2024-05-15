package systems.terranatal.omnijfx.jfx.datautils;

import javafx.util.StringConverter;

import java.util.function.Function;

/**
 * Utility interface for making custom data converters.
 */
public interface Converters {

    /**
     * Creates a bidirectional converter from String to a type T
     * @param fromString {@link Function} that converts a value from String to T
     * @param toString {@link Function} that converts a value from T to String
     * @return the {@link StringConverter} that will execute the functions
     * @param <T> the type on which this {@link StringConverter} operates
     */
    static <T> StringConverter<T> makeStrConverter(Function<String, T> fromString,
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
