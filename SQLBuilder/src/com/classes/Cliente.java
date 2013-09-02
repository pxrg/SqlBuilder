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
public class Cliente extends Pessoa {

    @Column(name = "TELEFONE")
    private String telefone;

    public Cliente(int id, String nome, String cpf, String telefone) {
        super(id, nome, cpf);
        this.telefone = telefone;
    }
}
