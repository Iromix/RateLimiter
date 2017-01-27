package com.codewise.internship;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class TokenGenerator {

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    public List<String> generateTokens(int amount) {
        List<String> tokens = new ArrayList<String>();
        for (int i = 0 ; i < amount; i++ )
            tokens.add(generateToken());
        return tokens;
    }
}
