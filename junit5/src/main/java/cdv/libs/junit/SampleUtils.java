package cdv.libs.junit;

import lombok.Data;
import lombok.experimental.UtilityClass;

import java.util.Arrays;

@UtilityClass
public class SampleUtils {

    public int sum(int... numbers) {
        return Arrays.stream(numbers).sum();
    }

    public KeyValuePair sum(KeyValuePair first, KeyValuePair second) {
        return new KeyValuePair(
                first.getKey() + ":" + second.getKey(),
                first.getValue() + "_" + second.getValue());
    }

    public String toUpperCase(String value) {
        return value != null ? value.toUpperCase() : null;
    }

    @Data
    public static class KeyValuePair {

        private final String key;

        private final String value;

    }

}
