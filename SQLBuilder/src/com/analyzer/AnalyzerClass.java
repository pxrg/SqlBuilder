package com.analyzer;

import com.annotations.CustomColumn;
import com.exceptions.AnalyzeException;
import com.util.StringUtils;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Classe apenas com metodos estaticos encarregada por filtrar e armazenar as
 * informacoes mapeadas das classes como classe, atributos e metodos, sendo esta
 * tambem responsavel pela invocacao dos mesmos por reflection. Todos classes ja
 * utilizadas, sao armazenadas para evitar reprocessamento de informacoes.
 *
 * @author paulo.gomes
 */
@SuppressWarnings("unchecked")
public class AnalyzerClass {

    /**
     * Contem o nome das classe(getName()) e um map com as informacoes das
     * classes.
     */
    private static Map<String, ClassStructure> _entitys;
    /**
     * Package das classes
     */
    private static String packageClass;

    static {
        _entitys = new HashMap();
        packageClass = "";
    }

    /**
     * Retorna o nome simples da classe sem package
     *
     * @param classe Classe a que se deseja o nome
     * @return String com o nome da classe
     */
    public static String getSimpleClassName(Class classe) {
        return classe.getSimpleName().contains(".")
                ? classe.getSimpleName().substring(classe.getSimpleName().lastIndexOf(".") + 1)
                : classe.getSimpleName();
    }

    /**
     * Verifica o nome contido na annotation
     *
     * @Table da classe do objeto utilizando como referencia para a tabela no
     * banco de dados invoca a sobrecarga com o parametro class
     *
     * @param obj objeto na qual sera utilizado a classe para analize
     * @return String com o 'name' contido
     */
    public static String getClassAnnotation(Object obj) {
        return getClassAnnotation(obj.getClass());
    }

    /**
     * Verifica o nome contido na annotation
     *
     * @Table da classe utilizando como referencia para a tabela no banco de
     * dados
     *
     * @param classe classe a ser analizada
     * @return String com o 'name' contido
     */
    public static String getClassAnnotation(Class classe) {
        Table entity = (Table) classe.getAnnotation(Table.class);
        return entity != null ? entity.name() : null;
    }

    /**
     * Varre todos os metodos da classe e da super classe( Caso a mesma nao seja
     * Object ) e cria um Map com <nome do metodo,metodo >
     * sendo possivel invocalos.
     *
     * A varredura é executada apenas uma vez, sendo armazanada no atributo
     * _entitys, utilizando essa referencia para utilizacao posteriores
     *
     * * invoca a sobrecarga com o parametro class
     *
     * @param obj Objeto na qual sera utilizado a classe para analize
     * @return Map com os nomes dos metodos como chave e os metodos
     */
    public static Map<String, Method> getMapMethods(Object obj) {
        return getMapMethods(obj.getClass());
    }

    /**
     * Varre todos os metodos da classe e da super classe( Caso a mesma nao seja
     * Object ) e cria um Map com <nome do metodo,metodo >
     * sendo possivel invocalos. A varredura é executada apenas uma vez, sendo
     * armazanada no atributo _entitys, utilizando essa referencia para
     * utilizacao posteriores
     *
     * @param classe classe a ser analizada
     * @return Map com os nomes dos metodos como chave e os metodos
     */
    public static Map<String, Method> getMapMethods(Class classe) {
        if (_entitys.containsKey(classe.getName()) && !_entitys.get(classe.getName()).methods.isEmpty()) {
            return _entitys.get(classe.getName()).methods;
        }
        Map<String, Method> metodos = new HashMap();
        ArrayList<Method> methods = new ArrayList(Arrays.asList(classe.getDeclaredMethods()));
        if (hasSuperClass(classe)) {
            List<Method> aux = Arrays.asList(classe.getSuperclass().getDeclaredMethods());
            if (aux != null && aux.size() > 0) {
                methods.addAll(aux);
            }
        }
        for (Method metod : methods) {
            metodos.put(metod.getName(), metod);
        }
        addMethodToMapEntity(classe, metodos);
        return metodos;
    }

