package com.cj.framework.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.cj.framework.security.LoginUser;
import com.cj.project.system.domain.SysUser;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author cc
 * @date 2025-01-02 14:10
 **/
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {

        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser user = loginUser.getUser();
        String name  = user.getUserId()+"-"+user.getUserName();
        this.strictInsertFill(metaObject,"createBy",String.class,name);
        this.strictInsertFill(metaObject,"updateBy",String.class,name);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser user = loginUser.getUser();
        String name  = user.getUserId()+"-"+user.getUserName();
        // 严格的只会填充空值
//        this.strictInsertFill(metaObject,"updateBy",String.class,name);
        this.setFieldValByName("updateBy",name,metaObject);
    }
}
