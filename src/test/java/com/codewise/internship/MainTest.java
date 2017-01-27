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

    @Test
    public void getSingleTokenFromServer() {
        int tokensAmount = 100;
        int refreshTimeInMilisec = 1000;
        TokenServer tokenServer = new TokenServer(tokensAmount,refreshTimeInMilisec);
        tokenServer.start();

        String token = tokenServer.getToken();

        assertThat(token).isNotEmpty();
        assertThat(tokenServer.getTokensAmount()).isEqualTo(tokensAmount-1);
    }

    @Test
    public void getTokenByClient() {
        TokenServer tokenServer = new TokenServer(10,1000);
        tokenServer.start();
        Client client = new Client(tokenServer);

        client.getTokenPermissionFromServer();

        assertThat(client.getToken()).isNotEmpty();
    }

}