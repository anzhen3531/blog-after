package com.anzhen.utils.result;

/**
 * @Classname : Result
 * @Date : 2021.4.27 15:28
 * @Created : anzhen
 * @Description :
 * @目的:
 */

import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class Result<T> {

    /**
     * 状态值
     */
    private Boolean status;

    /**
     * 状态码
     */
    private String code;

    /**
     * 状态信息
     */
    private String message;

    /**
     * 返回的结果
     */
    private T data;

    /**
     * 构造函数
     *
     * @param code    状态码
     * @param message 提示信息
     */
    public Result(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 构造函数
     *
     * @param status  状态
     * @param code    状态码
     * @param message 提示信息
     */
    public Result(Boolean status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    /**
     * 构造函数
     *
     * @param status  状态
     * @param code    状态码
     * @param message 提示信息
     * @param data  结果
     */
    public Result(Boolean status, String code, String message, T data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // --------- 静态方法 ----------

    /**
     * 枚举的状态码
     */
    private static final String CODE;

    /**
     * 枚举的提示信息
     */
    private static final String MESSAGE;

    /**
     * 500
     */
    private static final String ERROR_CODE;

    /**
     * 成功返回的状态码
     */
    private static final String SUCCESS_CODE;

    /**
     * 成功返回的状态信息
     */
    private static final String SUCCESS_MSG;

    /**
     * 静态代码块初始化
     */
    static {
        // 成功返回的状态码
        CODE = "getCode";
        // 枚举的提示信息
        MESSAGE = "getMessage";
        // 异常码
        ERROR_CODE = "500";
        // 成功返回的状态码
        SUCCESS_CODE = "200";
        // 成功返回的状态信息
        SUCCESS_MSG = "操作成功";
    }

    /**
     * 操作成功
     *
     * @return 返回结果
     */
    public static Result success() {
        return new Result(true, SUCCESS_CODE, SUCCESS_MSG);
    }

    /**
     * 操作成功
     *
     * @return 返回结果
     */
    public static <T> Result success(T result) {
        return new Result(true, SUCCESS_CODE, SUCCESS_MSG, result);
    }

    /**
     * 返回异常的
     *
     * @param t   泛型枚举
     * @param <T> 泛型
     * @return 返回 data
     */
    public static <T extends Enum> Result error(T t) {
        return error(t, CODE, MESSAGE, null);
    }

    /**
     * 返回异常的
     *
     * @param t       泛型枚举
     * @param message 提示信息
     * @param <T>     泛型
     * @return 返回 data
     */
    public static <T extends Enum> Result error(T t, String message) {
        return error(t, CODE, MESSAGE, message);
    }

    /**
     * 返回异常的
     *
     * @param t          返回的枚举
     * @param codeMethod 状态的方法
     * @param msgMethod  提示信息的状态码
     * @param <T>        泛型
     * @return 返回 data 对象
     */
    public static <T extends Enum> Result error(T t, String codeMethod, String msgMethod, String message) {
        try {
            Class<?> aClass = t.getClass();
            String code = aClass.getMethod(codeMethod).invoke(t).toString();
            message = StringUtils.isEmpty(message) ? aClass.getMethod(msgMethod).invoke(t).toString() : message;
            return new Result(false,code, message);
        } catch (Exception e) {
            return new Result(ERROR_CODE, e.getMessage());
        }
    }


}
