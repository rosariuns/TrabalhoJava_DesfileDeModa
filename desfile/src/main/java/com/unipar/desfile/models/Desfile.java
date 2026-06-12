package com.unipar.desfile.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tb_desfile")
public class Desfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private LocalDate data;
    private Double orcamentoTotal;

    // Construtor vazio exigido pelo Spring
    public Desfile() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public Double getOrcamentoTotal() { return orcamentoTotal; }
    public void setOrcamentoTotal(Double orcamentoTotal) { this.orcamentoTotal = orcamentoTotal; }
}