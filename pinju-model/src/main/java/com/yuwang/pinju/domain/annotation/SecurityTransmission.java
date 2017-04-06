package com.yuwang.pinju.domain.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <p>标记该注解的属性属于安全数据传输的数据值</p>
 *
 * @author gaobaowen
 * @since 2011-11-29 15:13:30
 */
@Documented
@Target({ METHOD })
@Retention(RUNTIME)
public @interface SecurityTransmission {
}