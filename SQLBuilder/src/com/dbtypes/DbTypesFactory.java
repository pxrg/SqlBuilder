/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dbtypes;

/**
 *
 * @author paulo.gomes
 */
public class DbTypesFactory {

    private DbTypesFactory() {
    }

    public static DbTypes getInstance(String dataBase) {
        DbTypes db = null;
        dataBase = dataBase.toLowerCase();
        if (dataBase.equals("mysql")) {
            db = new MySqlType();
        }else if(dataBase.equals("postgres")){
            db = new PostGresType();
        }
        return db;
    }
}
