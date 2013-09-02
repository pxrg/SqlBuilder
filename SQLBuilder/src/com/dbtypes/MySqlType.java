/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dbtypes;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.Column;

/**
 *
 * @author paulo.gomes
 */
public class MySqlType extends DbTypes{
    
    private Map<String, String> types;

    public MySqlType() {
        this.types = new HashMap();
        types.put("int", "INTEGER");
        types.put("integer", "INTEGER");
        types.put("long", "BIGINT");
        types.put("char", "CHAR(1)");
        types.put("boolean", "CHAR(1)");
        types.put("string", "VARCHAR(#1)");
        types.put("text", "TEXT");
        types.put("double", "DECIMAL(#1,#2)");
        types.put("float", "FLOAT");
        types.put("date", "DATETIME");
    }
    
    @Override
    public String getDbType(Column column) {
        String convertType = column.columnDefinition().toLowerCase();
        if(convertType.contains(".")){
            int lastIndex = convertType.lastIndexOf(".");
            convertType = convertType.substring(lastIndex + 1);
        }
        if (!this.types.containsKey(convertType)) {
            return null;
        }
        String sqlType = this.types.get(convertType);
        if (convertType.equals("string")) {
            sqlType = sqlType.replace("#1", String.valueOf(column.length()));
        }else if (convertType.equals("double")) {
            sqlType = sqlType.replace("#1", String.valueOf(column.precision()));
            sqlType = sqlType.replace("#2", String.valueOf(column.scale()));
        }
        return sqlType;        
    }
    
}
