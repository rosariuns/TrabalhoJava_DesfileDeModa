package com.unipar.desfile.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_modelo")
public class Modelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Double cache;

    public Modelo() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Double getCache() { return cache; }
    public void setCache(Double cache) { this.cache = cache; }
}