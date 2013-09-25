/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.factory.classes;

import com.classes.CarroTeste;
import com.exceptions.ClassBuilderException;
import junit.framework.TestCase;

/**
 *
 * @author prxgomes
 */
public class ClassBuilderTest extends TestCase {
    
    CarroTeste car;
    
    public ClassBuilderTest(String testName) {
        super(testName);
        car = new CarroTeste(1, "maaa", "mooo");
    }

    public void testRetornaMesmaInstancia() throws ClassBuilderException{
        CarroTeste novo = ClassBuilder.getInstance(CarroTeste.class);
        this.assertNotNull(novo);
    }
    
}
