package com.dream.content.test;

import org.junit.Test;
import redis.clients.jedis.Jedis;

public class TestRedis {

/*    @Test
    public void testRedisClient() throws ParseException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
        JedisClient bean = ctx.getBean(JedisClient.class);
        String json = bean.hget("CONTENT", "89");
        TbContent[] parse = JSON.parse(json, TbContent[].class);
        System.out.println(Arrays.asList(parse));
        //具体使用那个实现类--里面会自动获取，但是两个实现类不能同时存在
//        String set = bean.set("jedisClient", "haha");
//
//        String jedisClient = bean.get("jedisClient");
//        System.out.println(jedisClient);
//        ctx.close();


    }*/

    /**
     * 单机版
     */
    @Test
    public void testRedis(){
        //1、创建一个Jedis对象（相当于jdbc中的connection对象）需要哦指定ip和端口
        Jedis jedis = new Jedis("192.168.124.2", 6379);
        //2、使用Jedis对象操作数据库，每一个redis命令对应一个方法
        jedis.set("demo","java_jedis");
        String demo = jedis.get("demo");
        System.out.println(demo);
        //3、关闭
        jedis.close();
    }

   /* @Test
    public void testRedisCluster(){
        //1、使用JedisCluster对象，需要制定一个set集合作为redis集群的节点对象
        HashSet<HostAndPort> nodes = new HashSet<>();
        //每一个HostAndPort都是一个ip和端口描述的redis服务端的节点
        nodes.add(new HostAndPort("192.168.124.2",7001));
        nodes.add(new HostAndPort("192.168.124.2",7002));
        nodes.add(new HostAndPort("192.168.124.2",7003));
        nodes.add(new HostAndPort("192.168.124.2",7004));
        nodes.add(new HostAndPort("192.168.124.2",7005));
        nodes.add(new HostAndPort("192.168.124.2",7006));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        //2、使用Jedis对象操作数据库，每一个redis命令对应一个方法
        jedisCluster.set("cluster_demo","java_jedis_cluster");
        String demo = jedisCluster.get("cluster_demo");
        System.out.println(demo);
        //3、关闭
        jedisCluster.close();
    }*/
}
