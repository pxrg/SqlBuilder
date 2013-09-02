/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builder.sql;

import com.analyzer.AnalyzerClass;
import com.builder.condition.Condition;
import com.builder.condition.Conditions;
import com.builder.condition.Order;
import com.dbtypes.DbTypes;
import com.dbtypes.DbTypesFactory;
import com.dbtypes.PostGresType;
import com.exceptions.AnalyzeException;
import com.exceptions.SqlBuilderException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author paulo.gomes
 */
public class SqlBuilder {

    // Informacoes que deveram existir para todas as instancias
    private static String dataBase = "";
    private static String dataBaseType = "";
    private DbTypes db;
    private StringBuilder query;
    private List values;
    private Boolean useTableName;
    private Info info;
    private Map<String, Info> _alias;

    public SqlBuilder(Class classe) throws AnalyzeException {
        this.initClass(classe);
    }

    private void initClass(Class classe) throws AnalyzeException {
        db = DbTypesFactory.getInstance(SqlBuilder.dataBaseType);
        info = FactoryInfo.getInstance(classe);

        this._alias = new HashMap();

        // Atributo definido para possibilitar a criação de alias
        useTableName = db.useTableName();
    }

    /**
     * * Retorna o nome da coluna no banco de dados de acordo com
     *
     * @param attribute nome do atributo da classe
     * @return string com o nome da coluna
     * @throws SqlBuilderException caso nao seja encontrado o atributo na classe
     */
    public String get(String attribute) throws SqlBuilderException {
        return this.getSimpleColumnName(attribute);
    }

    // DML Basicos
    /**
     * Cria estrutura padrao para um select
     *
     * @return Instancia de SqlBuilder, facilitando a chamada de metodos
     * aninhados
     * @throws Exception
     */
    public SqlBuilder select() throws SqlBuilderException {
        return this.select(null);
    }

    /**
     * Cria estrutura padrao para um select nas colunas especificas
     *
     * @param values Lista de string com os nomes dos atributos a serem
     * retornados
     * @return Instancia de SqlBuilder, facilitando a chamada de metodos
     * aninhados
     * @throws Exception
     */
    public SqlBuilder select(String... values) throws SqlBuilderException {
        // Valor setado para facilitar o uso dos alias
        useTableName = true;
        String select = db.getSELECT();
        select = select.replace("#1", values != null ? this.getColumnsNames(values) : this.getColumnsNames());
        select = select.replace("#2", this.addTable(info.getTable()));
        this.query = new StringBuilder(select);
        this.values = new ArrayList();
        return this;
    }

    public SqlBuilder update() throws SqlBuilderException {
        String update = db.getUPDATE();
        update = update.replace("#1", this.addTable(info.getTable()));
        this.query = new StringBuilder(update);
        this.values = new ArrayList();
        return this;
    }

    public void update(Object obj) throws SqlBuilderException, AnalyzeException {
        if (!obj.getClass().equals(info.getClasse())) {
            throw new SqlBuilderException("Objecto deve ser uma instancia da "
                    + "Classe: " + info.getClasse().getName());
        }
        StringBuilder columns = new StringBuilder();
        String update = db.getUPDATE().replace("#1", this.addTable(info.getTable()));
        this.query = new StringBuilder(update);
        this.values = new ArrayList();
        for (String attr : info.getAttributes().keySet()) {
            if (!attr.equals(info.getAttributeId()) || !info.isAutoIncrement()) {
                Object objValue = AnalyzerClass.callMethodGetNativeObjectOrId(obj, attr);
                if (objValue == null && !info.getAttributes().get(attr).nullable()) {
                    throw new SqlBuilderException("Valor do atributo nao pode ser nulo: " + attr);
                }
                this.set(attr, objValue);
            }
        }
        this.add(Conditions.equal(info.getAttributeId(),
                AnalyzerClass.callMethodGetNativeObjectOrId(obj, info.getAttributeId())));
    }

    public void insert() throws SqlBuilderException {
        String columns = this.getColumnsNamesInInsert();
        String insert = db.getINSERT();
        String val = "";
        for (int i = 0; i < info.getAttributes().size(); i++) {
            val += " ?,";
        }
        val = val.substring(0, val.length() - 1);
        insert = insert.replace("#1", this.addTable(info.getTable()));
        insert = insert.replace("#2", columns);
        insert = insert.replace("#3", val);
        this.query = new StringBuilder(insert);
    }

