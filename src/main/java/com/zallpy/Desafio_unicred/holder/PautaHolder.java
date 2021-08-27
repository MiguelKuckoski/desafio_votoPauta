package com.zallpy.Desafio_unicred.holder;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zallpy.Desafio_unicred.holder.Enum.EnumStatusPauta;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = PautaHolder.class)
public class PautaHolder implements Serializable {

    @NotBlank(message = "Nome deve ser preenchido")
    private String nome;

    @NotBlank(message = "Código deve ser preenchido")
    private String codigo;

    private String descricao;

    private EnumStatusPauta statusPauta = EnumStatusPauta.AGUARDANDO;

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

    public EnumStatusPauta getStatusPauta() {
        return statusPauta;
    }

    public void setStatusPauta(EnumStatusPauta statusPauta) {
        this.statusPauta = statusPauta;
    }
}
