/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builder.condition;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author prg
 */
public class Conditions {
    
    public static Condition equal(String field, Object value){
        return new Equal(field, value);
    }
    
    public static Condition not(Condition condiction){
        return new Not(condiction);
    }
    
    public static Condition greater(String field, Object value){
        return new Greater(field, value);
    }
    
    public static Condition greaterEqual(String field, Object value){
        return new Greater(new Equal(field, value));
    }
    
    public static Condition less(String field, Object value){
        return new Less(field, value);
    }
    
    public static Condition lessEqual(String field, Object value){
        return new Less(new Equal(field, value));
    }
    
    public static Condition in(String field, List values){
        return new In(field, values);
    }
    
    public static Condition between(String field, Object firstValue, Object secondValue){
        List values = new ArrayList();
        values.add(firstValue);
        values.add(secondValue);
        return new Between(field, values);
    }
    
    public static Condition like(String field, Object value){
        return new Like(field, value);
    }
    
    public static Condition like(String field, Object value, LikeCondiction cond){
        StringBuilder formatValue = new StringBuilder(value.toString());
        String flagLike = "%";
        switch (cond) {
            case START:
                formatValue.insert(0, flagLike);
                break;
            case END:
                formatValue.append(flagLike);
                break;
            default:
                formatValue.insert(0, flagLike);
                formatValue.append(flagLike);
                break;
        }
        return new Like(field, formatValue.toString());
    }

    public static Condition isNull(String field){
        return new IsNull(field, null);
    }
    
    public static Condition isNotNull(String field){
        return new IsNotNull(field, null);
    }
    
    public static Condition or(Condition... conditions){
        return new Or(Arrays.asList(conditions));
    }
}
