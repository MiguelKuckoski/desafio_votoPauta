package com.zallpy.Desafio_unicred.service;

import com.zallpy.Desafio_unicred.holder.PautaHolder;
import com.zallpy.Desafio_unicred.holder.VotoHolder;
import com.zallpy.Desafio_unicred.model.Pauta;

import java.time.LocalDateTime;
import java.util.List;

public interface PautaService {

    void criarPauta(PautaHolder pautaHolder);

    LocalDateTime inicarVotacao(String codPauta, Integer tempoSessao);

    void votarPauta(VotoHolder votoHolder);

    PautaHolder getPautaByCodigo(String codPauta);

    List<Pauta> getPautasPendentesConclusao();

    void salvarPauta(Pauta pauta);
}
