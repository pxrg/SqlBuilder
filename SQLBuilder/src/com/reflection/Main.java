/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reflection;

import com.exceptions.AnalyzeException;
import com.exceptions.SqlBuilderException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 *
 * @author paulo.gomes
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws AnalyzeException, SQLException, SqlBuilderException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

//        Pessoa fulano = new Pessoa(20, "Ciclano", "11111111111");
//        Pessoa jao = new Pessoa(3, "Jao j", "999999999");
//        Carro gol = new Carro(1, "Volkswagem", "G5 2011");
//        gol.setPessoa(fulano);
//        SqlBuilder.setDataBaseName("DIAG");
//        SqlBuilder.setDataBaseType("postgres");
//        Object obj = gol.getClass().getDeclaredMethod("getId", null).invoke(gol, null);
//        System.out.println(obj);
//        System.out.println(Carro.class.getCanonicalName());
//
//        Map<String, Column> maps = AnalyzerClass.Analyzer(gol);
//        for (String key : maps.keySet()) {
//            System.out.println(key);
//            System.out.println(maps.get(key).columnDefinition());
//            if (maps.get(key).columnDefinition().contains(AnalyzerClass.getPackage()) && true) {
//                Object _obj_1 = AnalyzerClass.callMethodGetIdFrom(gol, key);
//                System.out.println(key + " : " + _obj_1);
//            }
//            System.out.println("\n");
//        }

//        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "admin");
//        SqlBuilder.setDataBaseName("TEST");
//        SqlBuilder.setDataBaseType("postgres");
//        SqlBuilder buildPessoa = new SqlBuilder(Pessoa.class);
//        SqlBuilder buildCarro = new SqlBuilder(Carro.class);
//        PreparedStatement statement = null;
//        ResultSet result = null;
//
//        SqlBuilder buildUser = new SqlBuilder(Usuario.class);
//        Usuario user = new Usuario(1, "user", new Date());
//        buildUser.insert(user);

//         //Criar tabela
//        String createPessoa = buildPessoa.createTable();
//        System.out.println(createPessoa);
//        PreparedStatement statement = conn.prepareStatement(createPessoa);
//        boolean foi = statement.execute();
//        //// Inserir Pessoa
//        buildPessoa.insert();
//        statement = conn.prepareStatement(buildPessoa.getQuery().toString());
//        statement.setInt(1, fulano.getId());
//        statement.setString(2, fulano.getNome());
//        statement.setString(3, fulano.getCpf());
//        statement.executeUpdate();
//        buildPessoa.select().equals("id");

//        buildPessoa.insert(jao);
//        buildPessoa.executeUpdate(conn);
//        
//        buildPessoa.select().like("cpf", "F%");
//        result = buildPessoa.executeQuery(conn);
//        if(result.next()){
//            System.out.println(result.getString(2));
//        }
//        System.out.println(buildCarro.select().equals("id", 1).like("marca").getQuery().toString());


//        buildCarro.insert(gol);
//        buildCarro.executeUpdate(conn);
//        
//        conn.close();
    }
}
