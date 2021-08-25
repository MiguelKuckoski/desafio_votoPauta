package com.zallpy.Desafio_unicred.holder.Enum;

public enum EnumStatusPauta {

    AGUARDANDO("Aguardando"),
    EM_VOTACAO("Em votação"),
    AUTORIZADA("Autorizada"),
    RECUSADA("Recusada");

    private String status;

    private EnumStatusPauta(String status){
        this.status = status;
    }

}
