package com.ahdms.user.center.utils;

import java.util.UUID;

/**
 * @author qinxiang
 * @date 2020-07-13 17:37
 */
public class UUIDUtils {

    private UUIDUtils(){

    }

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
