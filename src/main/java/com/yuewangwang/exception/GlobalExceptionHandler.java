package com.yuewangwang.exception;

import com.yuewangwang.utlis.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class) //接收异常的方法 接受所有异常 配合@Validated
    public Result handleException(Exception e) {
        e.printStackTrace();
        //判断异常的内容是否为空，为空就返回“操作失败”
        return Result.error(StringUtils.hasLength(e.getMessage())?e.getMessage():"操作失败");
    }

}
