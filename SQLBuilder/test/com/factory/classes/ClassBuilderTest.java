/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.factory.classes;

import com.classes.Carro;
import com.exceptions.ClassBuilderException;
import junit.framework.TestCase;

/**
 *
 * @author prxgomes
 */
public class ClassBuilderTest extends TestCase {
    
    Carro car;
    
    public ClassBuilderTest(String testName) {
        super(testName);
        car = new Carro(1, "maaa", "mooo");
    }

    public void testRetornaMesmaInstancia() throws ClassBuilderException{
        Carro novo = ClassBuilder.getInstance(Carro.class);
        this.assertNotNull(novo);
    }
    
}