    public void insert(Object obj) throws SqlBuilderException, AnalyzeException {
        // Retorna o valor padrao
        useTableName = db.useTableName();
        if (!obj.getClass().equals(info.getClasse())) {
            throw new SqlBuilderException("Objecto deve ser uma instancia da "
                    + "Classe: " + info.getClasse().getName());
        }
        StringBuilder columns = new StringBuilder();
        String insert = db.getINSERT();
        String val = "";
        this.values = new ArrayList();
        for (String attr : this.info.getAttributes().keySet()) {
            if (!attr.equals(info.getAttributeId()) || !info.isAutoIncrement()) {
                columns.append(this.addAttribute(info.getAttributes().get(attr).name())).append(",");
                val += " " + db.flag() + ",";
                Object objValue = AnalyzerClass.callMethodGetNativeObjectOrId(obj, attr);
                if (objValue == null && !info.getAttributes().get(attr).nullable()) {
                    throw new SqlBuilderException("Valor do atributo nao pode ser nulo: " + attr);
                }
                values.add(objValue);
            }
        }
        columns.deleteCharAt(columns.lastIndexOf(","));
        val = val.substring(0, val.length() - 1);
        insert = insert.replace("#1", this.addTable(info.getTable()));
        insert = insert.replace("#2", columns);
        insert = insert.replace("#3", val);
        this.query = new StringBuilder(insert);
    }

    public SqlBuilder delete() throws SqlBuilderException {
        useTableName = db.useTableName();
        String delete = db.getDELETE();
        delete = delete.replace("#1", this.addTable(info.getTable()));
        this.query = new StringBuilder(delete);
        this.values = new ArrayList();
        return this;
    }

    // Condicoes
    /**
     * Verifica se existe a clausula WHERE, caso exista é apenas adicionado um
     * AND ou entao adicionado um WHERE
     */
    private void addCondition() {
        if (query.indexOf(db.where().trim()) > 1) {
            query.append(db.and());
        } else {
            query.append(db.where());
        }
    }

    /**
     * Verifica se existe a clausula WHERE, caso exista é apenas adicionado um
     * OR ou entao adicionado um WHERE
     */
    private void addConditionOr() {
        if (query.indexOf(db.where().trim()) > 1) {
            query.append(db.or());
        } else {
            query.append(db.where());
        }
    }

    /**
     * Cria um script condicional utilizando o campo, operador e valor para isso
     *
     * @param field campo(atruibuto) a ser utilizado na comparacao
     * @param value valor a ser comparado
     * @param operator operacao a ser efetuada
     * @throws SqlBuilderException
     */
    private void createCondition(String field, Object value, String operator) throws SqlBuilderException {
        if (field.contains(Condition.sep) && operator.contains(Condition.sep)) {
            String[] fields = field.split(Condition.sep);
            String[] operators = operator.split(Condition.sep);
            addCondition();
            for (int i = 0; i < fields.length; i++) {
                String column = getColumnName(fields[i]);
                this.query.append(column).append(operators[i]);
                if (i != fields.length - 1) {
                    this.addConditionOr();
                }
            }
        } else {
            String column = getColumnName(field);
            this.addCondition();
            this.query.append(column).append(operator);
        }
        if (value != null) {
            if (value instanceof List) {
                this.values.addAll((List) value);
            } else {
                this.values.add(value);
            }
        }
    }

    public SqlBuilder add(Condition cond) throws SqlBuilderException {
        createCondition(cond.getField(), cond.getValue(), cond.getOperator(db));
        return this;
    }

    // Update SET
    private void addSet() {
        if (this.query.indexOf(db.set().trim()) > 0) {
            this.query.append(", ");
        } else {
            this.query.append(db.set());
        }
    }

    public SqlBuilder set(String field) throws SqlBuilderException {
        String column = this.getColumnName(field);
        addSet();
        this.query.append(column);
        this.query.append(db.equal());
        this.query.append(db.flag());
        return this;
    }

    public SqlBuilder set(String field, Object value) throws SqlBuilderException {
        boolean useTableAux = useTableName;
        useTableName = false;
        String column = this.getColumnName(field);
        addSet();
        this.query.append(column);
        this.query.append(db.equal());
        this.query.append(db.flag());
        this.values.add(value);
        useTableName = useTableAux;
        return this;
    }

    // Order
    private void addOrder(Order order, String value) {
        if (!this.query.toString().contains(db.orderBy().trim())) {
            this.query.append(db.orderBy());
        } else {
            this.query.append(" , ");
        }
        this.query.append(value);
        switch (order) {
            case DESC:
                this.query.append(db.desc());
                break;
            default:
                this.query.append(db.asc());
        }
    }

    public SqlBuilder setOrder(Order order, String field) throws SqlBuilderException {
        this.addOrder(order, getColumnName(field));
        return this;
    }

