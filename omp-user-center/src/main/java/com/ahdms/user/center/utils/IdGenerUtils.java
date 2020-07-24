package com.ahdms.user.center.utils;

import com.ahdms.framework.core.commom.util.IdGenerateUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author qinxiang
 * @date 2020-07-23 11:38
 */
@Component
@Data
public class IdGenerUtils {
    @Value("${idgen.name.fws}")
    private String fwsIdName;

    @Value("${idgen.name.gys}")
    private String gysIdName;

    @Value("${idgen.name.dls}")
    private String dlsIdName;

    @Value("${idgen.name.ylf}")
    private String ylfIdName;

    public String generateId(String idName){
        return IdGenerateUtils.generateId(idName,idName,4);
    }

    public String generateId(String idName,String prefix){
        return IdGenerateUtils.generateId(idName,prefix,4);
    }

    public String generateId(String idName,String prefix,int length){
        return IdGenerateUtils.generateId(idName,prefix,length);
    }
}
