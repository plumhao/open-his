package com.jinken.openhis.commons.annotation;

import com.jinken.openhis.commons.enums.BusinessTypeEnum;
import com.jinken.openhis.commons.enums.OperatorTypeEnum;

import java.lang.annotation.*;

/**
 * @Target({ElementType.PARAMETER, ElementType.METHOD})
 * 注解可以贴在哪些位置 方法上，类，参数等等...
 *
 * @Retention(RetentionPolicy.RUNTIME)
 * 注解保留到哪个阶段，自定义注解一般都保留运行时
 * java代码三个阶段  源代码 SOURCE，编译字节码：CLASS ，运行时 RUNTIME
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 模块
     */
    String title() default "";


    /**
     * 功能
     */
    BusinessTypeEnum businessType() default BusinessTypeEnum.OTHER;


    /**
     * 操作人类别
     */
    OperatorTypeEnum operatorType() default OperatorTypeEnum.MANAGE;


    /**
     * 是否保存请求的参数
     */
    boolean isSaveRequestData() default true;
}
