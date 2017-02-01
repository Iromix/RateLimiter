package com.codewise.internship;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

class TokenServer{

    private final int tokensAmount;
    private final int refreshTimeInMillisec;
    private TokenGenerator tokenGenerator;
    private BlockingDeque<String> tokens;
    private ScheduledExecutorService executor;

    TokenServer(int tokensAmount, int refreshTimeInMillisec) {
        this.tokensAmount = tokensAmount;
        this.refreshTimeInMillisec = refreshTimeInMillisec;
        tokens = new LinkedBlockingDeque<>();
        tokenGenerator = new TokenGenerator();
    }

    synchronized void startServer() {
        executor = Executors.newScheduledThreadPool(1);
        Runnable refreshTask = () -> refreshTokens();
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
        tokens = tokenGenerator.generateTokens(tokensAmount);
        System.out.println("token resfreshed");
    }

    synchronized String getToken() {
        while (tokens.isEmpty()) {
            System.out.println("pobieranie");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        notifyAll();
        return tokens.pollLast();
    }

    int getTokensAmount() {
        return tokens.size();
    }
}
