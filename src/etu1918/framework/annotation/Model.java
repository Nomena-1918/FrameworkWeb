package etu1918.framework.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Model {
    String name() default "classe modely";
    boolean isDAO() default false;
}
