package com.dream.sso.jedis;

public interface JedisClient {
    /** 新增键值对*/
    String set(String key, String value);

    /** 根据key得到value*/
    String get(String key);

    /** 判断key是否存在*/
    Boolean exists(String key);

    /** 设置key的过期时间 单位：秒*/
    Long expire(String key, int seconds);

    /** 获取key的剩余时间*/
    Long ttl(String key);

    /** 将对应的key的value值加1，如果该key不存在，则加入该key，值设置为1*/
    Long incr(String key);

    /** 一个key对应一个value，这个value是map，一个map中又是一个键值对*/
    Long hset(String key, String field, String value);

    /** 获取hash为key的数据中的field的value*/
    String hget(String key, String field);

    /**
     * 从hash中删除一个或多个field
     * String... field 可以是多个String参数
     * hdel("demo","field1");
     * hdel("demo","field1","field2");
     * hdel("demo"):会首先调用Long hdel(String key);方法，但是如果这个方法不存在
     * 则会去调用Long hdel(String key,String... field);
     * */
    Long hdel(String key, String... field);
    //Long hdel(String key);
}
