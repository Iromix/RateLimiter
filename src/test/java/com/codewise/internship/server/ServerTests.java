package com.codewise.internship.server;

import org.testng.annotations.Test;

import java.util.concurrent.BlockingDeque;

import static org.assertj.core.api.Assertions.assertThat;

public class ServerTests {

    @Test
    public void generateTokens() {
        TokenGenerator tokenGenerator = new TokenGenerator();

        BlockingDeque<String> tokens = tokenGenerator.generateTokens(10);

        assertThat(tokens.size()).isEqualTo(10);
    }

    @Test(timeOut = 5000, invocationCount = 5)
    public void shouldRefreshTokensAfterGivenTime() throws InterruptedException {
        //GIVEN
        int tokensAmount = 10;
        int refreshTimeInMillisec = 100;
        Authenticator auth = new Authenticator();
        TokenServer tokenServer = new TokenServer(tokensAmount, refreshTimeInMillisec, auth);

        //WHEN
        tokenServer.startServer();
        Thread getTokenThread = new Thread(tokenServer::getToken);
        getTokenThread.start();

        //should have one token less before nex refresh
        Thread.sleep(refreshTimeInMillisec / 2);
        assertThat(tokenServer.getTokensAmount()).isEqualTo(tokensAmount - 1);

        //should refresh tokens and have the sime amount of tokens
        Thread.sleep(refreshTimeInMillisec);
        assertThat(tokenServer.getTokensAmount()).isEqualTo(tokensAmount);
        tokenServer.stopServer();
    }
}
