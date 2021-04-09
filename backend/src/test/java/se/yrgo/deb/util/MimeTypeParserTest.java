package se.yrgo.deb.util;

import static se.yrgo.util.Assertions.assertListEqualsOrdered;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MimeTypeParserTest {
    @ParameterizedTest
    @ValueSource(strings = {
        "text/plain",
        "text/plain;q=0.5",
        "text/plain;q=0.5;format=1"
    })
    void simpleVariants(String input) {
        final List<String> expected = Arrays.asList("text/plain");
        final List<String> result = MimeTypeParser.parse(input);
        assertListEqualsOrdered(expected, result);
    }

    @Test
    void simpleCombinationOfTwo() {
        final List<String> expected = Arrays.asList("text/plain", "application/json");
        final List<String> result = MimeTypeParser.parse("text/plain, application/json");
        assertListEqualsOrdered(expected, result);
    }

    @Test
    void lowQualityOnFirst() {
        final List<String> expected = Arrays.asList("text/plain", "application/json");
        final List<String> result = MimeTypeParser.parse("application/json;q=0.5, text/plain");
        assertListEqualsOrdered(expected, result);
    }
}
