package com.codewise.internship;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

import java.util.List;

public class MainTest {

    @Test
    public void generateTokens() {
        TokenGenerator tokenGenerator = new TokenGenerator();
        List<String> tokens = tokenGenerator.generateTokens(100);

        assertThat(tokens.size()).isEqualTo(100);
    }
}