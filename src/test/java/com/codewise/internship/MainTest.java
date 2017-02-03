package com.codewise.internship;

import org.testng.annotations.*;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.concurrent.*;

//TODO refactor test and move client to other package
public class MainTest {

    private int tokensAmount = 10;
    private int refreshTimeInMillisec = 100;

    @Test
    public void generateTokens() {
        TokenGenerator tokenGenerator = new TokenGenerator();

        BlockingDeque<String> tokens = tokenGenerator.generateTokens(10);

        assertThat(tokens.size()).isEqualTo(10);
    }

    @Test(timeOut = 2000, invocationCount = 5)
    public void getTokenByClientAndAuthenticate() throws InterruptedException {
        Server tokenServer = new Server(tokensAmount,refreshTimeInMillisec);

        Client client = new Client(tokenServer);
        client.start();
        client.join();  //wait for result before assertation

        assertThat(client.getToken()).isNotEmpty();
        assertThat(tokenServer.canIPass(client.getToken())).isTrue();
        tokenServer.stopServer();
    }

    @Test(timeOut = 5000, invocationCount = 5)
    public void shouldRefreshTokensAfterGivenTime() throws InterruptedException {
        Authenticator auth = new Authenticator();
        TokenServer tokenServer = new TokenServer(tokensAmount,refreshTimeInMillisec, auth);
        tokenServer.startServer();

        Thread getTokenThread = new Thread(tokenServer::getToken);
        getTokenThread.start();

        //should have one token less before nex refresh
        Thread.sleep(refreshTimeInMillisec/2);
        assertThat(tokenServer.getTokensAmount()).isEqualTo(tokensAmount-1);
        //TODO repalce sleep with custom Clock sleep!!!
        //        FakeClock clock = new FakeClock(Instant.now(), ZoneId.systemDefault());

        //should refresh tokens and have the sime amount of tokens
        Thread.sleep(refreshTimeInMillisec);
        assertThat(tokenServer.getTokensAmount()).isEqualTo(tokensAmount);
        tokenServer.stopServer();
    }

}