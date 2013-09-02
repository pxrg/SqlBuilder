/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builder.sql;

import com.analyzer.AnalyzerClass;
import com.exceptions.AnalyzeException;

/**
 *
 * @author prg
 */
public class FactoryInfo {
    
    private FactoryInfo(){}
    
    public static Info getInstance(Class classe) throws AnalyzeException{
        String aliasTag = "_al_";
        ClassInfo info = new ClassInfo(classe);
        AnalyzerClass.Analyzer(classe);
        info.setTable(AnalyzerClass.getClassAnnotation(classe));
        info.setAttributeId(AnalyzerClass.getNameIdAttributeForClass(classe));
        info.setAttributes(AnalyzerClass.getMapAttributesWithAnnotation(classe));
        info.setIsAutoIncrement(AnalyzerClass.isAutoIncrementIdAttribute(classe));
        info.setAlias(aliasTag+AnalyzerClass.getSimpleClassName(classe).toLowerCase());
        return info;
    }
    
}
