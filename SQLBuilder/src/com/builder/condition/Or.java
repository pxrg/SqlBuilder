/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builder.condition;

import com.dbtypes.DbTypes;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author prg
 */
public class Or implements Condition{
    
    List<Condition> conditions;

    public Or(List<Condition> conditions) {
        this.conditions = conditions;
    }

    @Override
    public String getOperator(DbTypes db) {
        StringBuilder aux = new StringBuilder();
        for (Condition condition : conditions) {
            aux.append(condition.getOperator(db)).append(Condition.sep);
        }
        return aux.toString();
    }

    @Override
    public String getField() {
        StringBuilder aux = new StringBuilder();
        for (Condition condition : conditions) {
            aux.append(condition.getField()).append(Condition.sep);
        }
        return aux.toString();
    }

    @Override
    public Object getValue() {
        List aux = new ArrayList();
        for (Condition condition : conditions) {
            aux.add(condition.getValue());
        }
        return aux;
    }
    
}
