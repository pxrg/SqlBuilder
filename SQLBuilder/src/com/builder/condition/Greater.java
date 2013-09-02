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
public class Greater extends AbstractCondition{

    private Condition cond;
    
    public Greater(Condition condiction) {
        super(condiction);
        cond = condiction;
    }
    
    public Greater(String field, Object value) {
        super(field, value);
    }

    @Override
    public String getOperator(DbTypes db) {
        if (cond != null && cond.getOperator(db).contains(db.equal())) {
            return createOperator(db.greaterEqual(), db.flag());
        }
        return createOperator(db.greater(), db.flag());
    }
    
}
