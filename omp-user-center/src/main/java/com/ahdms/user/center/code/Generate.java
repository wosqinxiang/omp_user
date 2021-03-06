package com.ahdms.user.center.code;

import com.ahdms.framework.core.commom.util.DigestUtils;
import com.ahdms.framework.core.commom.util.IdGenerateUtils;
import com.ahdms.framework.core.commom.util.SpringUtils;
import com.ahdms.framework.core.web.response.ResultAssert;
import com.ahdms.framework.mybatis.generate.AutoGenerator;
import com.ahdms.framework.mybatis.generate.ConfigGenerator;

import java.util.Properties;

/**
 * @author zhoumin
 * @version 1.0.0
 * @date 2020/7/6 15:29
 */
public class Generate {

    public static void main(String[] args) {
//        ConfigGenerator config = buildConfig();
//        AutoGenerator generator = new AutoGenerator(config);
//        generator.run();
        System.out.println(DigestUtils.md5Hex("AHdms520"));
//        ResultAssert.assertTrue(false,"aaaaa");
//        ResultAssert.throwFail(ApiCode.USER_LOGIN_PWD_ERROR);
        String id = IdGenerateUtils.generateId("USER");
        System.out.println(id);
    }

    private static ConfigGenerator buildConfig() {
        ConfigGenerator config = new ConfigGenerator();
        config.setAuthor("qinxiang");
        config.setBasePackage("com.ahdms.user.center");
        config.setPrefixTableName("");
        Properties properties = SpringUtils.loadProperties("classpath:generator.properties");
        config.setDbUrl(properties.getProperty("spring.datasource.url"));
        config.setDbUser(properties.getProperty("spring.datasource.username"));
        config.setDbPassword(properties.getProperty("spring.datasource.password"));
        config.setGenerateTableName("pay_info");


        return config;
    }
}
