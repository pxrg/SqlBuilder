/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes;

import com.annotations.Column;
import com.annotations.Id;
import com.annotations.Table;

/**
 *
 * @author paulo.gomes
 */
@Table(name = "LIVRO")
public class LivroTeste extends ObjetoTeste{
    @Id
    @Column(name = "id_livro")
    private Long id;

    public LivroTeste() {
    }
    
    public LivroTeste(Long id, String nome) {
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
