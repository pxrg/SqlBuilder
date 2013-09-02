/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author paulo.gomes
 */
public class StringUtilsTest {
    
    public StringUtilsTest() {
    }
        
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testDeveCapitalizarString(){
        String test = "A Nova Mais Teste Um Sete Doze";
        String aux = StringUtils.capitalize(test.toLowerCase());
        System.out.println(aux);
        assertEquals(test, aux);
    }
    
    @Test
    public void testDeveCapitalizarUmaPalavra(){
        String test = "id";
        String aux = StringUtils.capitalize(test.toLowerCase());
        System.out.println(aux);
        assertEquals("Id", aux);
    }

}