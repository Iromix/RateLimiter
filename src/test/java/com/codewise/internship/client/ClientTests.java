package com.codewise.internship.client;

import com.codewise.internship.server.Server;
import org.testng.annotations.*;

import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;


public class ClientTests {

    @Test(timeOut = 2000, invocationCount = 5)
    public void getTokenByClientAndAuthenticate() throws InterruptedException {
        Server server = new Server(10, 100);

        Client client = new Client();
        client.setToken(server.getToken());

        assertThat(client.getToken()).isNotEmpty();
        assertThat(server.canIPass(client.getToken())).isTrue();
        server.stopServer();
    }
}