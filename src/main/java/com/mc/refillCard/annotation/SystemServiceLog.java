package com.mc.refillCard.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.PARAMETER})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface SystemServiceLog {
    String description() default "";
}
