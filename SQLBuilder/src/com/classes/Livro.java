/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author paulo.gomes
 */
@Entity
@Table(name = "LIVRO")
public class Livro extends Objeto{
    @Id
    @Column(name = "id_livro")
    private Long id;

    public Livro() {
    }
    
    public Livro(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
