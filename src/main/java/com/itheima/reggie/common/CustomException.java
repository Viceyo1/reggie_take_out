package com.itheima.reggie.common;


/**
 * ------------------------------业务异常--------------------------------------------------
 * 一：定义业务异常
 */

/**
 * 自定义业务异常类
 */
public class CustomException extends RuntimeException {
    public CustomException(String message){
        super(message);
    }
}


/*
---------------------------------------------------------------------------------------------
 */

//public class CustomException extends RuntimeException {
//    public CustomException(String message){
//        super(message);
//    }
//}
