package com.unipar.desfile.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_contrato")
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento: Muitos contratos podem pertencer a um mesmo desfile
    @ManyToOne
    @JoinColumn(name = "desfile_id")
    private Desfile desfile;

    // Relacionamento: Muitos contratos podem pertencer a uma mesma modelo
    @ManyToOne
    @JoinColumn(name = "modelo_id")
    private Modelo modelo;

    private String lookAtribuido;

    public Contrato() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Desfile getDesfile() { return desfile; }
    public void setDesfile(Desfile desfile) { this.desfile = desfile; }

    public Modelo getModelo() { return modelo; }
    public void setModelo(Modelo modelo) { this.modelo = modelo; }

    public String getLookAtribuido() { return lookAtribuido; }
    public void setLookAtribuido(String lookAtribuido) { this.lookAtribuido = lookAtribuido; }
}