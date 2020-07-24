package com.ahdms.user.center.dao;

import com.ahdms.framework.mybatis.mapper.IMapper;
import com.ahdms.user.center.bean.bo.AdminUserInfoBo;
import com.ahdms.user.center.bean.bo.AdminUserPageQueryBo;
import com.ahdms.user.center.bean.entity.AdminUserInfo;
import com.ahdms.user.center.bean.vo.AdminUserInfoPageReqVo;
import com.ahdms.user.center.bean.vo.AdminUserPageRspVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <B>说明：</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0.
 * @date 2020-07-13 13:34
 */
@Mapper
public interface IAdminUserInfoDao extends IMapper<AdminUserInfo> {

    IPage<AdminUserInfoBo> queryAdminSimple(@Param("page") IPage<AdminUserInfo> page, @Param("pageQueryVo") AdminUserPageQueryBo adminInfoBo , @Param("companyId") Long companyId);
}
