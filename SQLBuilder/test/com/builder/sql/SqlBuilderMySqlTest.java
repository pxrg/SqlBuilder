/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builder.sql;

import com.builder.condition.Condition;
import com.builder.condition.Conditions;
import com.builder.condition.Order;
import com.classes.Carro;
import com.classes.OutroCarro;
import com.classes.Pessoa;
import com.exceptions.AnalyzeException;
import com.exceptions.SqlBuilderException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Igor
 */
public class SqlBuilderMySqlTest {

    SqlBuilder sql;
    Carro car;

    public SqlBuilderMySqlTest() throws SqlBuilderException {
        SqlBuilder.setDataBaseName("TESTE");
        SqlBuilder.setDataBaseType("mysql");
        car = new Carro(1, "maaa", "mooo");
        try {
            sql = new SqlBuilder(Carro.class);
        } catch (Exception ex) {
            throw new SqlBuilderException(ex.getMessage());
        }
    }

    @Test
    public void testMySqlCriarScriptsDMLNaoVazio() throws SqlBuilderException {
        try {
            sql.select().
                    add(Conditions.equal("id", 8));
            System.out.println(sql.getQuery());
            assertFalse(sql.getQuery().toString().isEmpty());
        } catch (Exception ex) {
            throw new SqlBuilderException(ex.getMessage());
        }
    }

    @Test
    public void testMySqlAdicionarCondicaoWhereEAnd() throws SqlBuilderException {
        try {
            String script = "SELECT CARRO.ID_CARRO,CARRO.ID_PESSOA,CARRO.MARCA,"
                    + "CARRO.MODELO FROM CARRO WHERE CARRO.ID_CARRO BETWEEN ? AND ?"
                    + " AND CARRO.MARCA LIKE ?";
            sql.select()
                    .add(Conditions.between("id", null, null))
                    .add(Conditions.like("marca", null));
            System.out.println(sql.getQuery());
            assertEquals(script, sql.getQuery().toString());
        } catch (Exception ex) {
            throw new SqlBuilderException(ex.getMessage());
        }
    }

    @Test
    public void testMySqlRetornaSomenteIdEmSelect() throws SqlBuilderException {
        try {
            String script = "SELECT CARRO.ID_CARRO FROM CARRO";
            sql.select(new String[]{"id"});
            System.out.println(sql.getQuery());
            assertEquals(script, sql.getQuery().toString());
        } catch (Exception ex) {
            throw new SqlBuilderException(ex.getMessage());
        }
    }

    @Test
    public void testMySqlRetornaScriptUpdate() throws SqlBuilderException {
        try {
            String script = "UPDATE CARRO SET ID_CARRO = ?, MARCA = ? "
                    + "WHERE ID_CARRO = ?";
            sql.update().set("id").set("marca").add(Conditions.equal("id", null));
            System.out.println(sql.getQuery());
            assertEquals(script, sql.getQuery().toString());
        } catch (Exception ex) {
            throw new SqlBuilderException(ex.getMessage());
        }
    }

    @Test
    public void testMySqlRetornaScriptInsertIdAutoIncrement() throws SqlBuilderException {
        try {
            String script = "INSERT INTO CARRO(ID_PESSOA,MARCA,MODELO)"
                    + " VALUES( ?, ?, ?)";
            sql.insert(car);
            System.out.println(sql.getQuery());
            assertEquals(script, sql.getQuery().toString());
        } catch (Exception ex) {
            throw new SqlBuilderException(ex.getMessage());
        }
    }

    @Test
    public void testMySqlRetornaScriptInsertIdNaoAutoIncrement() throws SqlBuilderException {
        try {
            SqlBuilder build = new SqlBuilder(OutroCarro.class);
            OutroCarro carro = new OutroCarro(10, "oooo", "aaaa");
            String script = "INSERT INTO CARRO(ID_CARRO,ID_PESSOA,MARCA,MODELO)"
                    + " VALUES( ?, ?, ?, ?)";
            build.insert(carro);
            System.out.println(build.getQuery());
            assertEquals(script, build.getQuery().toString());
        } catch (Exception ex) {
            throw new SqlBuilderException(ex.getMessage());
        }
    }

