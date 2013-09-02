/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.annotations;

import java.lang.annotation.Annotation;
import javax.persistence.Column;
import javax.persistence.JoinColumn;

/**
 * Classe criada para permitir a edicao das informacoes da 
 * interface column, afim de que seja possivel utiliza-la 
 * para transporte de informacao
 * 
 * @author paulo.gomes
 */
public class CustomColumn implements Column {

    private String name;
    private boolean unique = false;
    private boolean nullable = true;
    private boolean insertable = true;
    private boolean updatable = false;
    private String columnDefinition = null;
    private String table = null;
    private int length = 0;
    private int precision = 10;
    private int scale = 2;

    private CustomColumn() {
    }

    public static CustomColumn getInstance(Column column) {
        CustomColumn custom = new CustomColumn();
        custom.name = column.name();
        custom.unique = column.unique();
        custom.nullable = column.nullable();
        custom.insertable = column.insertable();
        custom.updatable = column.updatable();
        custom.columnDefinition = column.columnDefinition();
        custom.table = column.table();
        custom.length = column.length();
        custom.precision = column.precision();
        custom.scale = column.scale();

        return custom;
    }

    public static CustomColumn getInstance(JoinColumn column) {
        CustomColumn custom = new CustomColumn();
        custom.name = column.name();
        custom.unique = column.unique();
        custom.nullable = column.nullable();
        custom.insertable = column.insertable();
        custom.updatable = column.updatable();
        custom.columnDefinition = column.columnDefinition();
        custom.table = column.table();
        custom.length = 255;
        custom.precision = 10;
        custom.scale = 2;

        return custom;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public void setInsertable(boolean insertable) {
        this.insertable = insertable;
    }

    public void setUpdatable(boolean updatable) {
        this.updatable = updatable;
    }

    public void setColumnDefinition(String columnDefinition) {
        this.columnDefinition = columnDefinition;
    }

    public void setTable(String table) {
        this.table = table;
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
        return this.name;
    }

    @Override
    public boolean unique() {
        return this.unique;
    }

    @Override
    public boolean nullable() {
        return this.nullable;
    }

    @Override
    public boolean insertable() {
        return this.insertable;
    }

    @Override
    public boolean updatable() {
        return this.updatable;
    }

    @Override
    public String columnDefinition() {
        return this.columnDefinition;
    }

    @Override
    public String table() {
        return this.table;
    }

    @Override
    public int length() {
        return this.length;
    }

    @Override
    public int precision() {
        return this.precision;
    }

    @Override
    public int scale() {
        return this.scale;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return this.getClass();
    }
}
