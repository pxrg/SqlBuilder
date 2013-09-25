/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes;

import com.annotations.Column;
import com.annotations.GeneratedValue;
import com.annotations.GenerationType;
import com.annotations.Id;
import com.annotations.JoinColumn;
import com.annotations.ManyToOne;
import com.annotations.Table;
import javax.swing.text.html.parser.Entity;


/**
 *
 * @author paulo.gomes
 */
@Table(name = "CARRO")
public class CarroTeste {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_CARRO")
    private int id;
    @Column(name = "MARCA", length = 100, nullable = false)
    private String marca;
    @Column(name = "MODELO", length = 150)
    private String modelo;
    @JoinColumn(name = "ID_PESSOA")
    @ManyToOne
    private PessoaTeste pessoa;

    public CarroTeste() {
    }

    public CarroTeste(int id, String marca, String modelo) {
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

    public PessoaTeste getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaTeste pessoa) {
        this.pessoa = pessoa;
    }
}
