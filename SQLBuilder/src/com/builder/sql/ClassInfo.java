/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builder.sql;

import com.annotations.Column;
import java.util.Map;

/**
 *
 * @author prg
 */
class ClassInfo implements Info{

    private Class classe;
    private String table;
    private String alias;
    private Boolean isAutoIncrement;
    private String attributeId;
    private Map<String, Column> attributes;

    public ClassInfo(Class classe) {
        this.classe = classe;
    }

    @Override
    public Class getClasse() {
        return classe;
    }

    @Override
    public String getTable() {
        return table;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public Boolean isAutoIncrement() {
        return isAutoIncrement;
    }

    @Override
    public String getAttributeId() {
        return attributeId;
    }

    @Override
    public Map<String, Column> getAttributes() {
        return attributes;
    }

    public void setClasse(Class classe) {
        this.classe = classe;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setIsAutoIncrement(Boolean isAutoIncrement) {
        this.isAutoIncrement = isAutoIncrement;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public void setAttributes(Map<String, Column> attributes) {
        this.attributes = attributes;
    }
    
}
