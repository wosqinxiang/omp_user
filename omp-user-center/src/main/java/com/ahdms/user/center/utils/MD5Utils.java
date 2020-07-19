package com.ahdms.user.center.utils;

import com.ahdms.framework.core.commom.util.DigestUtils;

/**
 * @author qinxiang
 * @date 2020/7/18 14:33
 */
public class MD5Utils {

    public static String md5WithSalt(String source,String salt){
        return DigestUtils.md5Hex(source+salt);
    }
}
