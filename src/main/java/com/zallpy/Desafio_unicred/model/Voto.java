package com.zallpy.Desafio_unicred.model;

import javax.persistence.*;

@Entity(name = "Voto")
@Table(name = "TB_VOTO")
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pauta_id")
    private Pauta pauta;

    private Integer codAssociado;

    private Boolean voto;

    public Voto(Integer id, Integer codAssociado, Boolean voto) {
        this.id = id;
        this.codAssociado = codAssociado;
        this.voto = voto;
    }

    public Voto() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pauta getPauta() {
        return pauta;
    }

    public void setPauta(Pauta pauta) {
        this.pauta = pauta;
    }

    public Integer getCodAssociado() {
        return codAssociado;
    }

    public void setCodAssociado(Integer codAssociado) {
        this.codAssociado = codAssociado;
    }

    public Boolean getVoto() {
        return voto;
    }

    public void setVoto(Boolean voto) {
        this.voto = voto;
    }
}
