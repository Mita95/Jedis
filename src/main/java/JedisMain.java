import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JedisMain {

    //address of your redis server
    private static final String redisHost = "localhost";
    private static final Integer redisPort = 6379;

    //the jedis connection jedisPool..
    private static JedisPool jedisPool = null;

    public JedisMain() {
        jedisPool = new JedisPool(redisHost, redisPort);
    }

    public void addSets() {
        String key = "members";
        String member1 = "Richa";
        String member2 = "Aysha";
        String member3 = "Deepesh";

        //get a jedis connection jedis connection jedisPool
        Jedis jedis = jedisPool.getResource();
        try {
            //save to redis
            jedis.sadd(key, member1, member2, member3);

            Set<String> members = jedis.smembers(key);
            for (String member : members) {
                System.out.println(member);
            }
        } catch (JedisException e) {
            //if something wrong happen, return it back to the jedisPool
        } finally {
            //it's important to return the Jedis instance to the jedisPool once you've finished using it
        }
    }

    public void addHash() {
        String key = "student";
        Map<String, String> map = new HashMap<>();
        map.put("first_name", "John");
        map.put("last_name", "Cray");
        map.put("school", "St Xaviers");

        Jedis jedis = jedisPool.getResource();
        try {
            jedis.hmset(key, map);

            Map<String, String> retrieveMap = jedis.hgetAll(key);
            for (String keyMap : retrieveMap.keySet()) {
                System.out.println(keyMap + " " + retrieveMap.get(keyMap));
            }

        } catch (JedisException e) {
            //if something wrong happen, return it back to the jedisPool
        } finally {
            //it's important to return the Jedis instance to the jedisPool once you've finished using it
        }
    }

    public static void main(String[] args) throws SQLException {
        JedisMain main = new JedisMain();
        main.addSets();
        main.addHash();
    }
}