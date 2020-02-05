package cn.itcast.travel.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Jedis工具类
 */
public final class JedisUtil {
    private static JedisPool jedisPool;

    static {
        //读取配置文件
        InputStream is = JedisUtil.class.getClassLoader().getResourceAsStream("jedis.properties");
        Properties prop = new Properties();
        try {
            prop.load(is);
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(Integer.parseInt(prop.getProperty("maxTotal")));
            config.setMaxIdle(Integer.parseInt(prop.getProperty("maxIdle")));
            jedisPool = new JedisPool(config,prop.getProperty("host"), Integer.parseInt(prop.getProperty("port")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //获取jedis
    public static Jedis getJedis(){
        return jedisPool.getResource();
    }
    //关闭jedis
    public static void close(Jedis jedis){
        jedis.close();
    }
}