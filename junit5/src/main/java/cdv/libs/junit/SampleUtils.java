package cdv.libs.junit;

import lombok.experimental.UtilityClass;

import java.util.Arrays;

@UtilityClass
public class SampleUtils {

    public int sum(int... numbers) {
        return Arrays.stream(numbers).sum();
    }

}
