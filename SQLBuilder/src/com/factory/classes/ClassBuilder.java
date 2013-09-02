/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.factory.classes;

import com.exceptions.ClassBuilderException;

/**
 *
 * @author prxgomes
 */
public class ClassBuilder {

    static <T> T getInstance(Class newClass) throws ClassBuilderException {
        try {
            return (T) Class.forName(newClass.getCanonicalName()).newInstance();
        } catch (ClassNotFoundException ex) {
            throw new ClassBuilderException(ex.getMessage());
        } catch (InstantiationException ex) {
            throw new ClassBuilderException(ex.getMessage());
        } catch (IllegalAccessException ex) {
            throw new ClassBuilderException(ex.getMessage());
        }
    }
}
