/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes;

import javax.persistence.Column;

/**
 *
 * @author paulo.gomes
 */
public abstract class Objeto {
    
    @Column(name = "NOME")
    protected String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
