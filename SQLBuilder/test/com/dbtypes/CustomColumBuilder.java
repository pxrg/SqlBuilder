/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dbtypes;

import com.annotations.Column;
import java.lang.annotation.Annotation;

/**
 *
 * @author paulo.gomes
 */
public class CustomColumBuilder implements Column {

    private String columnDefinition = null;
    private boolean nullable = true;
    private int length = 255;
    private int precision = 10;
    private int scale = 2;

    public void setColumnDefinition(String columnDefinition) {
        this.columnDefinition = columnDefinition;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }   
    
    
    @Override
    public String name() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean unique() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean nullable() {
        return nullable;
    }

    @Override
    public boolean insertable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updatable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String columnDefinition() {
        return columnDefinition;
    }

    @Override
    public String table() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public int precision() {
        return precision;
    }

    @Override
    public int scale() {
        return scale;
    }
    
    @Override
    public Class<? extends Annotation> annotationType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
