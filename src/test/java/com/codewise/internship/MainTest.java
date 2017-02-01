package com.codewise.internship;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.BlockingDeque;

public class MainTest {

    @Test
    public void generateTokens() {
        TokenGenerator tokenGenerator = new TokenGenerator();

        BlockingDeque<String> tokens = tokenGenerator.generateTokens(10);

        assertThat(tokens.size()).isEqualTo(10);
    }

    @Test
    public void getSingleTokenFromServer() {
        int tokensAmount = 100;
        int refreshTimeInMillisec = 1000;
        TokenServer tokenServer = new TokenServer(tokensAmount,refreshTimeInMillisec);
        tokenServer.startServer();

        String token = tokenServer.getToken();

        tokenServer.stopServer();
        assertThat(token).isNotEmpty();
        assertThat(tokenServer.getTokensAmount()).isEqualTo(tokensAmount-1);
    }

    @Test
    public void getTokenByClient() {
        TokenServer tokenServer = new TokenServer(10,1000);
        tokenServer.startServer();
        Client client = new Client(tokenServer);

        client.getTokenPermissionFromServer();

        tokenServer.stopServer();
        assertThat(client.getToken()).isNotEmpty();
    }

    @Test
    public void shouldRefreshTokensAfterSecond() throws InterruptedException {
        int tokensAmount = 10;
        int refreshTimeInMillisec = 1000;
        TokenServer tokenServer = new TokenServer(10,refreshTimeInMillisec);

        tokenServer.startServer();
        tokenServer.getToken();
        //TODO repalce sleep with custom Clock sleep!!!
        //        FakeClock clock = new FakeClock(Instant.now(), ZoneId.systemDefault());
        Thread.sleep(refreshTimeInMillisec+1);

        tokenServer.stopServer();
        assertThat(tokenServer.getTokensAmount()).isEqualTo(tokensAmount);
    }

}