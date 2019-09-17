package com.zyao.qanda.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class JedisAdapter implements InitializingBean{
	
	private static final Logger logger=LoggerFactory.getLogger(JedisAdapter.class);
	
	private JedisPool pool;
	
	
	public static void print(int index,Object obj) {
		System.out.println(String.format("%d, %s", index,obj.toString()));
	}
	public static void main(String[] argv) {
		Jedis jedis=new Jedis("redis://localhost:6379/8");
		jedis.flushDB();
		
		
		jedis.set("hello","world");
		print(1,jedis.get("hello"));
		jedis.rename("hello","hallo");
		print(1,jedis.get("hallo"));
		jedis.setex("hello2", 15, "world");
		
		
		jedis.set("pv", "100");
		jedis.incr("pv");
		jedis.incrBy("pv", 5);
		jedis.decrBy("pv", 2);
		print(2,jedis.get("pv"));
		print(3,jedis.keys("*"));
		
		
		String list="list";
		jedis.del("list");
		for(int i=0;i<10;++i) {
			jedis.lpush(list, "a"+String.valueOf(i));
		}
		
		print(4,jedis.lrange(list, 0, 11));
		print(4,jedis.lrange(list, 0, 3));
		print(4,jedis.llen("list"));
		//print(4,jedis.lpop("list"));
		print(4,jedis.llen("list"));
		print(4,jedis.lindex("list", 3));
		print(4,jedis.linsert("list", BinaryClient.LIST_POSITION.AFTER,"a5", "ok"));
		
		String userKey="userxx";
		jedis.hset(userKey,"name","Jane");
		jedis.hset(userKey, "age", "24");
		print(5,jedis.hget(userKey, "name"));
		jedis.hdel(userKey, "age");
		//print(5,jedis.hget(userKey, "age"));
		print(5,jedis.hexists(userKey, "email"));
		print(5,jedis.hkeys(userKey));
		print(5,jedis.hvals(userKey));
		jedis.hsetnx(userKey, "uni", "tud");
		jedis.hsetnx(userKey,"name","Pierre");
		print(5,jedis.hget(userKey, "name"));
		print(5, jedis.hgetAll(userKey));
		
		
		String likeKey1="commentLike1";
		String likeKey2="commentLike2";
		for(int i=0;i<10;i++) {
			jedis.sadd(likeKey1, String.valueOf(i));
			jedis.sadd(likeKey2, String.valueOf(i*i));
		}
		
		print(6,jedis.smembers(likeKey1));
		print(6,jedis.smembers(likeKey2));
		print(6,jedis.sunion(likeKey1,likeKey2));
		print(6,jedis.sinter(likeKey1,likeKey2));
		print(6,jedis.sdiff(likeKey1,likeKey2));
		print(6,jedis.sismember(likeKey1, "12"));
		print(6,jedis.sismember(likeKey2, "16"));
		jedis.srem(likeKey1, "5");
		print(6,jedis.smembers(likeKey1));
		jedis.smove(likeKey2, likeKey1, "25");
		print(6,jedis.smembers(likeKey1));
		print(6,jedis.scard(likeKey1));
		
		//Sorted List
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		pool=new JedisPool("redis://localhost:6379/8");
	}
	
	public long sadd(String key,String value) {
		Jedis jedis=null;
		try {
			jedis=pool.getResource();
			return jedis.sadd(key, value);
		}catch(Exception e) {
			logger.error("异常发生"+e.getMessage());
		}finally {
			if(jedis!=null) {
				jedis.close();
			}
		}
		
		return 0;
	}
	
	public long scard(String key) {
		Jedis jedis=null;
		try {
			jedis=pool.getResource();
			return jedis.scard(key);
		}catch(Exception e) {
			logger.error("异常发生"+e.getMessage());
		}finally {
			if(jedis!=null) {
				jedis.close();
			}
		}
		
		return 0;
	}
	
	public long srem(String key, String value) {
		Jedis jedis=null;
		try {
			jedis=pool.getResource();
			return jedis.srem(key, value);
		}catch(Exception e) {
			logger.error("异常发生"+e.getMessage());
		}finally {
			if(jedis!=null) {
				jedis.close();
			}
		}
		
		return 0;
	}
	
	public boolean sismember(String key,String value) {
		Jedis jedis=null;
		try {
			jedis=pool.getResource();
			return jedis.sismember(key, value);
		}catch(Exception e) {
			logger.error("异常发生"+e.getMessage());
		}finally {
			if(jedis!=null) {
				jedis.close();
			}
		}
		
		return false;
	}
}