    /**
     * Varre todos os atributos da classe e sua super classe ( Caso a mesma seja
     * diferente de Object ) verificando se os mesmos possuem a annotation
     *
     * @Column ou
     * @ManyToOne +
     * @JoinColumn (Especificas para o funcionamento da classe SqlBuilder)
     * verificando tambem
     * @Id e o
     * @GeneratedValue, considerando apenas o strategy AUTO como auto_increment
     * no banco.
     *
     * * A varredura é executada apenas uma vez, sendo armazanada no atributo
     * _entitys, utilizando essa referencia para utilizacao posteriores
     *
     * * invoca a sobrecarga com o parametro class
     *
     * @param obj Objeto na qual sera utilizado a classe para analize
     * @return Map com nome do atributo e Column com as informacoes do mesmo.
     */
    public static Map<String, Column> getMapAttributesWithAnnotation(Object obj) {
        return getMapAttributesWithAnnotation(obj.getClass());
    }

    /**
     * Varre todos os atributos da classe e sua super classe ( Caso a mesma seja
     * diferente de Object ) verificando se os mesmos possuem a annotation
     *
     * @Column ou
     * @ManyToOne +
     * @JoinColumn (Especificas para o funcionamento da classe SqlBuilder)
     * verificando tambem
     * @Id e o
     * @GeneratedValue, considerando apenas o strategy AUTO como auto_increment
     * no banco.
     *
     * * A varredura é executada apenas uma vez, sendo armazanada no atributo
     * _entitys, utilizando essa referencia para utilizacao posteriores
     *
     * @param classe classe a ser analizada
     * @return Map com nome do atributo e Column com as informacoes do mesmo.
     */
    public static Map<String, Column> getMapAttributesWithAnnotation(Class classe) {
        if (_entitys.containsKey(classe.getName()) && !_entitys.get(classe.getName()).attributes.isEmpty()) {
            return _entitys.get(classe.getName()).attributes;
        }
        Map<String, Column> atributos = new HashMap();
        ArrayList<Field> fields = new ArrayList(Arrays.asList(classe.getDeclaredFields()));
        if (hasSuperClass(classe)) {
            List<Field> fieldsAux = Arrays.asList(classe.getSuperclass().getDeclaredFields());
            if (fieldsAux != null && fieldsAux.size() > 0) {
                fields.addAll(fieldsAux);
            }
        }
        CustomColumn column = null;
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                column = CustomColumn.getInstance(field.getAnnotation(Column.class));
                column.setColumnDefinition(field.getType().getName());
                atributos.put(field.getName(), column);
                if (field.isAnnotationPresent(Id.class)) {
                    boolean auto = false;
                    if (field.isAnnotationPresent(GeneratedValue.class)) {
                        GeneratedValue generat = (GeneratedValue) field.getAnnotation(GeneratedValue.class);
                        auto = generat.strategy().equals(GenerationType.AUTO);
                    }
                    addIdToMapEntity(classe, field.getName(), auto);
                }
            } else if (field.isAnnotationPresent(ManyToOne.class)) {
                column = CustomColumn.getInstance(field.getAnnotation(JoinColumn.class));
                column.setColumnDefinition(field.getType().getName());
                atributos.put(field.getName(), column);
            }
        }
        addAttributeToMapEntity(classe, atributos);
        return atributos;
    }

    /**
     * Verifica se a classe ja foi mapeada criando um novo mapeamento caso nao
     * seja encontrado, ou atualiza caso ja exista
     *
     * @param classe classe a ser mapeada
     * @param id nome do atributo com annotation
     * @Id
     * @param auto definicao da estategia de criacao do id true ==
     * auto_increment
     */
    private static void addIdToMapEntity(Class classe, String id, Boolean auto) {
        if (_entitys.containsKey(classe.getName())) {
            _entitys.get(classe.getName()).id = id;
            _entitys.get(classe.getName()).autoIncrement = auto;
        } else {
            _entitys.put(classe.getName(), new ClassStructure(classe, id, auto));
        }
    }

    /**
     * Verifica se a classe ja foi mapeada criando um novo mapeamento caso nao
     * seja encontrado, ou atualiza caso ja exista
     *
     * @param classe classe a ser mapeada
     * @param maps map com os atributos processados
     */
    private static void addAttributeToMapEntity(Class classe, Map maps) {
        if (_entitys.containsKey(classe.getName())) {
            _entitys.get(classe.getName()).attributes.putAll(maps);
        } else {
            ClassStructure classInfo = new ClassStructure(classe);
            classInfo.attributes.putAll(maps);
            _entitys.put(classe.getName(), classInfo);
        }
    }

    /**
     * Verifica se a classe ja foi mapeada criando um novo mapeamento caso nao
     * seja encontrado, ou atualiza caso ja exista
     *
     * @param classe classe a ser mapeada
     * @param maps Map com os metodos processados
     */
    private static void addMethodToMapEntity(Class classe, Map maps) {
        if (_entitys.containsKey(classe.getName())) {
            _entitys.get(classe.getName()).methods.putAll(maps);
        } else {
            ClassStructure classInfo = new ClassStructure(classe);
            classInfo.methods.putAll(maps);
            _entitys.put(classe.getName(), classInfo);
        }
    }

    public static void Analyzer(Object obj) throws AnalyzeException {
        Analyzer(obj.getClass());
    }

    /**
     * Caso a classe nao tenha sido mapeada, e feito a analize dos seus
     * atributos e metodos, em seguida sao mapeados, caso contrario nada e
     * feito.
     *
     * @param classe classe a ser analizada
     * @throws AnalyzeException Caso ocorra algum erro, a excessao sera lancada
     */
    public static void Analyzer(Class classe) throws AnalyzeException {
        try {
            setPackage(classe);
            if (!_entitys.containsKey(classe.getName())) {
                getMapAttributesWithAnnotation(classe);
                getMapMethods(classe);
            }
            if (_entitys.get(classe.getName()).methods.isEmpty()) {
                getMapMethods(classe);
            }
        } catch (Exception ex) {
            throw new AnalyzeException(ex.getMessage());
        }

    }

    /**
     * Apenas capitaliza o nome do atributo, concatenando o 'get' para a
     * formacao do nome padrao do atributo.
     *
     * @param field nome do atributo
     * @return String com o formato padrao do metodo get referente ao atributo
     */
    public static String methodNameGet(String field) {
        return "get" + StringUtils.capitalizeCaseSensitive(field);
    }

    /**
     * Apenas capitaliza o nome do atributo, concatenando o 'set' para a
     * formacao do nome padrao do atributo.
     *
     * @param field nome do atributo
     * @return String com o formato padrao do metodo set referente ao atributo
     */
    public static String methodNameSet(String field) {
        return "set" + StringUtils.capitalizeCaseSensitive(field);
    }

    /**
     * Package da classe passada como parametro
     *
     * @return String com o nome do package
     */
    public static String getPackage() {
        return packageClass;
    }

    /**
     * Formata e extrai o nome do package da classe
     *
     * @param classe classe a ser analizada
     */
    private static void setPackage(Class classe) {
        if (packageClass.isEmpty() || !classe.getCanonicalName().contains(packageClass)) {
            String classPath = classe.getCanonicalName();
            packageClass = classPath.substring(0, classPath.lastIndexOf("."));
        }
    }

    /**
     * E feita a analize da classe e em seguida retornado um Map com os dados do
     * atributo marcado com a annotation
     *
     * @Id
     * @param classe classe a ser analizada
     * @return Map com o nome do atributo e um column com os dados do mesmo.
     * @throws AnalyzeException Caso ocorra algum erro.
     */
    public static Map<String, Column> getMapId(Class classe) throws AnalyzeException {
        Analyzer(classe);
        Map<String, Column> resultMap = new HashMap();
        String id = _entitys.get(classe.getName()).id;
        resultMap.put(id, (Column) _entitys.get(classe.getName()).attributes.get(id));
        return resultMap;
    }

    /**
     * Invoca o metodo 'get' referente ao objeto passado, utilizando o nome do
     * atributo para acesso ao mesmo ( Utiliza o pressuposto que o metodo segue
     * o padrao 'get'+ atributo(capitalizado)) retornando um Object com o valor
     * contido no atributo.
     *
     * @param obj Objeto no qual sera invocado o metodo
     * @param field nome do atributo no qual se deseja o valor atraves do metodo
     * get
     * @return Object com o valor contido no atributo.
     * @throws AnalyzeException As causas podem ser : IllegalAccessException,
     * IllegalArgumentException, InvocationTargetException
     */
    public static Object callMethodGet(Object obj, String field) throws AnalyzeException {
        try {
            Analyzer(obj);
            ClassStructure classAux = _entitys.get(obj.getClass().getName());
            if (classAux.attributes.containsKey(field) && classAux.methods.isEmpty()) {
                getMapMethods(obj);
            }
            Method method = (Method) classAux.methods.get(methodNameGet(field));
            return method.invoke(obj, null);
        } catch (IllegalAccessException ex) {
            throw new AnalyzeException(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            throw new AnalyzeException(ex.getMessage());
        } catch (InvocationTargetException ex) {
            throw new AnalyzeException(ex.getMessage());
        }
    }

    /**
     * Invoca o metodo get do atributo anotado como
     *
     * @Id da classe relacionada ao objeto passado como parametro, utilizando
     * para isso o nome do atributo que deve ser uma entidade mapeada. Este
     * metodo utiliza internamente o metodo 'callMethodGet'
     * @param obj Objeto a ser utilizado como referencia
     * @param fieldEntiy nome do atributo da classe que deve ser outra entidade
     * @return O valor contido no atributo demarcado como
     * @Id da classe relacionada ou null caso a mesma nao tenha sido instanciada
     * @throws AnalyzeException Erros referentes a chamada de metodos, ver
     * 'callMethodGet'
     */
    public static Object callMethodGetIdFrom(Object obj, String fieldEntiy) throws AnalyzeException {
        Analyzer(obj);
        Object returned = callMethodGet(obj, fieldEntiy);
        if (returned == null) {
            return null;
        }
        Analyzer(returned);
        return callMethodGet(returned, _entitys.get(returned.getClass().getName()).id);
    }

    /**
     * Faz a chamada do metodo get do objeto, verificando se o retorno obtido e
     * uma classe do mesmo package( Uma entidade ), caso seja e invocado o
     * metodo 'callMethodGetIdFrom', caso contrario e retornado o valor obtido.
     *
     * * Este metodo utiliza internamente o metodo 'callMethodGet' e
     * 'callMethodGetIdFrom'
     *
     * @param obj Objeto a ser utilizado como referencia
     * @param field atributo no qual se deseja o valor
     * @return Object com o valor do atributo passado como paramtro
     * @throws AnalyzeException Erros referentes a chamada de metodos, ver
     * 'callMethodGet' e 'callMethodGetIdFrom'
     */
    public static Object callMethodGetNativeObjectOrId(Object obj, String field) throws AnalyzeException {
        Analyzer(obj);
        Object aux = callMethodGet(obj, field);
        if (aux == null) {
            return null;
        }
        if (aux.getClass().getCanonicalName().contains(packageClass)) {
            return callMethodGetIdFrom(obj, field);
        } else {
            return aux;
        }
    }

    /**
     * Retorna o nome do atributo mapeado como
     *
     * @Id
     * @param classe classe a ser analizada
     * @return String com o nome do atributo anotado como
     * @Id
     * @throws AnalyzeException Referente a analize da classe
     */
    public static String getNameIdAttributeForClass(Class classe) throws AnalyzeException {
        Analyzer(classe);
        return _entitys.get(classe.getName()).id;
    }

    /**
     * Analiza a classe e retorna se a mesma e anotada com estategia de geracao
     * automatica de id
     *
     * @param classe classe a ser analizada
     * @return true caso seja de geracao automatica de id
     * @throws AnalyzeException Referente a analiza da classe
     */
    public static boolean isAutoIncrementIdAttribute(Class classe) throws AnalyzeException {
        Analyzer(classe);
        return _entitys.get(classe.getName()).autoIncrement;
    }

    /**
     * Se a super classe da classe passada como parametro for diferente de
     * Object(Classe base de todas) é retornado true (Herda de alguma outra
     * classe) e se possui o mesmo path das outras classes mapeadas
     *
     * @param classe classe a ser analizada
     * @return true caso a super classe seja do mesmo package ou diferente de
     * Object
     */
    private static boolean hasSuperClass(Class classe) {
        return classe.getSuperclass().getName().contains(packageClass) || !classe.getSuperclass().equals(Object.class);
    }

    public static Class getClassFromField(Class classe, String field) throws AnalyzeException {
        Analyzer(classe);
        return _entitys.get(classe.getName()).methods.get(methodNameGet(field)).getReturnType();
    }

    public static Map<String, Column> getMapAttributesFromFieldClass(Class classe, String field) throws AnalyzeException {
        return getMapAttributesWithAnnotation(getClassFromField(classe, field));
    }

    private static class ClassStructure {

        public Class classe;
        public String id;
        public Boolean autoIncrement = false;
        public Map<String, Column> attributes = new HashMap();
        public Map<String, Method> methods = new HashMap();

        public ClassStructure(Class classe) {
            this.classe = classe;
        }

        public ClassStructure(Class classe, String id, Boolean auto) {
            this.classe = classe;
            this.id = id;
            this.autoIncrement = auto;
        }
    }
}
