package com.anzhen.globexception;

import com.anzhen.utils.result.Result;
import com.anzhen.utils.result.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Classname : GlobException
 * @Date : 2021.5.11 16:00
 * @Created : anzhen
 * @Description :  全局异常捕获
 * @目的:
 */

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public Result handleException(RuntimeException e) {
        String msg = e.getMessage();
        if (msg == null || msg.equals("")) {
            msg = "服务器出错";
        }
        System.out.println("进入全局异常捕捉");
        System.out.println(e.getMessage());
        return Result.error(ResultCodeEnum.FILE_UPLOAD_ERROR, msg);
    }

}