package com.zhu.demo.aop;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogOption {
    String title() default ""; // 操作模块
    String type() default "";  // 操作类型
    String method() default "";  // 操作方法
    String param() default "";  // 操作参数
    String remark() default "";  // 操作说明
}
