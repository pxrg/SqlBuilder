/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author paulo.gomes
 */
@Entity
@Table(name = "USUARIO")
public class Usuario {
    
    @Id
    @Column(name = "ID_USUARIO")
    private int id;
    @Column(name = "NOME")
    private String nome;
    @Column(name = "DATA_CADASTRO")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataCadastro;

    public Usuario() {
    }

    public Usuario(int id, String nome, Date dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.dataCadastro = dataCadastro;
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

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
    
}
