package com.zallpy.Desafio_unicred.service;

import com.zallpy.Desafio_unicred.holder.PautaHolder;
import com.zallpy.Desafio_unicred.holder.VotoHolder;

public interface PautaService {

    void criarPauta(PautaHolder pautaHolder);

    void inicarVotacao(String codPauta, Integer tempoSessao);

    void votarPauta(VotoHolder votoHolder);

    PautaHolder getPautaByCodigo(String codPauta);
}
