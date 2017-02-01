package com.codewise.internship;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import java.util.concurrent.BlockingDeque;

public class MainTest {

    @Test
    public void generateTokens() {
        TokenGenerator tokenGenerator = new TokenGenerator();

        BlockingDeque<String> tokens = tokenGenerator.generateTokens(10);

        assertThat(tokens.size()).isEqualTo(10);
    }

    @Test(timeout = 2000)
    public void getSingleTokenByClient() throws InterruptedException {
        TokenServer tokenServer = new TokenServer(10,1000);
        tokenServer.startServer();
        Client client = new Client(tokenServer);

        client.start();

//        get some time to consume token
        Thread.sleep(1000);
        assertThat(client.getToken()).isNotEmpty();
        tokenServer.stopServer();
    }

    @Test(timeout = 2000)
    public void shouldRefreshTokensAfterSecond() throws InterruptedException {
        int tokensAmount = 10;
        int refreshTimeInMillisec = 1000;
        TokenServer tokenServer = new TokenServer(10,refreshTimeInMillisec);

        tokenServer.startServer();
        Thread getTokenThread = new Thread(tokenServer::getToken);
        getTokenThread.start();

        //should have one token less before nex refresh
        Thread.sleep(refreshTimeInMillisec/2);
        assertThat(tokenServer.getTokensAmount()).isEqualTo(tokensAmount-1);
        //TODO repalce sleep with custom Clock sleep!!!
        //        FakeClock clock = new FakeClock(Instant.now(), ZoneId.systemDefault());
        //should refresh tokens and have the sime amount of tokens
        Thread.sleep(refreshTimeInMillisec/2+1);
        assertThat(tokenServer.getTokensAmount()).isEqualTo(tokensAmount);
        tokenServer.stopServer();
    }

}