/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builder.condition;

import com.dbtypes.DbTypes;

/**
 *
 * @author prg
 */
public abstract class AbstractCondition implements Condition{
    private String field;
    private Object value;

    public AbstractCondition(Condition condiction) {
        this.field = condiction.getField();
        this.value = condiction.getValue();
    }
    
    public AbstractCondition(String field, Object value) {
        this.field = field;
        this.value = value;
    }
    
    @Override
    public String getField(){
        return field;
    };
    
    @Override
    public abstract String getOperator(DbTypes db);
    
    @Override
    public Object getValue(){
        return value;
    };
    
    protected String createOperator(String... params){
        StringBuilder aux = new StringBuilder();
        for (String string : params) {
            aux.append(string);
        }
        return aux.toString();
    }
}
