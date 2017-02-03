package com.codewise.internship.server;

public class Server {

    private TokenServer tokenServer;
    private Authenticator auth;

    public Server(int tokensAmount, int refreshTimeInMillisec) {
        auth = new Authenticator();
        tokenServer = new TokenServer(tokensAmount,refreshTimeInMillisec, auth);
        tokenServer.startServer();
    }

    public void stopServer() {
        tokenServer.stopServer();
    }

    public String getToken() {
        return tokenServer.getToken();
    }

    public boolean canIPass(String token) {
        return auth.isAuthenticated(token);
    }

}
