package com.yuwang.pinju.web.annotatioin;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Annotated class or method, access the method need to check many permissions
 * of identity user.</p>
 *
 * @author gaobaowen
 * @since 2011-12-23 13:33:17
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Documented
@Inherited
public @interface AssistantPermissions {

	AssistantPermission[] value();
}
