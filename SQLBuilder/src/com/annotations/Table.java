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
@Target(value = {ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Table {

    public String name() default "";

    public String catalog() default "";

    public String schema() default "";
}
