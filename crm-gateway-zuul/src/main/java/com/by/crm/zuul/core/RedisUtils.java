package com.by.crm.zuul.core;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import org.apache.commons.lang.StringUtils;

@Component
public class RedisUtils {
	private final static String REQUEST_TIMES = "REQUEST_TIMES";

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Resource
	private ValueOperations<String, String> valueOperations;

	public void setTokenExpire(String userLoginId, String fromBy, String token) {
		String key = Constants.INSURANCE_USERINFO_TOKEN_XXX_XXX_XXX.replaceAll(
				Constants.INSURANCE_USERINFO_TOKEN_XXX_XXX_XXX_REGEX,
				"$1" + userLoginId + "$2" + fromBy + "$3" + token);
		if (fromBy.equals(ELoginChannel.PC.name())) {
			// 设置超时时间为30分钟
			redisTemplate.expire(key, 30, TimeUnit.MINUTES);
		} else {
			// 设置超时时间为3小时
			// redisTemplate.expire(key, 10, TimeUnit.DAYS);
		}
	}

	public String getUserInfoByToken(String fromBy, String token) {
		String keyname = getTokenKeyName(fromBy, token);
		if (StringUtils.isNotBlank(keyname)) {
			return redisTemplate.opsForValue().get(keyname);
		}
		return "";

	}

	public String getTokenKeyName(String fromBy, String token) {
		Set<String> keys = redisTemplate.keys(Constants.INSURANCE_USERINFO_TOKEN_XXX_XXX_XXX
				.replaceAll(Constants.INSURANCE_USERINFO_TOKEN_XXX_XXX_XXX_REGEX, "$1*$2" + fromBy + "$3" + token));
		if (keys != null && !keys.isEmpty()) {
			String keyname = (String) keys.iterator().next();
			return keyname;
		}
		return null;
	}

	/**
	 * 针对整个系统访问次数限流
	 * 
	 * @return
	 */
	public boolean getRequestTimes() {
		boolean flag = true;
//		redisTemplate.watch(REQUEST_TIMES);
//		redisTemplate.setEnableTransactionSupport(true);
		Object value = redisTemplate.opsForValue().get(REQUEST_TIMES);
//		redisTemplate.multi();
//		List<Object> rs = null;
		if (value != null) {
			Integer requestTimes = Integer.parseInt(value.toString());
			if (requestTimes > 600000) {
				flag = false;
			} else {
				redisTemplate.opsForValue().increment(REQUEST_TIMES, 1l);
			}
		} else {
			redisTemplate.opsForValue().set(REQUEST_TIMES, "1", 1, TimeUnit.MINUTES);
		}

//		rs = redisTemplate.exec();
//		if (rs == null || rs.size() == 0) {
//			getRequestTimes();
//		}
		return flag;
	}

	/**
	 * 针对单个用户访问次数限流
	 * 
	 * @param userLoginId
	 * @return
	 */
	public boolean getRequestTimes(String userLoginId, String ifFactoryUser) {
		String requestKey = REQUEST_TIMES + userLoginId + "@" + ifFactoryUser;
		if (redisTemplate.hasKey(requestKey)) {
			Integer requestTimes = Integer.parseInt(redisTemplate.opsForValue().get(requestKey));
			if (requestTimes > 1000) {
				return false;
			}
			redisTemplate.opsForValue().increment(requestKey, 1l);
		} else {
			redisTemplate.opsForValue().set(requestKey, "1", 1, TimeUnit.MINUTES);
		}
		return true;
	}

	private void flushdb() {
		redisTemplate.execute(new RedisCallback<Object>() {
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				connection.flushDb();
				return "ok";
			}
		});
	}
}
