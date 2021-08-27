package com.zallpy.Desafio_unicred.service;

import com.zallpy.Desafio_unicred.holder.PautaHolder;
import com.zallpy.Desafio_unicred.holder.VotoHolder;

import java.time.LocalDateTime;

public interface PautaService {

    void criarPauta(PautaHolder pautaHolder);

    LocalDateTime inicarVotacao(String codPauta, Integer tempoSessao);

    void votarPauta(VotoHolder votoHolder);

    PautaHolder getPautaByCodigo(String codPauta);
}