    @Test(expected = SqlBuilderException.class)
    public void testRetornaExceptionAtributoNullableFalse() throws SqlBuilderException, AnalyzeException {
        Carro carro = new Carro();
        sql.insert(carro);
    }

    @Test
    public void testPossuiClausulaOrderELimit() throws SqlBuilderException {
        String query = "SELECT CARRO.ID_CARRO FROM CARRO ORDER BY CARRO.ID_CARRO ASC  LIMIT ?";
        sql.select(new String[]{"id"}).setOrder(Order.ASC, "id").maxResult(1);
        assertEquals(query, sql.getQuery().toString());
    }
    
    @Test
    public void testPossuiClausulaMaisDeUmaOrderELimit() throws SqlBuilderException {
        String query = "SELECT CARRO.ID_CARRO FROM CARRO ORDER BY CARRO.ID_CARRO ASC  , CARRO.MARCA DESC  LIMIT ?";
        sql.select(new String[]{"id"}).setOrder(Order.ASC, "id").setOrder(Order.DESC, "marca").maxResult(1);
        assertEquals(query, sql.getQuery().toString());
    }

    @Test
    public void testConstroiSqlComClausulaOr() throws SqlBuilderException {
        String query = "SELECT CARRO.ID_CARRO FROM CARRO WHERE CARRO.ID_CARRO = ? OR CARRO.MARCA = ?";
        sql.select(new String[]{"id"}).add(
                Conditions.or(new Condition[]{Conditions.equal("id", 0), Conditions.equal("marca", 0)}));
        assertEquals(query, sql.getQuery().toString());
    }

    @Test
    public void testRetornaOAliasDeQualquerAtributo() throws SqlBuilderException {
        String alias = "ps";
        sql.select(new String[]{"id"});
        sql.createAlias("pessoa", alias);
        assertEquals(alias, sql.getAliasName("ps.id"));
    }

    @Test
    public void testCriarAliasEntidadeRelacionada() throws SqlBuilderException {
        String query = "SELECT CARRO.ID_CARRO FROM CARRO, PESSOA  WHERE CARRO.ID_PESSOA = PESSOA.ID_PESSOA";
        sql.select(new String[]{"id"});
        sql.createAlias("pessoa", "ps");
        assertEquals(query, sql.getQuery().toString());
    }
    
    @Test
    public void testCriarAliasEntidadeRelacionadaEmQualquerPontoDaQuery() throws SqlBuilderException {
        String query = "SELECT CARRO.ID_CARRO FROM CARRO , PESSOA "
                + "WHERE CARRO.MARCA = ? AND CARRO.ID_PESSOA = PESSOA.ID_PESSOA";
        sql.select(new String[]{"id"});
        sql.add(Conditions.equal("marca", ""));
        sql.createAlias("pessoa", "ps");
        assertEquals(query, sql.getQuery().toString());
    }

    @Test
    public void testRetornaNomeAtributoAPartirDoNomeDaColuna() throws SqlBuilderException {
        String nome = "id";
        sql.getAttributeNameFromColumnName("ID_CARRO");
        assertEquals(nome, sql.getAttributeNameFromColumnName("ID_CARRO"));
    }

    @Test
    public void testRetornaSqlComTabelasRelacionais() throws SqlBuilderException {
        String query = "SELECT CARRO.ID_CARRO FROM CARRO, PESSOA  WHERE "
                + "CARRO.ID_PESSOA = PESSOA.ID_PESSOA AND PESSOA.NOME = ?";
        Pessoa pessoa = new Pessoa(10, "Pessoa", "1234");
        car.setPessoa(pessoa);
        sql.select(new String[]{"id"}).createAlias("pessoa", "pss");
        sql.add(Conditions.equal("pss.nome", ""));
        assertEquals(query, sql.getQuery().toString());
    }
}
