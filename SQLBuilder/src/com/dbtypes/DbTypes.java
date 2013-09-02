/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dbtypes;

import javax.persistence.Column;

/**
 *
 * @author paulo.gomes
 */
public abstract class DbTypes {
    
    String CREATE = "CREATE TABLE IF NOT EXISTS #1(#2)";
    String INSERT = "INSERT INTO #1(#2) VALUES(#3)";
    String SELECT = "SELECT #1 FROM #2";
    String UPDATE = "UPDATE #1";
    String DELETE = "DELETE FROM #1";
    
    public abstract String getDbType(Column column);
    
    public boolean useQuote(){ return false;}
    public boolean useDataBaseName(){ return false;}
    public boolean useTableName(){ return false;}
    
    public String getCREATE(){return CREATE;};
    public String getINSERT(){return INSERT;};
    public String getSELECT(){return SELECT;};
    public String getUPDATE(){return UPDATE;};
    public String getDELETE(){return DELETE;};
    
    public String select(){return " SELECT ";};
    public String insert(){return " INSERT ";};
    public String delete(){return " DELETE ";};
    public String update(){return " UPDATE ";};
    public String from(){return " FROM ";};
    public String into(){return " INTO ";};
    public String values(){return " VALUES ";};
    public String as(){return " AS ";};
    
    public String flag(){return "?";}
    public String set(){return " SET ";}
    
    public String where(){return " WHERE ";}
    public String and(){return " AND ";}
    public String or(){return " OR ";}
    public String not(){return " NOT ";}
    
    public String greater(){return " > ";}
    public String greaterEqual(){return " >= ";}
    public String less(){return " < ";}
    public String lessEqual(){return " <= ";}
    public String equal(){return " = ";}
    public String notEqual(){return " != ";}
    public String in(){return " IN ";}
    public String notIn(){return " NOT IN ";}
    public String like(){return " LIKE ";}
    public String between(){return " BETWEEN ";}
    public String notBetween(){return " NOT BETWEEN ";}
    public String isNull(){return " IS NULL ";}
    public String isNotNull(){return " IS NOT NULL ";}
    
    public String orderBy(){return " ORDER BY ";}
    public String desc(){return " DESC ";}
    public String asc(){return " ASC ";}
    public String limit(){return " LIMIT ";}
}
