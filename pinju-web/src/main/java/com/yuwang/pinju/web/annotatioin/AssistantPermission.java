package com.yuwang.pinju.web.annotatioin;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Annotated class or method, access the method need to check permission
 * of identity user.</p>
 *
 * @author gaobaowen
 * @since 2011-6-28 16:27:26
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Documented
@Inherited
public @interface AssistantPermission {

    /**
     * <p>Specificate target name of permission.</p>
     *
     * @return
     * @author gaobaowen
     * @since 2011-6-28 16:27:26
     */
    String target();

    /**
     * <p>Specificate action name of permission.</p>
     *
     * @return
     * @author gaobaowen
     * @since 2011-6-28 16:27:26
     */
    String action();
}
