package com.yuwang.pinju.validation.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

@Documented
@Constraint(validatedBy = { })
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@NotEmpty(message = "{memberRegister.loginName.notempty}")
@ByteLength(min = 4, max = 50, message = "{memberRegister.loginName.length}")
@Pattern.List({
	@Pattern(regexp = "[\u4e00-\u9faf_0-9a-zA-Z-]+", message = "{memberRegister.loginName.pattern}"),
	@Pattern(regexp = "[\u4e00-\u9fafa-zA-Z].*", message = "{memberRegister.loginName.pattern2}")
})
public @interface MemberName {
	String message() default "{com.mycompany.constraints.validlicenseplate}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
