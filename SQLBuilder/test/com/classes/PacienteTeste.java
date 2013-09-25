/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes;

import com.annotations.Column;
import com.annotations.GeneratedValue;
import com.annotations.GenerationType;
import com.annotations.Id;
import com.annotations.Table;
import java.io.Serializable;
import java.util.Date;
import javax.swing.text.html.parser.Entity;

/**
 *
 * @author igor.santos
 */
@Table(name = "PACIENTE")
public class PacienteTeste extends PojoBaseTeste implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_PACIENTE", nullable = false)
    private Integer id;
    @Column(name = "NOME", nullable = false)
    private String nome;
    @Column(name = "DATANASCIMENTO", nullable = false)
    private Date datanascimento;
    @Column(name = "SEXO", nullable = false)
    private char sexo;
    

    public PacienteTeste() {
    }

    public PacienteTeste(Integer id) {
        this.id = id;
    }

    public PacienteTeste(Integer id, String nome, Date datanascimento, char sexo) {
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