package com.zallpy.Desafio_unicred.timerTask;

import com.zallpy.Desafio_unicred.holder.Enum.EnumStatusPauta;
import com.zallpy.Desafio_unicred.model.Pauta;
import com.zallpy.Desafio_unicred.queue.RabbitMQSender;
import com.zallpy.Desafio_unicred.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component @EnableScheduling
public class ConcluirPautas {

    private static final long SEGUNDO = 1000;
    private static final long MINUTO = SEGUNDO * 60;

    @Autowired
    private PautaService pautaService;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Scheduled(fixedDelay = MINUTO)
    @Transactional
    public void finalizarPauta() {
        List<Pauta> pautas = pautaService.getPautasPendentesConclusao();
        pautas.parallelStream().forEach(pauta -> {
            Long votosPositivos = pauta.getVotos().stream().filter(voto -> voto.getVoto() == Boolean.TRUE).count();
            Long votosNegativos = pauta.getVotos().stream().filter(voto -> voto.getVoto() == Boolean.FALSE).count();

            EnumStatusPauta status = votosPositivos > votosNegativos ? EnumStatusPauta.AUTORIZADA : EnumStatusPauta.RECUSADA;
            pauta.setEnumStatusPauta(status);
            pautaService.salvarPauta(pauta);

            rabbitMQSender.send(pauta.getPautaHolder());
        });
    }

}