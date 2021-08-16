package com.anzhen.utils;

import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Classname : DateUtil
 * @Date : 21/08/03 9:33
 * @Created : anzhen
 * @Description :  时间格式化类
 * @目的:
 */
@UtilityClass
public class DateUtil {

    /**
     * 返回当前日期的字符串
     *
     * @return String
     */
    public String nowString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

}
