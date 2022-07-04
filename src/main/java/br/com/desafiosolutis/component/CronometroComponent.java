package br.com.desafiosolutis.component;

import br.com.desafiosolutis.dto.SessaoVotacaoDTO;
import br.com.desafiosolutis.service.SessaoVotacaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CronometroComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(CronometroComponent.class);


    private final SessaoVotacaoService sessaoVotacaoService;

    public CronometroComponent(SessaoVotacaoService sessaoVotacaoService){
        this.sessaoVotacaoService = sessaoVotacaoService;
    }

    @Scheduled(cron = "15 * * * * *")
    private void teste(){
        LOGGER.debug("Contador de tempo sendo executado...");
        List<SessaoVotacaoDTO> list = sessaoVotacaoService.buscarSessoesEmAndamento();
        LOGGER.debug("Quantidade de sessões abertas = {}", list.size());
        list.forEach(dto -> {
            LOGGER.debug("Sessao encerrada {}", dto.getId());
            if (dto.getAtiva()) {
                sessaoVotacaoService.EncerrarSessaoVotacao(dto);
            }
        });

    }

}
