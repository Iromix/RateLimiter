package com.codewise.internship;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

class Authenticator {

    private BlockingDeque<String> permissionTokens;

    Authenticator() {
        permissionTokens = new LinkedBlockingDeque<>();
    }

    synchronized boolean isAuthenticated(String token) {
        return permissionTokens.contains(token);
    }

    synchronized void addToken(String token) {
        permissionTokens.add(token);
    }

}
