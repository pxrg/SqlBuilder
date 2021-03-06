/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author paulo.gomes
 */
@Target(value = {ElementType.METHOD, ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Column {

    public String name() default "";

    public boolean unique() default false;

    public boolean nullable() default true;

    public boolean insertable() default true;

    public boolean updatable() default true;

    public String columnDefinition() default "";

    public String table() default "";

    public int length() default 255;

    public int precision() default 0;

    public int scale() default 0;
}
