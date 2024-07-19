package org.example;

import redis.clients.jedis.Jedis;

public class RedisConfig {
    Jedis jedis = new Jedis("localhost", 6379);

    public RedisConfig() {
    }

    public Jedis getJedis() {
        return jedis;
    }


}

