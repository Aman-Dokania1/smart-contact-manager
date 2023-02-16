package com.smartcontactmanager.customannotation;

import com.smartcontactmanager.customannotation.Impl.PasswordCodeImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordCodeImpl.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordCode {

    public String value() default "Aa";
    public String message() default "Password should start with Aa";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
