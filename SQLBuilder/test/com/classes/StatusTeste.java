/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes;

import com.annotations.Column;
import com.annotations.Id;
import com.annotations.JoinColumn;
import com.annotations.ManyToOne;
import com.annotations.Table;
import java.io.Serializable;
import javax.swing.text.html.parser.Entity;

/**
 *
 * @author igor.santos
 */
@Table(name = "STATUS")
public class StatusTeste implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID_STATUS", nullable = false)
    private Integer id;
    @Column(name = "NOME", nullable = false)
    private String nome;
    @JoinColumn(name = "ID_CATEGORIA", referencedColumnName = "ID_CATEGORIA")
    @ManyToOne(optional = false)
    private CategoriaTeste categoria;

    public StatusTeste() {
    }

    public StatusTeste(Integer id) {
        this.id = id;
    }

    public StatusTeste(Integer id, String nome) {
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
    
    public CategoriaTeste getCategoria() {
        if (categoria == null) {
            categoria = new CategoriaTeste();
        }
        return categoria;
    }

    public void setCategoria(CategoriaTeste categoria) {
        this.categoria = categoria;
    }
}