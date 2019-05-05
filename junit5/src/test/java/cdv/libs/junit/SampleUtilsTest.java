package cdv.libs.junit;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static cdv.libs.junit.SampleUtils.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
class SampleUtilsTest {

    @BeforeAll
    void beforeAll() {
        System.out.println(">>> before all");
    }

    @AfterAll
    void afterAll() {
        System.out.println(">>> after all");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println(">>> before each");
    }

    @AfterEach
    void afterEach() {
        System.out.println(">>> after each");
    }

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

    @Test
    @DisplayName("should sum two key-value pairs")
    void sumTwoKeyValuePairs() {

        KeyValuePair first = new KeyValuePair("firstKey", "firstValue");
        KeyValuePair second = new KeyValuePair("secondKey", "secondValue");

        KeyValuePair sum = sum(first, second);

        assertAll(
                () ->  assertEquals("firstKey:secondKey", sum.getKey()),
                () ->  assertEquals("firstValue_secondValue", sum.getValue()));
    }

    @ParameterizedTest(name = "should convert to upper case string: {0}")
    @ValueSource(strings = { "test", "Test", "TeSt" })
    void convertToUpperCase(String value) {
        assertEquals("TEST", toUpperCase(value));
    }

}