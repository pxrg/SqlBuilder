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
public @interface ManyToOne {

    public Class targetEntity() default void.class;

    public boolean optional() default true;
}
