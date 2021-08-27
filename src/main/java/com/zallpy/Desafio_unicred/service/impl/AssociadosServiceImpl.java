package com.zallpy.Desafio_unicred.service.impl;

import com.zallpy.Desafio_unicred.exception.holder.CustomException;
import com.zallpy.Desafio_unicred.service.AssociadosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.InputMismatchException;
import java.util.Map;

@Service
public class AssociadosServiceImpl implements AssociadosService {

    @Override
    public String getStatus(String cpf) {
        boolean isCpf = validacpf(cpf);
        if(isCpf) {
            String status = consultarcpf(cpf);
            return status;
        }else{
            throw  new CustomException("cpf não encontrado ou inválido", HttpStatus.NOT_FOUND);
        }
    }

    private Boolean validacpf(String cpf) {
        if (cpf.equals("00000000000") ||
                cpf.equals("11111111111") ||
                cpf.equals("22222222222") || cpf.equals("33333333333") ||
                cpf.equals("44444444444") || cpf.equals("55555555555") ||
                cpf.equals("66666666666") || cpf.equals("77777777777") ||
                cpf.equals("88888888888") || cpf.equals("99999999999") ||
                (cpf.length() != 11)){
            return(false);
        }

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
                num = (int)(cpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)){
                dig10 = '0';
            } else {
                dig10 = (char)(r + 48);
            }

            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = (int)(cpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)){
                dig11 = '0';
            } else {
                dig11 = (char)(r + 48);
            }

            if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10))) {
                return(true);
            } else {
                return(false);
            }
        } catch (InputMismatchException erro) {
            return(false);
        }
    }

    private String consultarcpf(String cpf) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.getForEntity("https://user-info.herokuapp.com/users/" + cpf,  Map.class);

        if(response.getStatusCode() == HttpStatus.OK) {
            Map<String, String> responseBody = response.getBody();
            return responseBody.get("status");
        }else{
            throw new CustomException("Erro ao consultar cpf", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
