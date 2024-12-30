package com.yuewangwang.validation;

import com.yuewangwang.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * <State,String>  <给那个注解提供校验,娇艳的数据类型>
 */
public class StateValidation implements ConstraintValidator<State,String> {

    /**
     * @param value                      将来要校验的数据
     * @param constraintValidatorContext
     * @return 如果返回false则校验不通过 true校验通过
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        //数据为空校验失败
        if (value == null) {
            return false;
        }

        //校验成功
        if (value.equals("草稿") || value.equals("已保存")) {
            return true;
        }


        return false;
    }
}
