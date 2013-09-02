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
public interface Condition {
    
    public String sep = ",";
    
    public String getField();
    
    public abstract String getOperator(DbTypes db);
    
    public Object getValue();
    
    
}
