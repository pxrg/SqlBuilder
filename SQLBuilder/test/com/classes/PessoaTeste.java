/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes;

import com.annotations.Column;
import com.annotations.Id;
import com.annotations.Table;
import java.util.List;
import javax.swing.text.html.parser.Entity;

/**
 *
 * @author paulo.gomes
 */
@Table(name = "PESSOA")
public class PessoaTeste {

    @Id
    @Column(name = "ID_PESSOA")
    private int id;
    @Column(name = "NOME")
    private String nome;
    @Column(name = "CPF")
    private String cpf;
    
    private List<CarroTeste> carros;

    public PessoaTeste() {
    }

    public PessoaTeste(int id, String nome, String cpf) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
