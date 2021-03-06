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
public class Equal extends AbstractCondition {

    public Equal(String field, Object value) {
        super(field, value);
    }

    @Override
    public String getOperator(DbTypes db) {
        return createOperator( db.equal(), db.flag());
    }
}
