/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.annotation;

import com.annotations.Column;
import com.annotations.GeneratedValue;
import com.annotations.Id;
import com.annotations.JoinColumn;
import com.annotations.ManyToOne;
import com.annotations.Table;
import com.classes.CarroTeste;
import com.classes.ClienteTeste;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import junit.framework.TestCase;

/**
 *
 * @author paulo.gomes
 */
public class AnnotationTest extends TestCase {

    public AnnotationTest(String testName) {
        super(testName);
    }
    // TODO add test methods here. The name must begin with 'test'. For example:
    // public void testHello() {}

    public void testPegarAnotacaoDoAtributoPorReflection() throws ClassNotFoundException {
        CarroTeste car = new CarroTeste(1, "m", "n");
        Field[] fields = car.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column attr = (Column) field.getAnnotation(Column.class);
                this.assertNotNull(attr);
            }
        }
    }

    public void testPegarAnotacaoDaClassePorReflection() throws ClassNotFoundException {
        CarroTeste car = new CarroTeste(1, "m", "n");
        Annotation anot = car.getClass().getAnnotation(Table.class);
        this.assertNotNull(anot);
    }

    public void testDevePegarAAnotacaoDeId() {
        CarroTeste car = new CarroTeste(1, "m", "n");
        Field[] fields = car.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                Id attr = (Id) field.getAnnotation(Id.class);
                GeneratedValue val = (GeneratedValue) field.getAnnotation(GeneratedValue.class);
                this.assertNotNull(attr);
                this.assertNotNull(val);
                this.assertNotNull(val.strategy());
                break;
            }
        }
    }

    public void testPegarRelacionamentos() {
        CarroTeste car = new CarroTeste(1, "m", "n");
        Field[] fields = car.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ManyToOne.class)) {                
                JoinColumn attrCol = (JoinColumn) field.getAnnotation(JoinColumn.class);
                ManyToOne attr = (ManyToOne) field.getAnnotation(ManyToOne.class);
                this.assertNotNull(attr);
                this.assertNotNull(attrCol);
                System.out.println(attrCol.name());
                break;
            }
        }
    }
    
    public void testVerificaSeEClasseSemHeranca(){
        CarroTeste car = new CarroTeste(1, "m", "n");
        this.assertTrue(car.getClass().getSuperclass().equals(Object.class));
    }
    
    public void testVerificaSeEClasseComHeranca(){
        ClienteTeste cliente = new ClienteTeste(1, "m", "n", "0123456");
        this.assertFalse(cliente.getClass().getSuperclass().equals(Object.class));
    }
    
    public void testPegarAnnotationsClasseComHerancaSuperClasseComAnnotation(){
        ClienteTeste cliente = new ClienteTeste(1, "m", "n", "0123456");
        Field[] fields = cliente.getClass().getFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column attr = (Column) field.getAnnotation(Column.class);
                this.assertNotNull(attr);
            }
        }
    }
    
}
