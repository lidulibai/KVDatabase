package com.umeclub;

import java.util.Iterator;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Database {
    private Jedis jedis;
    private JedisPool jedisPool;

    public Database() {
        initPool();
        jedis = jedisPool.getResource();
    }

    private void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(5);
        config.setMaxWaitMillis(1000);
        config.setTestOnBorrow(false);

        jedisPool = new JedisPool(config, "192.168.99.100", 6379);
    }

    /**
     * @Param hash byte[]
     */
    public byte[] get(byte[] hash) {
        Set<String> keys = jedis.keys("*"+ new String(hash));
        Iterator<String> key = keys.iterator();
        if (key.hasNext()) {
            String trueKey = key.next();
            return jedis.get(trueKey) == null ? null : jedis.get(trueKey).getBytes();
        }
        return null;
    }

    // public void put(byte[] hash, byte[] value) {
        // jedis.set("0:" + new String(hash), new String(value));
    // }

    /**
     * @Param id long
     */
    public byte[] get(long id) {
        Set<String> keys = jedis.keys(String.valueOf(id) + "*");
        Iterator<String> key = keys.iterator();
        while (key.hasNext()) {
            String trueKey = key.next();
            String k = trueKey.split(":")[0];
            if (Long.valueOf(k) == id) {
                return jedis.get(trueKey) == null ? null : jedis.get(trueKey).getBytes();
            }
        }
        return null;
    }

    // public void put(long id, byte[] value) {
        // jedis.set(String.valueOf(id), new String(value));
    // }

    public void put(KeyObject key, byte[] value) {
        jedis.set(key.toString(), new String(value));
    }

    public byte[] get(KeyObject key) {
        if (key == null) {
            return null;
        }
        if (key.getHash() == null) {
            return jedis.get(String.valueOf(key.getId())) == null ? null : jedis.get(String.valueOf(key.getId())).getBytes();
        }
        return jedis.get(key.toString()) == null ? null : jedis.get(key.toString()).getBytes();
    }
}
