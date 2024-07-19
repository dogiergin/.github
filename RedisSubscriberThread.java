package org.example;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class RedisSubscriberThread {

    public static void main(String[] args) {
        // Abonelik işlemi için bir thread oluşturun
        Thread subscriberThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Jedis jedis = new Jedis("localhost"); // Redis sunucusuna bağlanın

                // Abone olacak kanal adı
                String channelName = "myChannel";

                // Abonelik işlemi
                jedis.subscribe(new JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String message) {
                        System.out.println("Received message: " + message + " from channel: " + channel);
                    }
                }, channelName);

                // Bağlantıyı kapat
                jedis.close();
            }
        });

        // Thread'i başlat
        subscriberThread.start();

        // Ana thread başka işler yapabilir
        // Örneğin, aboneye mesaj göndermek
        Jedis jedisPublisher = new Jedis("localhost");
        jedisPublisher.publish("myChannel", "Hello, Redis!");

        // Başka işlemler veya bekleme
        try {
            Thread.sleep(5000); // 5 saniye bekle
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Yayıncı bağlantısını kapat
        jedisPublisher.close();
    }
}
