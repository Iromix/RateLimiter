package com.codewise.internship.client;

class Client {

    private String token;

    public void doSomething() {
        System.out.println("doing something");
    }

    String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
