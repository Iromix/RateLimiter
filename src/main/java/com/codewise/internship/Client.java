package com.codewise.internship;

class Client {

    private String token;

    private TokenServer tokenServer;
    Client(TokenServer tokenServer) {
        this.tokenServer = tokenServer;
    }

    void getTokenPermissionFromServer() {
        token = tokenServer.getToken();

        System.out.println("get permission successfully token: " +  token);

        doSomething();
    }

    void doSomething() {}

    public String getToken() {
        return token;
    }
}