    // Order
    public SqlBuilder setOrder(Order order, String... fields) throws SqlBuilderException {
        StringBuilder builder = new StringBuilder();
        for (String string : fields) {
            builder.append(getColumnName(string));
            builder.append(",");
        }
        builder.deleteCharAt(builder.lastIndexOf(","));
       addOrder(order, builder.toString());
        return this;
    }

    // Limit
    public SqlBuilder maxResult(int limit) {
        this.query.append(db.limit()).append(db.flag());
        this.values.add(limit);
        return this;
    }

    //Utilizando tabelas relacionais (ALIAS)
    public void createAlias(String field, String alias) throws SqlBuilderException {
        try {
            if (info.getAttributes().containsKey(field) || _alias.containsKey(getAliasName(field))) {
                if (!_alias.containsKey(alias)) {
                    Class classeAliasAux = info.getAttributes().containsKey(field)
                            ? info.getClasse()
                            : _alias.get(getAliasName(field)).getClasse();
                    Info classAux = FactoryInfo.getInstance(AnalyzerClass.getClassFromField(classeAliasAux, getSimpleAttributeName(field)));
                    classAux.setAlias(alias);
                    _alias.put(alias, classAux);
                }
                formatAlias(_alias.get(alias).getClasse(), field, alias);
            } else {
                throw new SqlBuilderException("Atributo não existe para e classe: " + info.getClasse().getName());
            }
        } catch (AnalyzeException ex) {
            throw new SqlBuilderException(ex.getMessage(), ex);
        }
    }

    protected void addTableForAlias(Class classeAlias) throws SqlBuilderException {
        int index = this.query.indexOf(db.where().trim());
        String tableAux = AnalyzerClass.getClassAnnotation(classeAlias);
        tableAux = ", " + this.addTable(tableAux) + " ";
        if (index > 1) {
            this.query.insert(this.query.indexOf(db.where().trim()), tableAux);
        } else {
            this.query.append(tableAux);
        }
    }

    protected String getAttributeNameFromColumnName(String columnName) {
        String name = columnName.trim().replaceAll("\"", "");
        for (String string : info.getAttributes().keySet()) {
            if (info.getAttributes().get(string).name().equals(name.trim())) {
                return string;
            }
        }
        return null;
    }

    protected String getAliasName(String attribute) {
        return attribute.substring(0, attribute.indexOf("."));
    }

    protected String getSimpleAttributeName(String attribute) {
        if (attribute.contains(".")) {
            return attribute.substring(attribute.indexOf(".") + 1);
        }
        return attribute;
    }

    protected void formatAlias(Class classeAlias, String field, String alias) throws SqlBuilderException {
        if (!useTableName) {
            // Habilita a utilizacao do nome da tabela
            useTableName = true;
        }
        addTableForAlias(classeAlias);
        this.addCondition();
        this.query.append(this.getColumnName(field)).append(db.equal());
        this.query.append(this.getColumnName(alias + "." + _alias.get(alias).getAttributeId()));
    }

    // Outros
    protected String getColumnsNamesInInsert() throws SqlBuilderException {
        StringBuilder attr = new StringBuilder();
        for (String key : info.getAttributes().keySet()) {
            if (!key.equals(info.getAttributeId()) || !info.isAutoIncrement()) {
                attr.append(this.getColumnName(key));
                attr.append(",");
            }
        }
        attr.deleteCharAt(attr.lastIndexOf(","));
        return attr.toString();
    }

    protected String getColumnsNames() throws SqlBuilderException {
        StringBuilder attr = new StringBuilder();
        for (String key : info.getAttributes().keySet()) {
            attr.append(this.getColumnName(key));
            attr.append(",");
        }
        attr.deleteCharAt(attr.lastIndexOf(","));
        return attr.toString();
    }

    protected String getColumnsNames(String... columns) throws SqlBuilderException {
        StringBuilder aux = new StringBuilder();
        for (String attr : columns) {
            aux.append(this.getColumnName(attr));
            aux.append(",");
        }
        aux.deleteCharAt(aux.lastIndexOf(","));
        return aux.toString();
    }

    /**
     * Retorna o nome da coluna fomatado de acordo com as configuracoes de
     * DBTypes
     *
     * @param attribute nome do atributo da classe
     * @return nome da coluna no banco que representa o atributo, ja formatado
     * @throws SqlBuilderException
     */
    private String getColumnName(String attribute) throws SqlBuilderException {
        if (this.info.getAttributes().containsKey(attribute)) {
            return this.addAttribute(this.info.getAttributes().get(attribute).name());
        } else if (attribute.contains(".")) {
            String[] aux = attribute.split("\\.");
            return this.addAttribute(
                    this._alias.get(aux[0]).getAttributes().get(aux[1]).name(),
                    this._alias.get(aux[0]).getTable());
        } else {
            throw new SqlBuilderException("Atributo Nao Existe para a Classe: " + info.getClasse().getName());
        }
    }

