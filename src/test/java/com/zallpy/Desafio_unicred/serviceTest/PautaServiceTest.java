package com.zallpy.Desafio_unicred.serviceTest;

import com.zallpy.Desafio_unicred.dao.PautaDao;
import com.zallpy.Desafio_unicred.exception.holder.CustomException;
import com.zallpy.Desafio_unicred.holder.Enum.EnumStatusPauta;
import com.zallpy.Desafio_unicred.holder.PautaHolder;
import com.zallpy.Desafio_unicred.holder.VotoHolder;
import com.zallpy.Desafio_unicred.model.Pauta;
import com.zallpy.Desafio_unicred.model.Voto;
import com.zallpy.Desafio_unicred.service.PautaService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
public class PautaServiceTest {

    @Autowired
    private PautaService pautaService;

    @MockBean
    private PautaDao pautaDao;

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(this.pautaService);
    }

    @Test
    public void retornaTrue_AoSalvarNovaPauta() {
        PautaHolder pautaHolder = new PautaHolder();
        pautaHolder.setCodigo("345");
        Assertions.assertDoesNotThrow(() -> pautaService.criarPauta(pautaHolder));
    }

    @Test
    public void deveLancarException_QuandoPautaExistente() {
        Mockito
            .when(this.pautaDao.getPautaByCodigo("pt1"))
            .thenReturn(new Pauta(1, "pauta1", "pt1",EnumStatusPauta.AGUARDANDO, LocalDateTime.now(), LocalDateTime.now()));

        PautaHolder pautaHolder = new PautaHolder();
        pautaHolder.setCodigo("pt1");

        Assertions.assertThrows(CustomException.class, () -> pautaService.criarPauta(pautaHolder));
    }

    @Test
    public void deveLancarException_QuandoTentarIniciarVotacaoEPautaInexistente() {
        Assertions.assertThrows(CustomException.class, () -> pautaService.inicarVotacao("456", 1));
    }

    @Test
    public void deveRetornarDataTerminoVotacao_QuandoVotacaoIniciada() {
        Mockito
            .when(this.pautaDao.getPautaByCodigo("pt1"))
            .thenReturn(new Pauta(1, "pauta1", "pt1",EnumStatusPauta.AGUARDANDO, null, null));

        LocalDateTime dtInicio = LocalDateTime.now();
        LocalDateTime dtFim = dtInicio.plusMinutes(2);

        LocalDateTime returnDate = pautaService.inicarVotacao("pt1", 2);
        LocalDateTime dtFimPlus = dtFim.plusMinutes(1);

        //Necessario trabalhar com um range para compensar a diferença entre a chamada dos métodos
        Assertions.assertTrue((dtFim.isBefore(returnDate) ||  dtFim.isEqual(returnDate)) && dtFimPlus.isAfter(returnDate));
    }

    @Test
    public void deveLancarException_QuandoVotacaoJaIniciadaOuFinalizada() {
        LocalDateTime dtInicio = LocalDateTime.now();
        LocalDateTime dtFim = dtInicio.plusMinutes(2);

        Mockito
            .when(this.pautaDao.getPautaByCodigo("pt1"))
            .thenReturn(new Pauta(1, "pauta1", "pt1",EnumStatusPauta.EM_VOTACAO, dtInicio, dtFim));

        Mockito
            .when(this.pautaDao.getPautaByCodigo("pt2"))
            .thenReturn(new Pauta(1, "pauta2", "pt2",EnumStatusPauta.AUTORIZADA, dtInicio, dtFim));

        Assertions.assertThrows(CustomException.class, () -> pautaService.inicarVotacao("pt1", 2));
        Assertions.assertThrows(CustomException.class, () -> pautaService.inicarVotacao("pt2", 2));

    }

    @Test
    public void deveLancarException_QuandoVotarPautaInexistente() {
        VotoHolder votoHolder = new VotoHolder();
        votoHolder.setCodPauta("123");
        Assertions.assertThrows(CustomException.class, () -> pautaService.votarPauta(votoHolder));
    }

    @Test
    public void deveLancarException_QuandoVotacaoNaoEstiverAberta() {
        Mockito
            .when(this.pautaDao.getPautaByCodigo("pt1"))
            .thenReturn(new Pauta(1, "pauta1", "pt1",EnumStatusPauta.AGUARDANDO, null, null));

        VotoHolder votoHolder = new VotoHolder();
        votoHolder.setCodPauta("123");

        Assertions.assertThrows(CustomException.class, () -> pautaService.votarPauta(votoHolder));
    }

    @Test
    public void deveLancarException_QuandoUsuarioJaVotou() {
        LocalDateTime dtInicio = LocalDateTime.now();
        LocalDateTime dtFim = dtInicio.plusMinutes(2);

        Mockito
            .when(this.pautaDao.getPautaByCodigo("pt1"))
            .thenReturn(new Pauta(1, "pauta1", "pt1",EnumStatusPauta.EM_VOTACAO, dtInicio, dtFim));

        Mockito
            .when(this.pautaDao.getVotoByPautaEAssociado("pt1", 123))
            .thenReturn(new Voto(1, 123, true));

        VotoHolder votoHolder = new VotoHolder();
        votoHolder.setCodPauta("pt1");
        votoHolder.setCodPauta("123");

        Assertions.assertThrows(CustomException.class, () -> pautaService.votarPauta(votoHolder));
    }

    @Test
    public void executaSemExcessao_AoSalvarVoto() {
        LocalDateTime dtInicio = LocalDateTime.now();
        LocalDateTime dtFim = dtInicio.plusMinutes(2);

        Mockito
            .when(this.pautaDao.getPautaByCodigo("pt1"))
            .thenReturn(new Pauta(1, "pauta1", "pt1",EnumStatusPauta.EM_VOTACAO, dtInicio, dtFim));

        VotoHolder votoHolder = new VotoHolder();
        votoHolder.setCodPauta("pt1");
        votoHolder.setCodAssociado(123);

        Assertions.assertDoesNotThrow(() -> pautaService.votarPauta(votoHolder));
    }

    @Test
    public void retornarPauta_QuandoEnviadoHolder() {


    }

    @Test
    public void retornarPauta_QuandoPautaExistente() {
        Pauta pauta = new Pauta(1, "pauta1", "pt1",EnumStatusPauta.AGUARDANDO, null, null);
        Mockito
            .when(this.pautaDao.getPautaByCodigo("pt1"))
            .thenReturn(pauta);

        Assertions.assertEquals(pautaService.getPautaByCodigo("pt1"), pauta.getPautaHolder());
    }

    @Test
    public void deveLancarException_QuandoPautaInexistente() {
        Assertions.assertThrows(CustomException.class, () -> pautaService.getPautaByCodigo("pt1"));
    }

}
