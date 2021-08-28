package com.zallpy.Desafio_unicred.model;

import com.zallpy.Desafio_unicred.holder.Enum.EnumStatusPauta;
import com.zallpy.Desafio_unicred.holder.PautaHolder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "Pauta")
@Table(name = "TB_PAUTA")
public class Pauta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    @Column(unique=true)
    private String codigo;

    private String descricao;

    @Column(name = "status_pauta")
    private EnumStatusPauta enumStatusPauta;

    private LocalDateTime dtInicioVotacao;

    private LocalDateTime dtFimVotacao;

    @OneToMany(mappedBy = "pauta", cascade = CascadeType.ALL)
    private List<Voto> votos;

    @Transient
    private PautaHolder pautaHolder;

    public Pauta(Integer id, String nome, String codigo, EnumStatusPauta enumStatusPauta, LocalDateTime dtInicioVotacao, LocalDateTime dtFimVotacao) {
        this.id = id;
        this.nome = nome;
        this.codigo = codigo;
        this.enumStatusPauta = enumStatusPauta;
        this.dtInicioVotacao = dtInicioVotacao;
        this.dtFimVotacao = dtFimVotacao;
    }

    public Pauta() {

    }

    public LocalDateTime getDtInicioVotacao() {
        return dtInicioVotacao;
    }

    public void setDtInicioVotacao(LocalDateTime dtInicioVotacao) {
        this.dtInicioVotacao = dtInicioVotacao;
    }

    public LocalDateTime getDtFimVotacao() {
        return dtFimVotacao;
    }

    public void setDtFimVotacao(LocalDateTime dtFimVotacao) {
        this.dtFimVotacao = dtFimVotacao;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public EnumStatusPauta getEnumStatusPauta() {
        return enumStatusPauta;
    }

    public void setEnumStatusPauta(EnumStatusPauta enumStatusPauta) {
        this.enumStatusPauta = enumStatusPauta;
    }

    public PautaHolder getPautaHolder() {
        if(pautaHolder == null) {
            createHolder();
        }
        return pautaHolder;
    }

    private void createHolder() {
        PautaHolder pautaHolder = new PautaHolder();
        pautaHolder.setCodigo(this.codigo);
        pautaHolder.setDescricao(this.descricao);
        pautaHolder.setNome(this.nome);
        pautaHolder.setStatusPauta(this.enumStatusPauta);

        this.pautaHolder = pautaHolder;
    }

    public List<Voto> getVotos() {
        return votos;
    }

    public void setVotos(List<Voto> votos) {
        this.votos = votos;
    }
}