    /**
     * Retorna o nome da coluna do banco
     *
     * @param attribute nome do atributo da classe
     * @return nome da coluna no banco que representa o atributo, sem formatacao
     * @throws SqlBuilderException
     */
    private String getSimpleColumnName(String attribute) throws SqlBuilderException {
        if (this.info.getAttributes().containsKey(attribute)) {
            return this.info.getAttributes().get(attribute).name();
        } else {
            throw new SqlBuilderException("Atributo Nao Existe para a Classe: " + info.getClasse().getName());
        }
    }

    public StringBuilder getQuery() {
        return query;
    }

    public List getValues() {
        return values;
    }

    /**
     * Nome do banco(schema) de dados a ser utilizado
     *
     * @param dataBaseName
     */
    public static void setDataBaseName(String dataBaseName) {
        dataBase = dataBaseName;
    }

    /**
     * Tipo do banco de dados a ser utilizado (Configurado apenas para MySql e
     * PostGres)
     *
     * @param dataBaseType String com o tipo do banco de dados.
     */
    public static void setDataBaseType(String dataBaseType) {
        SqlBuilder.dataBaseType = dataBaseType.toLowerCase();
    }

    public void setQuery(StringBuilder query) {
        this.query = query;
    }

    public void clearQuery() {
        this.query = new StringBuilder();
    }

    public PreparedStatement getPreparedStatementWithGeneratedKey(Connection conn) throws SQLException, SqlBuilderException {
        if (!this.query.toString().contains("INSERT")) {
            throw new SqlBuilderException("Apenas comando Insert é permitido para esse metodo.");
        }
        if (countCaracter(db.flag()) != values.size()) {
            throw new SqlBuilderException("Quantidade de Paramentros nao "
                    + "corresponde ao esperado");
        }
        PreparedStatement statement = conn.prepareStatement(this.query.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
        int count = 1;
        for (Object object : values) {
            if (object instanceof Date) {
                statement.setObject(count, formatDate(object));
            } else {
                statement.setObject(count, object);
            }
            count++;
        }
        return statement;
    }

    public PreparedStatement getPreparedStatement(Connection conn) throws SQLException, SqlBuilderException {
        if (countCaracter(db.flag()) != values.size()) {
            throw new SqlBuilderException("Quantidade de Paramentros nao "
                    + "corresponde ao esperado");
        }
        PreparedStatement statement = conn.prepareStatement(this.query.toString());
        int count = 1;
        for (Object object : values) {
            if (object instanceof Date) {
                statement.setObject(count, formatDate(object));
            } else {
                statement.setObject(count, object);
            }
            count++;
        }
        return statement;
    }

    private int countCaracter(String caracter) {
        int count = 0;
        if (this.query.indexOf(caracter) > 0) {
            String aux = this.query.toString();
            char _char = caracter.charAt(0);
            for (int i = aux.indexOf(caracter); i < aux.length(); i++) {
                if (aux.charAt(i) == _char) {
                    count++;
                }
            }
        }
        return count;
    }

    private String addAttribute(String attribute, String tableClass) {
        StringBuilder attr = new StringBuilder();
        if (useTableName && db.useQuote()) {
            attr.append(addQuotes(tableClass));
            attr.append(".");
            attr.append(addQuotes(attribute));
        } else if (useTableName) {
            attr.append(tableClass).append(".").append(attribute);
        } else if (db.useQuote()) {
            attr.append(addQuotes(attribute));
        } else {
            attr.append(attribute);
        }
        return attr.toString();
    }

    private String addAttribute(String attribute) {
        return addAttribute(attribute, info.getTable());
    }

    private String addTable(String table) throws SqlBuilderException {
        if (dataBase.isEmpty()) {
            throw new SqlBuilderException("Nome do banco de dados é nulo, use setDataBaseName");
        }
        StringBuilder attr = new StringBuilder();
        if (db.useDataBaseName() && db.useQuote()) {
            attr.append(addQuotes(dataBase));
            attr.append(".");
            attr.append(addQuotes(table));
        } else if (db.useDataBaseName()) {
            attr.append(dataBase).append(".").append(table);
        } else if (db.useQuote()) {
            attr.append(addQuotes(table));
        } else {
            attr.append(table);
        }
        return attr.toString();
    }

    private String addQuotes(String param) {
        String quote = "\"";
        if (param.contains(quote)) {
            return param;
        } else {
            return quote + param + quote;
        }
    }

    private Object formatDate(Object obj) {
        if (db instanceof PostGresType) {
            return new Timestamp(((Date) obj).getTime());
        } else {
            return obj;
        }
    }
}
