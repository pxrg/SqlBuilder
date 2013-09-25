/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes;

import com.annotations.Column;
import com.annotations.Id;
import com.annotations.Table;
import java.util.Date;
import javax.swing.text.html.parser.Entity;

/**
 *
 * @author paulo.gomes
 */
@Table(name = "USUARIO")
public class UsuarioTeste {
    
    @Id
    @Column(name = "ID_USUARIO")
    private int id;
    @Column(name = "NOME")
    private String nome;
    @Column(name = "DATA_CADASTRO")
    private Date dataCadastro;

    public UsuarioTeste() {
    }

    public UsuarioTeste(int id, String nome, Date dataCadastro) {
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
