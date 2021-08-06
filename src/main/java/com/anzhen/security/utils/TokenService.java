package com.anzhen.security.utils;

import cn.hutool.core.util.StrUtil;
import com.anzhen.service.auth.UserDetail;
import com.anzhen.utils.CommonConstant;
import com.anzhen.utils.RedisService;
import com.anzhen.utils.jwt.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Classname : JwtSecurityProperties
 * @Date : 21/08/01 14:33
 * @Created : anzhen
 * @Description :
 * @目的:
 */

@Component
@Slf4j
public class TokenService {

    /**
     * 分转毫秒
     */
    private static final long MINUTE_TO_MILLISECOND = 60 * 1000;

    private static final long REFRESH_MILLISECOND = 20 * 60 * 1000;


    @Autowired
    private RedisService redisService;

    /**
     * token请求头
     */
    @Value("${token.header}")
    private String header;

    /**
     * token秘钥
     */
    @Value("${token.secret}")
    private String secret;

    /**
     * token有效期(默认30分钟)
     */
    @Value("${token.expireTime}")
    private int expireTime;


    /**
     * 获取用户身份信息
     *
     * @param request
     */
    public UserDetail getCommonUser(HttpServletRequest request) {
        // 从请求头获取token
        String token = getToken(request);
        System.out.println(token);
        // 没有任何问题
        System.out.println(StrUtil.isNotEmpty(token));
        if(StrUtil.isNotEmpty(token)){
            Map<String,Object> map = JwtUtils.parseToken(token, secret);
            log.info(map.toString());
            String uuid = map.get(CommonConstant.TOKEN_KEY).toString();
            System.out.println(uuid);
            String loginTokenKey = getKey(uuid);
            System.out.println(loginTokenKey);
            // 放入redis中
            UserDetail commonUser = redisService.get(loginTokenKey);
            System.out.println(commonUser);
            return commonUser;
        }
        return null;
    }

    private String getKey(String uuid) {
        return CommonConstant.TOKEN_REDIS_KEY + uuid;
    }

    /**
     * 获取token
     *
     * @param request
     */
    private String getToken(HttpServletRequest request) {
        // 获取请求头
        System.out.println(this.header);
        String token = request.getHeader(this.header);
        log.info("获取token" + token);
        if(StrUtil.isNotEmpty(token) && token.startsWith(CommonConstant.TOKEN_PREFIX)){
            token = token.replaceAll(CommonConstant.TOKEN_PREFIX, "");
        }
        //
        log.info(token);
        return token;
    }


    /**
     * 校验token
     *
     * @param userDetail
     */
    public void validateToken(UserDetail userDetail){
        log.info("刷新时间 ");
        long time = System.currentTimeMillis() - userDetail.getLoginTime();
        if(time <= REFRESH_MILLISECOND){
            refreshToken(userDetail);
        }
    }

    /**
     * 生成token
     *
     * @param commonUser
     */
    public String createToken(UserDetail commonUser) {
        System.out.println(secret);
        Map<String, Object> tokenMap = new HashMap(2);
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        tokenMap.put(CommonConstant.TOKEN_KEY, uuid);
        String token = JwtUtils.createToken(tokenMap, secret);
        commonUser.setToken(uuid);

        // 将数据放入redis中
        refreshToken(commonUser);
        return token;
    }

    /**
     * 从redis删除token
     *
     */
    public Boolean deleteToken(String token){
        if(!StrUtil.isNotEmpty(token)){
            log.info("正在删除token" + token);
            return redisService.del(CommonConstant.TOKEN_REDIS_KEY+token);
        }
        return false;
    }

    /**
     * 刷新token
     *
     * @param commonUser
     */
    private void refreshToken(UserDetail commonUser) {
        commonUser.setLoginTime(System.currentTimeMillis());
        commonUser.setExpireTime(System.currentTimeMillis() + expireTime * MINUTE_TO_MILLISECOND);
        String key = getKey(commonUser.getToken());
        //用redis缓存token
        redisService.set(key, commonUser, expireTime, TimeUnit.MINUTES);
    }


}
