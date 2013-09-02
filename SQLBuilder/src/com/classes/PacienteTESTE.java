/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author igor.santos
 */
@Entity
@Table(name = "PACIENTE")
public class PacienteTESTE extends PojoBaseTESTE implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_PACIENTE", nullable = false)
    private Integer id;
    @Column(name = "NOME", nullable = false)
    private String nome;
    @Column(name = "DATANASCIMENTO", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date datanascimento;
    @Column(name = "SEXO", nullable = false)
    private char sexo;
    

    public PacienteTESTE() {
    }

    public PacienteTESTE(Integer id) {
        this.id = id;
    }

    public PacienteTESTE(Integer id, String nome, Date datanascimento, char sexo) {
        this.id = id;
        this.nome = nome;
        this.datanascimento = datanascimento;
        this.sexo = sexo;
    }
    
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDatanascimento() {
        return datanascimento;
    }

    public void setDatanascimento(Date datanascimento) {
        this.datanascimento = datanascimento;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }
}