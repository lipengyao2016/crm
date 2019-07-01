package com.crm.utils;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JWTUtils {

    private static String secret = "DA5FrcYZ09LML7YbykUSurIJoOobllB8";
    private static String issur = "SERVICE",subject = " login token",audience = "APP";
    private static String claimKey = "loginData";

    public  static String createToken() {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            //头部信息
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("alg", "HS256");
            map.put("typ", "JWT");

            Date nowDate = new Date();
            Date expireDate = new Date(System.currentTimeMillis() + 1000 * 3600 * 6 );//2小过期



            String token = JWT.create()
                    /*设置头部信息 Header*/
                    .withHeader(map)
                    /*设置 载荷 Payload*/
                    .withIssuer(issur)//签名是有谁生成 例如 服务器
                    .withSubject(subject)//签名的主题
                    //.withNotBefore(new Date())//定义在什么时间之前，该jwt都是不可用的.
                    .withAudience(audience)//签名的观众 也可以理解谁接受签名的
                    .withIssuedAt(nowDate) //生成签名的时间
                    .withExpiresAt(expireDate)//签名过期的时间
                    /*签名 Signature */
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            exception.printStackTrace();
        }
        return null;
    }


    public static String createTokenWithClaim(String userLoginData) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            //头部信息
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("alg", "HS256");
            map.put("typ", "JWT");

            Date nowDate = new Date();
            Date expireDate = new Date(System.currentTimeMillis() + 1000 * 3600 * 6 );//2小过期

            String token = JWT.create()
                    /*设置头部信息 Header*/
                    .withHeader(map)
                    .withClaim(claimKey, userLoginData)
                    /*设置 载荷 Payload*/
                    .withIssuer(issur)//签名是有谁生成 例如 服务器
                    .withSubject(subject)//签名的主题
                    //.withNotBefore(new Date())//定义在什么时间之前，该jwt都是不可用的.
                    .withAudience(audience)//签名的观众 也可以理解谁接受签名的
                    .withIssuedAt(nowDate) //生成签名的时间
                    .withExpiresAt(expireDate)//签名过期的时间
                    /*签名 Signature */
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            exception.printStackTrace();
        }
        return null;
    }


    public static String verifyToken(String token) {

        String loginData = "";

        /*try {*/
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issur)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);

            Map<String, Claim> claims = jwt.getClaims();
            Claim claim = claims.get(claimKey);
            System.out.println(claim.asString());
            loginData = claim.asString();

            List<String> audience = jwt.getAudience();
            System.out.println(subject);
            System.out.println(audience.get(0));
        /*} catch (JWTVerificationException exception) {
            exception.printStackTrace();
        }*/

        return loginData;

    }

    public static void main(String args[]){
    }

}
