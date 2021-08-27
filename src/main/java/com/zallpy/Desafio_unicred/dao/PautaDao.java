package com.zallpy.Desafio_unicred.dao;

import com.zallpy.Desafio_unicred.model.Pauta;
import com.zallpy.Desafio_unicred.model.Voto;

import java.util.List;

public interface PautaDao {
    Pauta getPautaByCodigo(String codigo);

    void salvarPauta(Pauta novaPauta);

    Voto getVotoByPautaEAssociado(String codPauta, Integer codAssociado);

    void salvarVoto(Voto novoVoto);

    List<Pauta> getPautasPendentesConclusao();
}
