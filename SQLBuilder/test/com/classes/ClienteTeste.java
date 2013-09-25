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
public class ClienteTeste extends PessoaTeste {

    @Column(name = "TELEFONE")
    private String telefone;

    public ClienteTeste(int id, String nome, String cpf, String telefone) {
        super(id, nome, cpf);
        this.telefone = telefone;
    }
}
