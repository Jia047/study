package com.jia.annotation;

import java.lang.annotation.*;

/**
 * 记录桩操作
 * @author kk
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldName {

    /**
     * 名称描述
     */
    String name() default "";
}
