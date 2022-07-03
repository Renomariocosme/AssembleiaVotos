package br.com.desafiosolutis.service;

import br.com.desafiosolutis.advice.NotFoundException;
import br.com.desafiosolutis.dto.SessaoVotacaoAbrirDTO;
import br.com.desafiosolutis.dto.SessaoVotacaoDTO;
import br.com.desafiosolutis.model.SessaoVotacao;
import br.com.desafiosolutis.repository.SessaoVotacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SessaoVotacaoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessaoVotacaoService.class);
    private static final Integer TEMPO_DEFAULT = 1;

    private final SessaoVotacaoRepository repository;
    private final PautaService pautaService;


    @Autowired
    public SessaoVotacaoService(SessaoVotacaoRepository repository, PautaService pautaService){
        this.repository = repository;
        this.pautaService = pautaService;

    }
/**
 * Se a sessao votacao é valida entao persiste os dados na base e inicia
 * a contagem para o encerramento da mesma.
 *
 * @param sessaoVotacaoAbrirDTO - @{@link SessaoVotacaoAbrirDTO}
 * @return - @{@link SessaoVotacaoDTO}
 */


    public SessaoVotacaoDTO abrirSessaoVotacao(SessaoVotacaoAbrirDTO sessaoVotacaoAbrirDTO){
        LOGGER.debug("Abrindo a sessao de votacao para a pauta {}", sessaoVotacaoAbrirDTO.getIdPauta());

        isValidaAbrirSessao(sessaoVotacaoAbrirDTO);

        SessaoVotacaoDTO dto = new SessaoVotacaoDTO(
                null,
                LocalDateTime.now(),
                calcularTempo(sessaoVotacaoAbrirDTO.getTempo()),
                Boolean.TRUE);

        return salvar(dto);
    }

    /**
     * valida se os dados para iniciar uma validacao são consistentes
     * e ja estao persistidos na base de dados
     *
     * @param sessaoVotacaoAbrirDTO - @{@link SessaoVotacaoAbrirDTO}
     * @return - boolean
     */

    public boolean isValidaAbrirSessao(SessaoVotacaoAbrirDTO sessaoVotacaoAbrirDTO){
        if (pautaService.isPautaValida((int) sessaoVotacaoAbrirDTO.getIdPauta().longValue())){
            return Boolean.TRUE;
        } else {
            throw new NotFoundException("Pauta não localizada pelo ID" + sessaoVotacaoAbrirDTO.getIdPauta());
        }
    }
    /**
     * busca se ha sessoes em andamento, se houver, os dados sao retornados para o validador de tempo
     *
     * @return - List<@{@link SessaoVotacaoDTO}>
     */

    public List<SessaoVotacaoDTO> buscarSessoesEmAndamento(){
        LOGGER.debug("Buscando sessoes em andamento");
        List<SessaoVotacaoDTO> list = repository.buscarTodasSessoesEmAndamento(Boolean.TRUE)
                .stream()
                .map(SessaoVotacaoDTO::toDTO)
                .collect(Collectors.toList());

        return list
                .stream()
                .filter(dto -> dto.getDataHoraInicio().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());


    }

    /**
     * Quando houver sessao de votacao com o tempo data hora fim expirado,
     * a flag ativo é setado como FALSE e persistido a alteracao na base de dados.
     *
     * @param dto - @{@link SessaoVotacaoDTO}
     */

    public void EncerrarSessaoVotacao(SessaoVotacaoDTO dto){
        LOGGER.debug("Encerrando sessão com tempo de duração expirado{}", dto.getId());
        dto.setAtiva(Boolean.FALSE);
        salvar(buscarSessaoVotacaoPeloId(dto.getId()));
    }
    /**
     * @param id - @{@link SessaoVotacaoDTO} ID
     * @return - @{@link SessaoVotacaoDTO}
     */

    public SessaoVotacaoDTO buscarSessaoVotacaoPeloId(Integer id){
        Optional<SessaoVotacao> optionalSessaoVotacao = repository.findById(id);
        if(!optionalSessaoVotacao.isPresent()){
            LOGGER.error("Sessao de votacao nao localizada para o oid {}", id);
            throw new NotFoundException("Sessão de votação não localizada para o id" + id);
        }
        return SessaoVotacaoDTO.toDTO(optionalSessaoVotacao.get());
    }

    /**
     * Se houver a sessao de ID informado e com a tag ativa igual a TRUE
     * entao e considerada como valida para votacao.
     *
     * @param id - @{@link SessaoVotacao} ID
     * @return - boolean
     */

    public boolean isSessaoVotacaoValida(Integer id){
        return repository.existsByIdAndAndAtiva(id, Boolean.TRUE);
    }

    /**
     * @param id - @{@link SessaoVotacao} ID
     * @return - boolean
     */

    public boolean isSessaoVotacaoExiste(Integer id){
        if (repository.existsById(id)){
            return Boolean.TRUE;
        }else{
            LOGGER.error("Sessao de votacao não localizada para o id {}", id);
            throw new NotFoundException("Sessão de votação não localizada para o id" + id);
        }
    }
/**
 * Se houver a sessao de ID informado e com tag ativa igual a FALSE
 * entao e considerada como valida para contagem
 *
 * @param id - @{@link SessaoVotacao} ID
 * @return - boolean
 */

    public boolean isSessaoValidaParaContagem(Integer id){
        return repository.existsByIdAndAndAtiva(id, Boolean.FALSE);
    }

    /**
     * Com base no LocalDateTime inicial é calculada a LocalDateTime final somando-se o
     * tempo em minutos informado na chamada do servico.
     * <p>
     * Se o tempo nao for informado ou for informado com valor 0,
     * entao é considerado o tempo de 1 minuto como default.
     *
     * @param tempo - tempo em minutos
     * @return - localDateTime
     */

    private LocalDateTime calcularTempo(Integer tempo){
        if (tempo != null && tempo != 0){
            return LocalDateTime.now().plusSeconds(tempo);
        } else {
            return LocalDateTime.now().plusSeconds(TEMPO_DEFAULT);
        }
    }
    /**
     * @param dto - @{@link SessaoVotacaoDTO}
     * @return - @{@link SessaoVotacaoDTO}
     */

    public SessaoVotacaoDTO salvar(SessaoVotacaoDTO dto){
        LOGGER.debug("Salvando a sessao de votacao...");
        if(Optional.ofNullable(dto).isPresent()){
            return SessaoVotacaoDTO.toDTO(repository.save(SessaoVotacaoDTO.toEntity(dto)));
        }
        return null;
    }


}
