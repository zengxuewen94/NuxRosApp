package com.nudtit.lib_common.click;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : LJY
 * @date : 2022/2/24
 * 在onClick方法上使用注解@RepeatClick 允许重复点击
 */

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD})
public @interface FinishClick {
}
