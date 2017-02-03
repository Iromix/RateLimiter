package com.codewise.internship.client;

import com.codewise.internship.server.Server;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainSimulation {

   public static void main(String[] args) {
      Server server = new Server(10, 100);

      Executor executor = Executors.newFixedThreadPool(8);

      int numberOfClients = 100;
      for (int i = 0; i < numberOfClients; i++)
          executor.execute(() -> {
              Client client = new Client();
              String consumedToken = server.getToken();
              System.out.println("acquired token");
              client.setToken(consumedToken);
              if (server.canIPass(client.getToken()))
                  client.doSomething();
          });

   }
}
