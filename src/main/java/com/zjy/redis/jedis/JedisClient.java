package com.zjy.redis.jedis;

public interface JedisClient {
    String set(String key, String value);

    String get(String key);

    /*  
     * 是否存在
     * @author zhangjiuyang
     * @date 2018/7/26 19:36
     * @param [key]
     * @return java.lang.Boolean
     */
    Boolean exists(String key);

    /*  
     * 过期时间
     * @author zhangjiuyang
     * @date 2018/7/26 19:36
     * @param [key, seconds]
     * @return java.lang.Long
     */
    Long expire(String key, int seconds);

    Long ttl(String key);

    Long incr(String key);

    Long hset(String key, String field, String value);

    String hget(String key, String field);

    Long hdel(String key, String...fields);
}
