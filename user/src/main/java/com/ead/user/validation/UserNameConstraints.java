package com.ead.user.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD})//onde vc pode usar essa anotacao(metodo ou campo)
@Retention(RetentionPolicy.RUNTIME)//definindo quando vai acontecer ( nesse caso em tempo de execucao)
@Constraint(validatedBy = UserNameConstraintsImpl.class)
public @interface UserNameConstraints {
	
	String message() default "Invalid userName";
	Class<?>[] group() default{};
	Class<? extends Payload>[] payload() default{};

}
