package com.moldavets.url_shortener_api.service.Impl;

import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.*;

class ShortUrlGeneratorTest {

    private ShortUrlGenerator shortUrlGenerator = new ShortUrlGenerator();

    @RepeatedTest(5)
    void generate_shouldReturnGeneratedStringWithCorrectLengthWithAllowedSymbols() {
        String result = shortUrlGenerator.generate();
        assertEquals(6, result.length());
    }

    @RepeatedTest(5)
    void generate_shouldGenerateDifferentStrings_whenMethodCallsTwoTimes() {
        String first = shortUrlGenerator.generate();
        String second = shortUrlGenerator.generate();

        assertNotEquals(first, second);
    }

    @RepeatedTest(5)
    void generate_shouldGenerateOnlyFromAllowedCharacters() {
        String result = shortUrlGenerator.generate();
        String allowedString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        for (Character character : result.toCharArray()) {
            assertTrue(allowedString.contains(character.toString()));
        }
    }

}