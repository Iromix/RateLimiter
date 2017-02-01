package com.codewise.internship;

class Client extends Thread {

    private String token;
    private TokenServer tokenServer;

    Client(TokenServer tokenServer) {
        this.tokenServer = tokenServer;
    }

    @Override
    public void run() {
        getTokenPermissionFromServer();
    }

    void getTokenPermissionFromServer() {
        token = tokenServer.getToken();

        System.out.println("get permission successfully token: " +  token);

        doSomething();
    }

    void doSomething() {}

    String getToken() {
        return token;
    }
}
