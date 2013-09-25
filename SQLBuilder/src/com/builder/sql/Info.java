/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builder.sql;

import com.annotations.Column;
import java.util.Map;

/**
 *
 * @author prg
 */
interface Info {
    
    public Class getClasse();
    public String getTable();
    public String getAlias();
    public Boolean isAutoIncrement();
    public String getAttributeId();
    public Map<String, Column> getAttributes();
    
    public void setAlias(String alias);
}
