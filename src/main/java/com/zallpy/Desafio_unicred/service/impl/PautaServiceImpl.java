package com.zallpy.Desafio_unicred.service.impl;

import com.zallpy.Desafio_unicred.dao.PautaDao;
import com.zallpy.Desafio_unicred.holder.Enum.EnumStatusPauta;
import com.zallpy.Desafio_unicred.holder.PautaHolder;
import com.zallpy.Desafio_unicred.holder.VotoHolder;
import com.zallpy.Desafio_unicred.model.Pauta;
import com.zallpy.Desafio_unicred.model.Voto;
import com.zallpy.Desafio_unicred.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PautaServiceImpl implements PautaService {

    @Autowired
    private PautaDao pautaDao;

    @Override
    public void criarPauta(final PautaHolder pautaHolder) {
        final Pauta pauta = pautaDao.getPautaByCodigo(pautaHolder.getCodigo());
        if(pauta != null) {
            //TODO return exception, pauta já existente
        }else{
            final Pauta novaPauta = getPautaByHolder(pautaHolder);
            pautaDao.salvarPauta(novaPauta);
        }
    }

    @Override
    public void inicarVotacao(String codPauta, Integer tempoSessao) {
        final Pauta pauta = pautaDao.getPautaByCodigo(codPauta);
        if(pauta != null ) {
            if(EnumStatusPauta.AGUARDANDO.equals(pauta.getEnumStatusPauta())) {
                LocalDateTime inicioVotacao = LocalDateTime.now();
                LocalDateTime fimVotacao = inicioVotacao.plusMinutes(tempoSessao);

                pauta.setDtInicioVotacao(inicioVotacao);
                pauta.setDtFimVotacao(fimVotacao);
                pautaDao.salvarPauta(pauta);

            }else if(EnumStatusPauta.EM_VOTACAO.equals(pauta.getEnumStatusPauta())) {
                //TODO votação em andamento
            }else {
                //TODO pauta finalizada
            }
        }else{
            //TODO return exception, pauta inexistente
        }
    }

    @Override
    public void votarPauta(VotoHolder votoHolder) {
        final Pauta pauta = pautaDao.getPautaByCodigo(votoHolder.getCodPauta());
        if(pauta != null) {
            if(EnumStatusPauta.EM_VOTACAO.equals(pauta.getEnumStatusPauta()) && pauta.getDtFimVotacao().isAfter(LocalDateTime.now())) {
                final Voto voto = pautaDao.getVotoByPautaEAssociado(votoHolder.getCodPauta(), votoHolder.getCodAssociado());
                if(voto != null) {
                    //TODO usuario já votou, retorna erro
                }else{
                    final Voto novoVoto = getVotoByHolder(votoHolder, pauta);
                    pautaDao.salvarVoto(novoVoto);
                }
            }else{
                //TODO Pauta não está em sessão
            }
        }else{
            //TODO Pauta inexistente
        }

    }

    @Override
    public PautaHolder getPautaByCodigo(String codPauta) {
        final Pauta pauta = pautaDao.getPautaByCodigo(codPauta);
        if(pauta != null) {
            PautaHolder pautaHolder = pauta.getPautaHolder();
            return pautaHolder;
        }else{
            //TODO pauta inexistente
            return null;
        }
    }

    private Voto getVotoByHolder(VotoHolder votoHolder, Pauta pauta) {
        Voto voto = new Voto();
        voto.setVoto(votoHolder.getVoto());
        voto.setPauta(pauta);
        voto.setCodAssociado(votoHolder.getCodAssociado());

        return voto;
    }

    private Pauta getPautaByHolder(PautaHolder pautaHolder) {
        final Pauta pauta = new Pauta();
        pauta.setCodigo(pautaHolder.getCodigo());
        pauta.setDescricao(pautaHolder.getDescricao());
        pauta.setNome(pautaHolder.getNome());
        pauta.setEnumStatusPauta(pautaHolder.getStatusPauta());
        return pauta;
    }
}
