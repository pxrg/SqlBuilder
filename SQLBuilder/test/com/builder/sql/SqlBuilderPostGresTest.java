/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builder.sql;

import com.builder.condition.Conditions;
import com.builder.condition.Order;
import com.classes.Carro;
import com.classes.OutroCarro;
import com.classes.Pessoa;
import com.exceptions.AnalyzeException;
import com.exceptions.SqlBuilderException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author paulo.gomes
 */
public class SqlBuilderPostGresTest {

    SqlBuilder sql;
    Carro car;

    public SqlBuilderPostGresTest() throws SqlBuilderException {
        SqlBuilder.setDataBaseName("TESTE");
        SqlBuilder.setDataBaseType("postgres");
        car = new Carro(1, "maaa", "mooo");
        try {
            sql = new SqlBuilder(Carro.class);
        } catch (Exception ex) {
            throw new SqlBuilderException(ex.getMessage());
        }
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }
    
    
    @Test
    public void testPostGresCriarScriptsDMLNaoVazio() throws SqlBuilderException {
        try {
            sql.select().add(Conditions.equal("id", 8));
            System.out.println(sql.getQuery());
            assertFalse(sql.getQuery().toString().isEmpty());
        } catch (Exception ex) {
            throw new SqlBuilderException(ex.getMessage());
        }
    }

    @Test
    public void testPostGresAdicionarCondicaoWhereEAnd() throws SqlBuilderException {
        try {
            String script = "SELECT \"CARRO\".\"ID_CARRO\",\"CARRO\".\"ID_PESSOA\",\"CARRO\".\"MARCA\","
                    + "\"CARRO\".\"MODELO\" FROM \"TESTE\".\"CARRO\" WHERE \"CARRO\".\"ID_CARRO\" BETWEEN ? AND ?"
                    + " AND \"CARRO\".\"MARCA\" LIKE ?";
            sql.select()
                    .add(Conditions.between("id", null, null))
                    .add(Conditions.like("marca", null));
            System.out.println(sql.getQuery());
            assertEquals(script,sql.getQuery().toString());
        } catch (Exception ex) {
            throw new SqlBuilderException(ex.getMessage());
        }
    }

    @Test
    public void testPostGresRetornaSomenteIdEmSelect() throws SqlBuilderException {
        try {
            String script = "SELECT \"CARRO\".\"ID_CARRO\" FROM \"TESTE\".\"CARRO\"";
            sql.select(new String[]{"id"});
            System.out.println(sql.getQuery());
            assertEquals(script,sql.getQuery().toString());
        } catch (Exception ex) {
            throw new SqlBuilderException(ex.getMessage());
        }
    }

    @Test
    public void testPostGresRetornaScriptUpdate() throws SqlBuilderException {
        try {
            String script = "UPDATE \"TESTE\".\"CARRO\" SET \"ID_CARRO\" = ?, \"MARCA\" = ? "
                    + "WHERE \"ID_CARRO\" = ?";
            sql.update().set("id").set("marca").add(Conditions.equal("id", null));
            System.out.println(sql.getQuery());
            assertEquals(script,sql.getQuery().toString());
        } catch (Exception ex) {
            throw new SqlBuilderException(ex.getMessage());
        }
    }
    
     @Test
    public void testPostGresRetornaScriptUpdatePassandoObjeto() throws SqlBuilderException {
        try {
            String script = "UPDATE \"TESTE\".\"CARRO\" SET \"ID_PESSOA\" = ?, \"MARCA\" = ?, \"MODELO\" = ? "
                    + "WHERE \"ID_CARRO\" = ?";
            sql.update(car);
            System.out.println(sql.getQuery());
            assertEquals(script,sql.getQuery().toString());
        } catch (Exception ex) {
            throw new SqlBuilderException(ex.getMessage());
        }
    }
    
    @Test
    public void testPostGresRetornaScriptInsertIdAutoIncrement() throws SqlBuilderException {
        try {
            String script = "INSERT INTO \"TESTE\".\"CARRO\""
                    + "(\"ID_PESSOA\",\"MARCA\",\"MODELO\")"
                    + " VALUES( ?, ?, ?)";
            sql.insert(car);
            System.out.println(sql.getQuery());
            assertEquals(script,sql.getQuery().toString());
        } catch (Exception ex) {
            throw new SqlBuilderException(ex.getMessage());
        }
    }
    
    @Test
    public void testPostGresRetornaScriptInsertIdNaoAutoIncrement() throws SqlBuilderException {
        try {
            SqlBuilder build = new SqlBuilder(OutroCarro.class);
            OutroCarro carro = new OutroCarro(10, "oooo", "aaaa");
            String script = "INSERT INTO \"TESTE\".\"CARRO\""
                    + "(\"ID_CARRO\",\"ID_PESSOA\",\"MARCA\",\"MODELO\")"
                    + " VALUES( ?, ?, ?, ?)";
            build.insert(carro);
            System.out.println(build.getQuery());
            assertEquals(script,build.getQuery().toString());
        } catch (Exception ex) {
            throw new SqlBuilderException(ex.getMessage());
        }
    }
    
    @Test(expected = SqlBuilderException.class)
    public void testRetornaExceptionAtributoNullableFalse() throws SqlBuilderException, AnalyzeException{
        Carro carro = new Carro();
        sql.insert(carro);
    }
    
    @Test
    public void testPostGresRetornaSelectComAspasENomeDataBase() throws AnalyzeException, SqlBuilderException {
        sql.select("id");
        String query = "SELECT \"CARRO\".\"ID_CARRO\" FROM \"TESTE\".\"CARRO\"";
        assertEquals(query, sql.getQuery().toString());
        System.out.println(sql.getQuery().toString());
    }
    
    @Test
    public void testPossuiClausulaMaisDeUmaOrderELimit() throws SqlBuilderException {
        String query = "SELECT \"CARRO\".\"ID_CARRO\" FROM \"TESTE\".\"CARRO\" ORDER BY \"CARRO\".\"ID_CARRO\" ASC  , \"CARRO\".\"MARCA\" DESC  LIMIT ?";
        sql.select(new String[]{"id"}).setOrder(Order.ASC, "id").setOrder(Order.DESC, "marca").maxResult(1);
        assertEquals(query, sql.getQuery().toString());
    }
    
    @Test
    public void testRetornaSqlComTabelasRelacionais() throws SqlBuilderException {
        String query = "SELECT \"CARRO\".\"ID_CARRO\" FROM \"TESTE\".\"CARRO\", \"TESTE\".\"PESSOA\"  WHERE "
                + "\"CARRO\".\"ID_PESSOA\" = \"PESSOA\".\"ID_PESSOA\" AND \"PESSOA\".\"NOME\" = ?";
        Pessoa pessoa = new Pessoa(10, "Pessoa", "1234");
        car.setPessoa(pessoa);
        sql.select(new String[]{"id"}).createAlias("pessoa", "pss");
        sql.add(Conditions.equal("pss.nome", ""));
        assertEquals(query, sql.getQuery().toString());
    }
}