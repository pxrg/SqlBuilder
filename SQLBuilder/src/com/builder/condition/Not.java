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
public class Not extends AbstractCondition{
    
    private Condition cond;

    public Not(Condition condiction) {
        super(condiction);
        cond = condiction;
    }
    
    @Override
    public String getOperator(DbTypes db) {
        if (cond != null && cond.getOperator(db).contains(db.equal())) {
            return createOperator(db.notEqual(), db.flag());
        }
        return createOperator(db.not(), cond.getOperator(db));
    }
    
}
