package com.crm.assistant.util;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Resource
	private ValueOperations<String, String> valueOperations;

	public static final String SALE_MEANS_PPT_ATTACHMENT = "SALE_MEANS_PPT_ATTACHMENT";

	public void setInterval(Integer interval, String key) {
		redisTemplate.opsForValue().set(key, key, interval, TimeUnit.MINUTES);
	}

	public String getInterval(String key) {
		String result = redisTemplate.opsForValue().get(key);
		return result;
	}
}
