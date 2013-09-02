/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builder.condition;

import com.dbtypes.DbTypes;
import java.util.List;

/**
 *
 * @author prg
 */
public class In extends AbstractCondition{

    public In(String field, Object value) {
        super(field, value);
    }

    @Override
    public String getOperator(DbTypes db) {
        if (getValue() == null) {
            return null;
        }
        StringBuilder aux = new StringBuilder();
        aux.append("(");
        for (Object object : ((List)getValue())) {
            aux.append(db.flag()).append(",");
        }
        aux.deleteCharAt(aux.lastIndexOf(","));
        aux.append(")");
        return createOperator(db.in(), aux.toString());
    }
    
}
