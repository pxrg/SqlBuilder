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
public class Less extends AbstractCondition{

    private Condition cond;
    
    public Less(Condition condiction) {
        super(condiction);
        cond = condiction;
    }
    
    public Less(String field, Object value) {
        super(field, value);
    }

    @Override
    public String getOperator(DbTypes db) {
        if (cond != null && cond.getOperator(db).contains(db.equal())) {
            return createOperator(db.lessEqual(), db.flag());
        }
        return createOperator(db.less(), db.flag());
    }
    
}
