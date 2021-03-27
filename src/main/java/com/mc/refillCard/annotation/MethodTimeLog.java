package com.mc.refillCard.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodTimeLog {
    String description() default "";
}
