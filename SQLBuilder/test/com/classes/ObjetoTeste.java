/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes;

import com.annotations.Column;


/**
 *
 * @author paulo.gomes
 */
public abstract class ObjetoTeste {
    
    @Column(name = "NOME")
    protected String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
