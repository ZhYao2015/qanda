package com.zyao.qanda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zyao.qanda.utils.JedisAdapter;
import com.zyao.qanda.utils.RedisKeyUtils;

@Service
public class LikeService {
	@Autowired
	JedisAdapter jedisAdapter;
	
	public long  getLikeCount(int entityType, int entityId) {
		String likeKey=RedisKeyUtils.getLikeKey(entityType, entityId);
		return jedisAdapter.scard(likeKey);
	}
	
	public int getLikeStatus(int userId,int entityType, int entityId) {
		String likeKey=RedisKeyUtils.getLikeKey(entityType, entityId);
		if(jedisAdapter.sismember(likeKey, String.valueOf(userId))) {
			return 1;
		}
		String dislikeKey=RedisKeyUtils.getDislikeKey(entityType, entityId);
		return jedisAdapter.sismember(dislikeKey, String.valueOf(userId))?-1:0;
	}
	
	
	
	public long like(int userId, int entityType, int  entityId) {
		String likeKey=RedisKeyUtils.getLikeKey(entityType, entityId);
		
		jedisAdapter.sadd(likeKey, String.valueOf(userId));
		
		String dislikeKey=RedisKeyUtils.getDislikeKey(entityType, entityId);
		jedisAdapter.srem(dislikeKey,String.valueOf(userId));
		
		return jedisAdapter.scard(likeKey);
		
	}
	
	public long dislike(int userId, int entityType, int  entityId) {
		String dislikeKey=RedisKeyUtils.getDislikeKey(entityType, entityId);
		
		jedisAdapter.sadd(dislikeKey, String.valueOf(userId));
		
		String likeKey=RedisKeyUtils.getDislikeKey(entityType, entityId);
		jedisAdapter.srem(likeKey,String.valueOf(userId));
		
		return jedisAdapter.scard(dislikeKey);
		
	}
	
}
