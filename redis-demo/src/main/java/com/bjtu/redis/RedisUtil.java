package com.bjtu.redis;

import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import java.util.*;
public class RedisUtil {

    public RedisUtil(){
    }

    //基本操作封装
    private RedisTemplate redisTemplate;
    private static JedisPool jedisPool = JedisInstance.getInstance();


    public static void setIncrNum(String key , int field){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.incrBy(key,Long.parseLong(String.valueOf(field)));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
    }


    public static String getValueNum(String key){
        String ret=null;
        Jedis jedis=null;
        try{
            jedis = jedisPool.getResource();
            ret = jedis.get(key);
        }catch(Exception e){

        }finally {
            jedisPool.returnResource(jedis);
        }
        return ret;
    }


    public static void setHashPush(String key,String field,String value){
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            jedis.hset(key, field, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public static Map<String,String> getHashMap(String key){
        Jedis jedis = null;
        Map<String,String> ret = null;
        try {
            jedis = jedisPool.getResource();
            ret = jedis.hgetAll(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return ret;
    }

    public static void writeList(String key,String value){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.rpush(key,value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public static List<String> getArray(String key){
        Jedis jedis = null;
        List<String> ret = null;
        try{
            jedis = jedisPool.getResource();
            ret = jedis.lrange(key, 0, -1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return ret;
    }

    public static void setSession (String key,String value){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.set(key,value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public static void addSet(String key,String value){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.sadd(key,value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public static Set<String> getSet(String key){
        Set<String> ret = null;
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            ret = jedis.smembers(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return ret;
    }

}
