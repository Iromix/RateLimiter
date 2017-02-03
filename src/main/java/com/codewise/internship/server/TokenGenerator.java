package com.codewise.internship.server;

import java.util.UUID;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

class TokenGenerator {

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    BlockingDeque<String> generateTokens(int amount) {
        BlockingDeque<String> tokens = new LinkedBlockingDeque<>();
        for (int i = 0 ; i < amount; i++ )
            tokens.add(generateToken());
        return tokens;
    }
}
