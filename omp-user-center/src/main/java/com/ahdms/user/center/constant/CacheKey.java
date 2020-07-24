package com.ahdms.user.center.constant;

import com.ahdms.framework.cache.cache.core.ICacheKey;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;

/**
 * @author zhoumin
 * @version 1.0.0
 * @date 2020/7/7 16:22
 */
@Getter
@AllArgsConstructor
public enum CacheKey implements ICacheKey {
    // ORDER缓存key，失效时间30分钟
    USER("USER", Duration.ofMillis(30)),
    USER_ENABLE("USER_ENABLE",Duration.ofHours(24)),
    USER_LOGIN_ERROR("USER_LOGIN_ERROR_COUNT",Duration.ofHours(24)),
    PWDTOKEN("USER_PWD_TOKEN",Duration.ofMinutes(10)),
    SMSCODE("SMSCODE", Duration.ofMinutes(10));

    private String prefix;

    private Duration expire;
}
