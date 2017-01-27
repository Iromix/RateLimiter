package com.codewise.internship;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class TokenServer {

    private final int tokensAmount;
    private final int refreshTimeInMilisec;
    private TokenGenerator tokenGenerator;
    private List<String> tokens = new ArrayList<String>();

    TokenServer(int tokensAmount, int refreshTimeInMilisec)   {
        this.tokensAmount = tokensAmount;
        this.refreshTimeInMilisec = refreshTimeInMilisec;
        tokenGenerator = new TokenGenerator();
    }

    void start() {
        refreshTokens();
    }

    private void refreshTokens() {
        tokens = tokenGenerator.generateTokens(tokensAmount);
    }

    String getToken() {
        if (tokens.isEmpty())
            return null;
        else
            return tokens.remove(tokens.size()-1);
    }

    int getTokensAmount(){
        return tokens.size();
    }
}
