package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pessoaspoo3") // Especifique o nome da tabela, se necessário
public class Model {

    @Id
    private int cpf;
    private String nome;

    // Construtores
    public Model() {
    }

    public Model(String nome, int cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    // Getters e Setters
    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
