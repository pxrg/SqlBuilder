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
public class IsNull extends AbstractCondition{

    public IsNull(String field, Object value) {
        super(field, value);
    }

    @Override
    public String getOperator(DbTypes db) {
        return db.isNull();
    }

    @Override
    public Object getValue() {
        return null;
    }
    
}
