/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.analyzer;

import com.classes.Carro;
import com.classes.Cliente;
import com.classes.Livro;
import com.classes.PacienteTESTE;
import com.classes.Pessoa;
import com.exceptions.AnalyzeException;
import java.lang.reflect.Method;
import java.util.Map;
import javax.persistence.Column;
import static junit.framework.Assert.assertEquals;
import junit.framework.TestCase;

/**
 *
 * @author paulo.gomes
 */
public class AnalyzerClassTest extends TestCase {

    private Carro car;

    public AnalyzerClassTest(String testName) {
        super(testName);
        this.car = new Carro(1, "maaa", "mooo");
    }

    public void testRetornaAnotacaoDaClasse() {
        String ann = AnalyzerClass.getClassAnnotation(car);
        this.assertEquals("CARRO", ann);
        ann = AnalyzerClass.getClassAnnotation(Carro.class);
        this.assertEquals("CARRO", ann);
    }

    public void testRetornaNomesDosMetodosEMetodos() {
        Map<String, Method> attr = AnalyzerClass.getMapMethods(car);
        this.assertTrue(attr.containsKey("getMarca"));
        this.assertTrue(attr.containsKey("getModelo"));
    }

    public void testRetornaTodasMapsDosAtributosDaClasse() {
        Map<String, Column> maps = AnalyzerClass.getMapAttributesWithAnnotation(car);
        this.assertNotNull(maps);
        this.assertEquals(4, maps.size());
        for (String key : maps.keySet()) {
            this.assertNotNull(maps.get(key).name());
        }
    }

    public void testChamarMetodoPorReflection() throws AnalyzeException {
        String marca = car.getMarca();
        String chamada = AnalyzerClass.callMethodGet(car, "marca").toString();
        this.assertEquals(marca, chamada);
    }
    
    public void testChamarMetodoPorReflectionSuperClasse() throws AnalyzeException {
        Integer id = 10;
        Cliente cliente = new Cliente(id, "abc", "123", "123456");
        Integer chamada = (Integer) AnalyzerClass.callMethodGet(cliente, "id");
        this.assertEquals(id, chamada);
    }
    
    public void testPossuiMapParaAtributoId() throws AnalyzeException {
        String id = "id";
        Map<String, Column> maps = AnalyzerClass.getMapId(Carro.class);
        this.assertTrue(maps.containsKey(id));
        this.assertEquals("ID_CARRO", maps.get(id).name());
    }
    
    public void testChamarGetIdRelacionamento() throws AnalyzeException{
        Integer id = 10;
        Pessoa pessoa = new Pessoa(id, "Pessoa", "1234");
        car.setPessoa(pessoa);
        Integer newId = (Integer) AnalyzerClass.callMethodGetIdFrom(car, "pessoa");
        assertEquals(id, newId);
    }
    
    public void testGetIdRelacionamento() throws AnalyzeException{
        Integer id = 10;
        Pessoa pessoa = new Pessoa(id, "Pessoa", "1234");
        car.setPessoa(pessoa);
        Integer newId = (Integer) AnalyzerClass.callMethodGetIdFrom(car, "pessoa");
        assertEquals(id, newId);
    }
    
    public void testGetNomeAtributoId() throws AnalyzeException{
        String id = "id";
        String aux = AnalyzerClass.getNameIdAttributeForClass(car.getClass());
        assertEquals(id, aux);
    }
    
    public void testAtributoIdEAutoIncrement() throws AnalyzeException{
        assertTrue(AnalyzerClass.isAutoIncrementIdAttribute(car.getClass()));
    }
    
    public void testPegarAtributosDaSuperClasseComMapeamento(){
        assertEquals(4, AnalyzerClass.getMapAttributesWithAnnotation(Cliente.class).size());
    }
    
    public void testPegarAtributosDaSuperClasseSemMapeamento(){
        assertEquals(7, AnalyzerClass.getMapAttributesWithAnnotation(PacienteTESTE.class).size());
    }
    
    public void testRetornaNomeDaClasseSemPackage(){
        String classe = "Carro";
        assertEquals(classe, AnalyzerClass.getSimpleClassName(Carro.class));
    }
    
    public void testPegarMapDosAtributosDaClasseRelacionadaPeloAtributo() throws AnalyzeException{
        Pessoa pessoa = new Pessoa(10, "Pessoa", "1234");
        car.setPessoa(pessoa);
        Map<String, Column> attrs = AnalyzerClass.getMapAttributesFromFieldClass(Carro.class, "pessoa");
        assertEquals(3, attrs.size());
        assertTrue(attrs.containsKey("nome"));
        assertTrue(attrs.containsKey("cpf"));
    }
    
}
