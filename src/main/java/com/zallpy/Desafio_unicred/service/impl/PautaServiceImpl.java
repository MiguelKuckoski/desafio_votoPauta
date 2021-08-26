package com.zallpy.Desafio_unicred.service.impl;

import com.zallpy.Desafio_unicred.dao.PautaDao;
import com.zallpy.Desafio_unicred.exception.holder.CustomException;
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
            throw new CustomException("Pauta já existente, code: " + pautaHolder.getCodigo());
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
                throw new CustomException("Pauta já possui votação em andamento, code: " + codPauta);
            }else {
                throw new CustomException("Votação já finalizada, pauta código: " + codPauta + ", status: " + pauta.getEnumStatusPauta().getStatus());
            }
        }else{
            throw new CustomException("Pauta inexistente, code: " + codPauta);
        }
    }

    @Override
    public void votarPauta(VotoHolder votoHolder) {
        final Pauta pauta = pautaDao.getPautaByCodigo(votoHolder.getCodPauta());
        if(pauta != null) {
            if(EnumStatusPauta.EM_VOTACAO.equals(pauta.getEnumStatusPauta()) && pauta.getDtFimVotacao().isAfter(LocalDateTime.now())) {
                final Voto voto = pautaDao.getVotoByPautaEAssociado(votoHolder.getCodPauta(), votoHolder.getCodAssociado());
                if(voto != null) {
                    throw new CustomException("Voto do usuário já computado, pauta: " + votoHolder.getCodPauta());
                }else{
                    final Voto novoVoto = getVotoByHolder(votoHolder, pauta);
                    pautaDao.salvarVoto(novoVoto);
                }
            }else{
                throw new CustomException("Pauta não está em sessão aberta: " + votoHolder.getCodPauta());
            }
        }else{
            throw new CustomException("Pauta inexistente, code: " + votoHolder.getCodPauta());
        }

    }

    @Override
    public PautaHolder getPautaByCodigo(String codPauta) {
        final Pauta pauta = pautaDao.getPautaByCodigo(codPauta);
        if(pauta != null) {
            PautaHolder pautaHolder = pauta.getPautaHolder();
            return pautaHolder;
        }else{
            throw new CustomException("Pauta inexistente, code: " + codPauta);
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
        pauta.setEnumStatusPauta(EnumStatusPauta.AGUARDANDO);
        return pauta;
    }
}
