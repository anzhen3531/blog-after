package com.anzhen.utils.result;

import lombok.Getter;

/**
 * @Classname : ResultCodeEnum
 * @Date : 2021.4.27 15:37
 * @Created : anzhenp
 * @Description :
 * @目的:
 */
@Getter
public enum ResultCodeEnum {
    SUCCESS(true, 200,"成功"),
    NO_RIGHT_TO_ACCESS(false, 402, "无权访问"),
    NOT_LOGIN(false, 500, "没有登录账号"),
    USER_ACCOUNT_EXPIRED(false, 500, "账号错误"),
    USERNAME_NOTFIND(false, 501, "没有该账号"),
    UNKNOWN_REASON(false, 400, "未知错误"),
    SERVER_ERROR(false, 500, "服务器异常"),
    BAD_SQL_GRAMMAR(false, 500, "sql语法错误"),
    PAGE_NOT_FUND(false, 404, "页面丢失"),
    NAME_PASSWORD_ERROR(false, 406, "密码错误"),
    JSON_PARSE_ERROR(false, 400, "json解析异常"),
    CROSS_ORIGIN(false, 403, "跨域异常"),
    FILE_UPLOAD_ERROR(false, 500, "文件上传错误"),
    EXCEL_DATA_IMPORT_ERROR(false, 500, "Excel数据导入错误"),
    USER_NEED_AUTHORITIES(false,201,"用户未登录"),
    USER_LOGIN_FAILED(false,202,"用户账号或密码错误"),
    USER_LOGIN_SUCCESS(false,203,"用户登录成功"),
    USER_NO_ACCESS(false, 204,"用户无权访问"),
    USER_LOGOUT_SUCCESS(false, 205,"用户登出成功"),
    TOKEN_IS_BLACKLIST(false, 206,"此token为黑名单"),
    LOGIN_IS_OVERDUE(false, 207,"登录已失效");


    private Boolean success; // 是否响应成功
    private Integer code;    // 响应的状态码
    private String message;  // 响应的消息

    ResultCodeEnum(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
