package cdv.libs.junit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static cdv.libs.junit.SampleUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SampleUtilsTest {

    @Test
    @DisplayName("should sum two numbers")
    void sumTwoNumbers() {
        assertEquals(3, sum(1, 2));
    }

    @Test
    @DisplayName("should return single argument value")
    void sumSingleNumbers() {
        assertEquals(5, sum(5));
    }

}