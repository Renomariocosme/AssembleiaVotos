package br.com.desafiosolutis.service;

import br.com.desafiosolutis.advice.NotFoundException;
import br.com.desafiosolutis.dto.PautaDto;
import br.com.desafiosolutis.dto.PautaRespostaDTO;
import br.com.desafiosolutis.dto.SessaoVotacaoAbrirDTO;
import br.com.desafiosolutis.dto.VotacaoRequisicaoDTO;
import br.com.desafiosolutis.model.Pauta;
import br.com.desafiosolutis.model.SessaoVotacao;
import br.com.desafiosolutis.model.Voto;
import br.com.desafiosolutis.model.enumereted.StatusEnum;
import br.com.desafiosolutis.model.enumereted.VotoOpcao;
import br.com.desafiosolutis.repository.PautaRepository;
import br.com.desafiosolutis.repository.SessaoVotacaoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SessaoVotacaoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessaoVotacaoService.class);
    private static final Integer TEMPO_DEFAULT = 60;

    private final SessaoVotacaoRepository repository;
    private final PautaService pautaService;

    private final PautaRepository pautaRepository;


    public Optional<Pauta> buscarPautaPorId(Integer id) {
        return pautaRepository.findById(id);
    }


    public SessaoVotacao abrirSessaoVotacao(SessaoVotacaoAbrirDTO sessaoVotacaoAbrirDTO) {
        Pauta pauta = pautaRepository.findById(sessaoVotacaoAbrirDTO.getIdPauta()).orElse(null);

        SessaoVotacao sessaoVotacao = SessaoVotacao.builder().pauta(pauta).build();


//        if (sessaoVotacao.getPauta().getStatus().equals(StatusEnum.valueOf("FECHADO"))) {
//            throw new RuntimeException("A pauta se encontrada fechada para votação");
//        }
        sessaoVotacao.setDuracao(calcularTempo(sessaoVotacaoAbrirDTO.getTempo()));
        sessaoVotacao.setDataInicio(LocalDateTime.now());
        sessaoVotacao.setDataFim(LocalDateTime.now().plus(sessaoVotacao.getDuracao(), ChronoUnit.SECONDS));

        return repository.save(sessaoVotacao);
    }



    public void EncerrarSessaoVotacao(SessaoVotacao sessaoVotacao) {
        LOGGER.info("Encerrando sessão com tempo de duração expirado = {}");
        if (sessaoVotacao.getDataFim().isBefore(LocalDateTime.now())) {
            sessaoVotacao.getPauta().setStatus(StatusEnum.FECHADO);

            sessaoVotacao.getPauta().setTotal(sessaoVotacao.getVoto().size());


            pautaRepository.save(sessaoVotacao.getPauta());

        }

        repository.save(sessaoVotacao);

    }


    public Optional<SessaoVotacao> buscarSessaoVotacaoPeloId(Integer id) {
        Optional<SessaoVotacao> optionalSessaoVotacao = repository.findById(id);
        if (!optionalSessaoVotacao.isPresent()) {
            LOGGER.info("Sessao de votacao nao localizada para o oid {}", id);
            throw new NotFoundException("Sessão de votação não localizada para o id" + id);
        }
        return optionalSessaoVotacao;
    }


    public boolean isSessaoVotacaoExiste(Integer id) {
        if (repository.existsById(id)) {
            return Boolean.TRUE;
        } else {
            LOGGER.info("Sessao de votacao não localizada para o id {}", id);
            throw new NotFoundException("Sessão de votação não localizada para o id" + id);
        }
    }

    public List<SessaoVotacao> buscarSessoesEmAndamento() {


        return repository.findAll()
                .stream()
                .filter(sessao ->
                        sessao.getDataFim() != null && sessao.getDataFim().isBefore(LocalDateTime.now())
                                && sessao.getPauta().getStatus().equals(StatusEnum.valueOf("ABERTO"))

                )
                .collect(Collectors.toList());



    }



    private Integer calcularTempo(Integer tempo) {
        if (tempo != null && tempo != 0) {
            return tempo;
        } else {
            return TEMPO_DEFAULT;
        }
    }



    public SessaoVotacao criarSessaoVotacao(VotacaoRequisicaoDTO dto) {

        if (repository.existsByPautaId(dto.getIdPauta())) {
            throw new NotFoundException("Pauta não existente");
        }

        SessaoVotacao sessaoVotacao = SessaoVotacao.builder()
                .duracao(calcularTempo(dto.getDuracao()))
                .pauta(buscarPauta(dto))
                .build();

        return repository.save(sessaoVotacao);
    }

    private Pauta buscarPauta(VotacaoRequisicaoDTO dto) {
        return pautaRepository.findById(dto.getIdPauta()).orElseThrow(() -> new NotFoundException("PAUTA NÃO EXISTE"));
    }

    public Integer quantidadesSim(SessaoVotacao sessaoVotacao) {
        return Math.toIntExact(sessaoVotacao.getVoto().stream().filter(c -> c.getVotoOpcao().equals(VotoOpcao.valueOf("SIM")))
                .count());
    }

    public Integer quantidadeNao(SessaoVotacao sessaoVotacao) {
        return Math.toIntExact(sessaoVotacao.getVoto().stream()
                .filter(c -> c.getVotoOpcao().equals(VotoOpcao.valueOf("NÃO")))
                .count());
    }


    private List<SessaoVotacao> todasVotacoesExpiradas() {


        return repository.findAll().stream()
                .filter(sessaoVotacao ->  sessaoVotacao.Aberta()
                ).collect(Collectors.toList());

    }

    @Scheduled(fixedDelay = 6000)
    public void sessaoFechada(){

       List<SessaoVotacao> votacaoList = todasVotacoesExpiradas();

        votacaoList.forEach(v ->{
            System.out.println(v);
            v.getPauta().setTotal(v.getVoto().size());
            v.getPauta().setQuantidadeVotoSim(quantidadesSim(v));
            v.getPauta().setQuantidadeVotoNao(quantidadeNao(v));
            repository.save(v);
            pautaService.Status(v.getPauta());
            pautaService.Percentual(v.getPauta());
            pautaService.calcularVencedor(v.getPauta());
            pautaRepository.save(v.getPauta());
            PautaRespostaDTO pautaRespostaDTO = PautaRespostaDTO.converteParaDto(v.getPauta());
        });
    }


    private void teste(){


        List<SessaoVotacao> list = buscarSessoesEmAndamento();

        list.forEach(sessaoVotacao -> {

            if (sessaoVotacao.getPauta().getStatus().equals(StatusEnum.valueOf("ABERTA"))) {
                EncerrarSessaoVotacao(sessaoVotacao);
            }
        });

}
        public SessaoVotacao getById(Integer id){

        return repository.findById(id).orElse(null);
        }

}


