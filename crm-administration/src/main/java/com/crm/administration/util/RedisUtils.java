package com.crm.administration.util;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.crm.common.ELoginChannel;
import com.crm.vo.UserLoginInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;

import org.apache.commons.lang.StringUtils;

@Component
public class RedisUtils {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void setToken(String fromBy, String token, UserLoginInfoVo vo) {
        String key = Constants.INSURANCE_USERINFO_TOKEN_XXX_XXX_XXX.replaceAll(
                Constants.INSURANCE_USERINFO_TOKEN_XXX_XXX_XXX_REGEX,
                "$1" + vo.getUserLoginId() + "$2" + fromBy + "$3" + token);
        redisTemplate.opsForValue().set(key, JSONObject.toJSONString(vo));

        if (fromBy.equals(ELoginChannel.PC.name())) {
            // 设置超时时间为30分钟
            redisTemplate.expire(key, 30, TimeUnit.MINUTES);
        } else {
            // 设置超时时间为3小时
            // redisTemplate.expire(key, 10, TimeUnit.DAYS);
        }
    }

    public UserLoginInfoVo getUserInfoByToken(String fromBy, String token) {
        String keyname = getTokenKeyName(fromBy, token);
        if (StringUtils.isNotBlank(keyname)) {
            String userStr = redisTemplate.opsForValue().get(keyname);
            return JSONObject.parseObject(userStr, UserLoginInfoVo.class);
        }
        return new UserLoginInfoVo();

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

    public void destoryUserInfoByUserId(String fromBy, String userId) {
        Set<String> keys = redisTemplate.keys(Constants.INSURANCE_USERINFO_TOKEN_XXX_XXX
                .replaceAll(Constants.INSURANCE_USERINFO_TOKEN_XXX_XXX_REGEX, "$1" + userId + "$2" + fromBy) + "*");
        redisTemplate.delete(keys);

    }

    public void destoryUserInfoBySession(String sessionId) {
        Set<String> keys = redisTemplate.keys("*" + sessionId);
        redisTemplate.delete(keys);
    }

    public void setUserVerifyCode(String userLoginId, String verifyCode, int minutes) {
        String key = "USER_LOGIN_VERIFY_" + userLoginId;
        redisTemplate.opsForValue().set(key, verifyCode, minutes, TimeUnit.MINUTES);
    }

    public String getUserVerifyCode(String userLoginId) {
        String key = "USER_LOGIN_VERIFY_" + userLoginId;
        if (StringUtils.isNotBlank(key)) {
            String verifyCode = redisTemplate.opsForValue().get(key);
            return verifyCode;
        }
        return null;

    }

    public void setUserLoginIp(String userLoginId, String ip) {
        String key = userLoginId + "." + ip;
        redisTemplate.opsForValue().set(key, "PC", 30, TimeUnit.MINUTES);
    }
}
