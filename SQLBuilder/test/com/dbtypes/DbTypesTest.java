/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dbtypes;

import java.util.Date;
import javax.persistence.Column;
import junit.framework.TestCase;

/**
 *
 * @author paulo.gomes
 */
public class DbTypesTest extends TestCase {
    
    private DbTypes type;
    private CustomColumBuilder column;
    
    public DbTypesTest(String testName) {
        super(testName);
        column = new CustomColumBuilder();
    }
    
    public void testMysqlDbTypes(){
        type = new MySqlType();
        column.setColumnDefinition(String.class.toString());
        this.assertEquals("VARCHAR(255)", type.getDbType(column));
        column.setColumnDefinition(Integer.class.toString());
        this.assertEquals("INTEGER", type.getDbType(column));
        column.setColumnDefinition(Date.class.toString());
        this.assertEquals("DATETIME", type.getDbType(column));
        column.setColumnDefinition(Double.class.toString());
        this.assertEquals("DECIMAL(10,2)", type.getDbType(column));
    }
    
     public void testPostGresDbTypes(){
        type = new PostGresType();
        column.setColumnDefinition(String.class.toString());
        this.assertEquals("CHARACTER VARYING(255)", type.getDbType(column));
        column.setColumnDefinition(Integer.class.toString());
        this.assertEquals("INTEGER", type.getDbType(column));
        column.setColumnDefinition(Date.class.toString());
        this.assertEquals("TIMESTAMP", type.getDbType(column));
        column.setColumnDefinition(Double.class.toString());
        this.assertEquals("DECIMAL(10,2)", type.getDbType(column));
    }
    
}
