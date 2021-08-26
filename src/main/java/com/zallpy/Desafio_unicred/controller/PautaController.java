package com.zallpy.Desafio_unicred.controller;

import com.zallpy.Desafio_unicred.holder.PautaHolder;
import com.zallpy.Desafio_unicred.holder.VotoHolder;
import com.zallpy.Desafio_unicred.service.PautaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/pauta")
public class PautaController {

    private final static Logger logger = LoggerFactory.getLogger(PautaController.class);

    @Autowired
    private PautaService pautaService;

    @RequestMapping(value = {"/"}, method = RequestMethod.POST)
    public ResponseEntity criarPauta(@Valid @RequestBody PautaHolder pautaHolder) {
        pautaService.criarPauta(pautaHolder);
        return ResponseEntity.accepted().build();
    }

    @RequestMapping(value = {"/iniciarVotacao/"}, method = RequestMethod.GET)
    public ResponseEntity inicarVotacao(
            @RequestParam(value = "codPauta", required = true) final String codPauta,
            @RequestParam(value = "tempoSessao", defaultValue = "1", required = false) final Integer tempoSessao) {
        pautaService.inicarVotacao(codPauta, tempoSessao);
        return ResponseEntity.accepted().build();
    }

    @RequestMapping(value = {"/votarPauta"}, method = RequestMethod.POST)
    public ResponseEntity votarPauta(@Valid @RequestBody VotoHolder votoHolder){
        pautaService.votarPauta(votoHolder);
        return ResponseEntity.accepted().build();
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public ResponseEntity consultarPauta(@RequestParam(value = "codPauta", required = true) final String codPauta) {
        PautaHolder pauta = pautaService.getPautaByCodigo(codPauta);
        return ResponseEntity.ok(pauta);
    }
}
