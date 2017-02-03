package com.codewise.internship;

class Client extends Thread {

    private String token;
    private Server tokenServer;

    Client(Server tokenServer) {
        this.tokenServer = tokenServer;
    }

    @Override
    public void run() {
        getTokenPermissionFromServer();
    }

    void getTokenPermissionFromServer() {
        token = tokenServer.getToken();

        System.out.println("client get successfully token: " +  token);

        doSomething();
    }

    private void doSomething() {}

    String getToken() {
        return token;
    }
}
