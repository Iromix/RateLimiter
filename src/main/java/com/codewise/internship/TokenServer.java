package com.codewise.internship;

import java.util.concurrent.*;

class TokenServer {

    private final int tokensAmount;
    private final int refreshTimeInMillisec;
    private Authenticator auth;
    private TokenGenerator tokenGenerator;
    private BlockingDeque<String> availableTokens;

    private ScheduledExecutorService executor;

    TokenServer(int tokensAmount, int refreshTimeInMillisec, Authenticator auth) {
        this.tokensAmount = tokensAmount;
        this.refreshTimeInMillisec = refreshTimeInMillisec;
        this.auth = auth;
        availableTokens = new LinkedBlockingDeque<>();
        tokenGenerator = new TokenGenerator();
    }

    synchronized void startServer() {
        executor = Executors.newScheduledThreadPool(1);
        Runnable refreshTask = this::refreshTokens;
        executor.scheduleAtFixedRate(refreshTask, 0, refreshTimeInMillisec, TimeUnit.MILLISECONDS);
    }

    synchronized void stopServer() {
        System.out.println("trying to stop server...");
        try {
            executor.shutdown();
            executor.awaitTermination(refreshTimeInMillisec, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            System.err.println("refresh token task interrupted");
        } finally {
            if (!executor.isTerminated())
                System.err.println("cancel non-finished tasks");
            executor.shutdownNow();
            System.out.println("shutdown finished");
        }
    }

    private synchronized void refreshTokens() {
        availableTokens = tokenGenerator.generateTokens(tokensAmount);
        System.out.println("token resfreshed");
        notifyAll();
    }

    synchronized String getToken() {
        while (availableTokens.isEmpty()) {
            System.out.println("nie ma tokenow");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String consumedToken = availableTokens.pollLast();
        if (consumedToken != null)
            auth.addToken(consumedToken);
        notifyAll();
        return consumedToken;
    }

    int getTokensAmount() {
        return availableTokens.size();
    }


}
