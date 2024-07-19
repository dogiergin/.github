package org.example;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class NotificationService {
    private final Jedis jedis;

    public NotificationService(Jedis jedis) {
        this.jedis = jedis;
    }

    public void subscribeToChannel() {
        Thread subscribeThread = new Thread(() -> {
            JedisPubSub jedisPubSub = new JedisPubSub() {
                @Override
                public void onMessage(String channel, String message) {
                    System.out.println("Message received   ===> " + message);


                }


            };
            jedis.subscribe(jedisPubSub, "EmpChannel");
            

        });
        subscribeThread.start();
    }
}








