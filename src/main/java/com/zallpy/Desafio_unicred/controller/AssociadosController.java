package com.zallpy.Desafio_unicred.controller;

import com.zallpy.Desafio_unicred.service.AssociadosService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/associados")
public class AssociadosController {

    @Autowired
    private AssociadosService associadosService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Informa se o usuário está apto 'ABLE_TO_VOTE' ou inapto 'UNABLE_TO_VOTE' para votar"),
    })
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public ResponseEntity getStatus(@RequestParam(value = "cpf", required = true) String cpf) {
        String response = associadosService.getStatus(cpf);
        return ResponseEntity.ok(response);
    }

}
