package com.zallpy.Desafio_unicred.serviceTest;

import com.zallpy.Desafio_unicred.exception.holder.CustomException;
import com.zallpy.Desafio_unicred.service.AssociadosService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class AssociadosServiceTest {

    @Autowired
    private AssociadosService associadosService;

    @Test
    public void deveLancarException_QuandoCpfInvalido() {
        Assertions.assertThrows(CustomException.class, () -> associadosService.getStatus("0936656"));
        Assertions.assertThrows(CustomException.class, () -> associadosService.getStatus("8741586498984984"));
        Assertions.assertThrows(CustomException.class, () -> associadosService.getStatus("15644805498"));
        Assertions.assertThrows(CustomException.class, () -> associadosService.getStatus("87198654922"));
        Assertions.assertThrows(CustomException.class, () -> associadosService.getStatus("093336"));
    }

    @Test
    public void retornaStatus_QuandoCpfValido() {
        Assertions.assertTrue(StringUtils.isNotBlank(associadosService.getStatus("09333564985")));
        Assertions.assertTrue(StringUtils.isNotBlank(associadosService.getStatus("10838276008")));
        Assertions.assertTrue(StringUtils.isNotBlank(associadosService.getStatus("53744907023")));
        Assertions.assertTrue(StringUtils.isNotBlank(associadosService.getStatus("23279047064")));
    }
}
