package com.mc.refillCard.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ControllerRequest {
    String description() default "";
}
