package com.zallpy.Desafio_unicred.dao.impl;

import com.zallpy.Desafio_unicred.dao.PautaDao;
import com.zallpy.Desafio_unicred.model.Pauta;
import com.zallpy.Desafio_unicred.model.Voto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
public class PautaDaoImpl implements PautaDao {

    @Autowired
    EntityManager entityManager;


    @Override
    public Pauta getPautaByCodigo(String codPauta) {
        Pauta pauta = entityManager
                .createQuery("SELECT p FROM Pauta p where p.codigo = :codPauta", Pauta.class)
                .setParameter("codPauta", codPauta)
                .getSingleResult();
        return pauta;
    }

    @Override
    public void salvarPauta(Pauta pauta) {
        if(pauta.getId() == null) {
            entityManager.persist(pauta);
        }else{
            entityManager.merge(pauta);
        }
    }

    @Override
    public Voto getVotoByPautaEAssociado(String codPauta, Integer codAssociado) {
        Voto voto = entityManager
                .createQuery("SELECT v FROM Voto v where v.pauta.codigo = :codPauta AND v.codAssociado= :codAssociado", Voto.class)
                .setParameter("codPauta", codPauta)
                .setParameter("codAssociado", codAssociado)
                .getSingleResult();
        return voto;
    }

    @Override
    public void salvarVoto(Voto novoVoto) {
        entityManager.persist(novoVoto);
    }
}
