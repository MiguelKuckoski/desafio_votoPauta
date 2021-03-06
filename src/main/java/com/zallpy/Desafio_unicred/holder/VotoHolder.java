package com.zallpy.Desafio_unicred.holder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class VotoHolder implements Serializable {

    @NotNull(message = "codAssociado deve ser preenchido")
    private Integer codAssociado;

    @NotBlank(message = "codPauta deve ser preenchido")
    private String codPauta;

    @NotNull(message = "voto deve ser preenchido")
    private Boolean voto;

    public Integer getCodAssociado() {
        return codAssociado;
    }

    public void setCodAssociado(Integer codAssociado) {
        this.codAssociado = codAssociado;
    }

    public String getCodPauta() {
        return codPauta;
    }

    public void setCodPauta(String codPauta) {
        this.codPauta = codPauta;
    }

    public Boolean getVoto() {
        return voto;
    }

    public void setVoto(Boolean voto) {
        this.voto = voto;
    }
}
