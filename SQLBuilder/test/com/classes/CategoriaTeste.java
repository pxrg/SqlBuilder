/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes;

import com.annotations.Column;
import com.annotations.Id;
import com.annotations.Table;
import java.io.Serializable;

/**
 *
 * @author igor.santos
 */
@Table(name = "CATEGORIA")
public class CategoriaTeste implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID_CATEGORIA", nullable = false)
    private Integer id;
    @Column(name = "NOME")
    private String nome;

    public CategoriaTeste() {
    }

    public CategoriaTeste(Integer id) {
        this.id = id;
    }

    public CategoriaTeste(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
