/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author paulo.gomes
 */
@Entity
@Table(name = "CARRO")
public class OutroCarro {

    @Id
    @Column(name = "ID_CARRO")
    private int id;
    @Column(name = "MARCA", length = 100, nullable = false)
    private String marca;
    @Column(name = "MODELO", length = 150)
    private String modelo;
    @JoinColumn(name = "ID_PESSOA")
    @ManyToOne
    private Pessoa pessoa;

    public OutroCarro() {
    }

    public OutroCarro(int id, String marca, String modelo) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}
