/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builder.condition;

import com.builder.condition.Conditions;
import com.builder.condition.Condition;
import com.dbtypes.DbTypes;
import com.dbtypes.DbTypesFactory;
import java.util.List;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author prg
 */
public class CondictionsTest {

    DbTypes db;

    public CondictionsTest() {
        db = DbTypesFactory.getInstance("postgres");
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testRetornaCondicaoTest() {
        Condition cond = Conditions.equal(null, null);
        assertNotNull(cond);
    }

    @Test
    public void testRetornaValoresPassadosCondiction() {
        String field = "id";
        Integer value = 10;
        Condition cond = Conditions.equal(field, value);
        assertEquals(field, cond.getField());
        assertEquals(value, cond.getValue());
    }

    @Test
    public void testRetornaMesmaCondicaoPassada() {
        assertEquals(db.equal()+db.flag(), Conditions.equal(null, null).getOperator(db));
    }

    @Test
    public void testAninhamentoDeCondicoesNotEqual() {
        String field = "id";
        Integer value = 10;
        Condition condNot = Conditions.not(Conditions.equal(field, value));
        assertEquals(db.notEqual()+db.flag(), condNot.getOperator(db));
    }
    
    @Test
    public void testAninhamentoDeCondicoesNotMaisOutrosSql() {
        String field = "id";
        Integer value = 10;
        Condition condNot = Conditions.not(Conditions.greater(field, value));
        assertEquals(db.not()+db.greater()+db.flag(), condNot.getOperator(db));
    }
    
    @Test
    public void testAninhamentoDeCondicoesMaiorIgual() {
        String field = "id";
        Integer value = 10;
        Condition cond = Conditions.greaterEqual(field, value);
        assertEquals(db.greaterEqual()+db.flag(), cond.getOperator(db));
    }
    
    @Test
    public void testAninhamentoDeCondicoesNotRetornaValoresOriginais() {
        String field = "id";
        Integer value = 10;
        Condition condNot = Conditions.not(Conditions.equal(field, value));
        assertEquals(field, condNot.getField());
        assertEquals(value, condNot.getValue());
    }
    
    @Test
    public void testSqlIn(){
        String id = "id";
        List values = new ArrayList();
        values.add(1);
        values.add(1);
        values.add(1);
        values.add(1);
        Condition cond = Conditions.in(id, values);
        assertEquals(db.in()+"(?,?,?,?)", cond.getOperator(db));
    }
}