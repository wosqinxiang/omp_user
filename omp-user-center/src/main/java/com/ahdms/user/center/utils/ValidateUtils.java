package com.ahdms.user.center.utils;

/**
 * @author qinxiang
 * @date 2020-07-15 8:20
 */
public class ValidateUtils {

    private static final String USERNAME_REGEX = ".{8,16}";

    private static final String PASSWORD_REGEX = "\\w{8,24}";

    private static final String MOBILE_REGEX = "\\d{11}";

    public static boolean validateUsername(String username){
        return username.matches(USERNAME_REGEX);
    }

    public static boolean validatePWD(String password){
        return password.matches(PASSWORD_REGEX);
    }

    public static boolean validateMobile(String mobile){
        return mobile.matches(MOBILE_REGEX);
    }

}
