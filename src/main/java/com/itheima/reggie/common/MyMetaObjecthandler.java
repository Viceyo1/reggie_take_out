package com.itheima.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;


/**
 * ------------------------------公共字段自动填充--------------------------------------
 */

//第一步：自动填充是mybatis-plus提供的一个功能，因此pom中是要导入mybatis-plus的坐标的
/*
        <dependency>
			<groupId>com.baomidou</groupId>
			<artifactId>mybatis-plus-boot-starter</artifactId>
			<version>3.4.2</version>
		</dependency>
 */

//第二步：在实体类中，需要对于那些属于公共字段的属性上添加注解
/*
      @TableField(fill = FieldFill.INSERT_UPDATE) //插入和更新时填充字段
    private LocalDateTime updateTime;
 */

//第三步：创建一个公共字段类，实现 MetaObjectHandler接口，重写两个方法，在类上加上@Component注解，声明成一个bean，这样整体架子搭好了
//重写的两个方法，insertFill是执行insert语句时会执行，updateFill是执行更新的时候执行


/**
 * 自定义元数据对象处理器
 */
@Component
@Slf4j
public class MyMetaObjecthandler implements MetaObjectHandler {
    /**
     * 插入操作，自动填充
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段自动填充[insert]...");
        log.info(metaObject.toString());

        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime",LocalDateTime.now());
        metaObject.setValue("createUser",BaseContext.getCurrentId());
        metaObject.setValue("updateUser",BaseContext.getCurrentId());
    }

    /**
     * 更新操作，自动填充
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动填充[update]...");
        log.info(metaObject.toString());

        long id = Thread.currentThread().getId();
        log.info("线程id为：{}",id);

        metaObject.setValue("updateTime",LocalDateTime.now());
        metaObject.setValue("updateUser",BaseContext.getCurrentId());
    }


    /*
    ------------------------------------------------------------------------------------------
     */

//    /**
//     * 插入操作，自动填充
//     * @param metaObject
//     */
//    @Override
//    public void insertFill(MetaObject metaObject) {
//        log.info("公共字段自动填充[insert]...");
//        log.info(metaObject.toString());
//
//        metaObject.setValue("createTime", LocalDateTime.now());
//        metaObject.setValue("updateTime",LocalDateTime.now());
//        metaObject.setValue("createUser",BaseContext.getCurrentId());
//        metaObject.setValue("updateUser",BaseContext.getCurrentId());
//    }
//
//    /**
//     * 更新操作，自动填充
//     * @param metaObject
//     */
//    @Override
//    public void updateFill(MetaObject metaObject) {
//        log.info("公共字段自动填充[update]...");
//        log.info(metaObject.toString());
//
//        long id = Thread.currentThread().getId();
//        log.info("线程id为：{}",id);
//
//        metaObject.setValue("updateTime",LocalDateTime.now());
//        metaObject.setValue("updateUser",BaseContext.getCurrentId());
//    }



    /*
    ------------------------------------------------------------------------------------------
     */
}


//第四步：编写一个BaseContext工具类，基于ThreadLocal封装的工具类

/*

public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();


public static void setCurrentId(Long id){
    threadLocal.set(id);
}


    public static Long getCurrentId(){
        return threadLocal.get();
    }
}


 */

//第五步：在过滤器的放行方法中调用BaseContext来获取当前登录用户的id


/*

   if(request.getSession().getAttribute("employee") != null){
            log.info("用户已登录，用户id为：{}",request.getSession().getAttribute("employee"));

            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);

            filterChain.doFilter(request,response);
            return;
        }


 */

