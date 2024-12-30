package com.yuewangwang.anno;


import com.yuewangwang.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented //元注解
@Target({ ElementType.FIELD}) //指定注解可以添加的位置
@Retention(RetentionPolicy.RUNTIME) //元注解
@Constraint(validatedBy = {StateValidation.class}) //指定提供校验规则的类
public @interface State {


    //校验失败后的提示信息
    String message() default "只能是已保存或者草稿";

    //指定分组
    Class<?>[] groups() default {};

    //负载 获取State注解的附加信息
    Class<? extends Payload>[] payload() default {};
}
