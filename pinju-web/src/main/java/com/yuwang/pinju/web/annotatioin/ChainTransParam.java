package com.yuwang.pinju.web.annotatioin;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @Project: pinju-web
 * @Description: chain参数annotation
 * @author 石兴 shixing@zba.com
 * @date 2011-8-12 下午07:03:48
 * @update 2011-8-12 下午07:03:48
 * @version V1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ChainTransParam {
    String fieldName() default "";
}