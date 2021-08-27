package com.zallpy.Desafio_unicred.controller;

import com.zallpy.Desafio_unicred.holder.PautaHolder;
import com.zallpy.Desafio_unicred.holder.VotoHolder;
import com.zallpy.Desafio_unicred.service.PautaService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/pauta")
public class PautaController {

    private final static Logger logger = LoggerFactory.getLogger(PautaController.class);

    @Autowired
    private PautaService pautaService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pauta cadastrada com sucesso"),
    })
    @RequestMapping(value = {""}, method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity criarPauta(@Valid @RequestBody PautaHolder pautaHolder) {
        pautaService.criarPauta(pautaHolder);
        return ResponseEntity.ok("Pauta cadastrada com sucesso");
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Votação aberta até: <data>"),
    })
    @RequestMapping(value = {"/iniciarVotacao"}, method = RequestMethod.GET)
    public ResponseEntity inicarVotacao(
            @RequestParam(value = "codPauta", required = true) final String codPauta,
            @RequestParam(value = "tempoSessao", defaultValue = "1", required = false) final Integer tempoSessao) {
        LocalDateTime dtFim = pautaService.inicarVotacao(codPauta, tempoSessao);
        return ResponseEntity.ok("Votação aberta até: " + dtFim);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Voto computado"),
    })
    @RequestMapping(value = {"/votarPauta"}, method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity votarPauta(@Valid @RequestBody VotoHolder votoHolder){
        pautaService.votarPauta(votoHolder);
        return ResponseEntity.ok("Voto computado");
    }

    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public ResponseEntity consultarPauta(@RequestParam(value = "codPauta", required = true) final String codPauta) {
        PautaHolder pauta = pautaService.getPautaByCodigo(codPauta);
        return ResponseEntity.ok(pauta);
    }
}
