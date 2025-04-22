package com.moldavets.url_shortener_api.service.Impl;

import java.util.Random;

public class ShortUrlGenerator {
    public static Random rand = new Random();
    private static final String allowedString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private char[] allowedCharacters = allowedString.toCharArray();
    private int lengthOfAllowedCharactersArray = allowedCharacters.length-1;

    public String generate() {
        StringBuilder shortUrl = new StringBuilder();
        for(int i = 0 ; i < 6; i++) {
            shortUrl.append(allowedCharacters[rand.nextInt(0,lengthOfAllowedCharactersArray)]);
        }
        return shortUrl.toString();
    }
}
