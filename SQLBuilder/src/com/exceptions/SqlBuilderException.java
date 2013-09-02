/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exceptions;

/**
 *
 * @author prxgomes
 */
public class SqlBuilderException extends Exception {

    public SqlBuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public SqlBuilderException(String message) {
        super(message);
    }
}
