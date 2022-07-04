package br.com.desafiosolutis.service;

import br.com.desafiosolutis.advice.NotFoundException;
import br.com.desafiosolutis.advice.SessaoEncerradaException;
import br.com.desafiosolutis.advice.VotoInvalidoException;
import br.com.desafiosolutis.dto.*;
import br.com.desafiosolutis.repository.VotacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VotacaoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VotacaoService.class);

    private final VotacaoRepository repository;
    private final PautaService pautaService;
    private final SessaoVotacaoService sessaoVotacaoService;
    private final UsuarioService usuarioService;


    @Autowired
    public VotacaoService(VotacaoRepository repository, PautaService pautaService, SessaoVotacaoService sessaoVotacaoService, UsuarioService usuarioService) {
        this.repository = repository;
        this.pautaService = pautaService;
        this.sessaoVotacaoService = sessaoVotacaoService;
        this.usuarioService = usuarioService;

    }
    /**
        * metodo responsavel por realizar as validacoes antes do voto ser computado
     * e persistido na base de dados
     *
             * @param dto - @{@link VotoDTO}
     * @return - boolean
     */

    public boolean isValidaVoto(VotoDTO dto){
        LOGGER.debug("Valindando os dados para voto idSessao = {} , idPauta = {} , idUsuario", dto.getIdSessaoVotacao(), dto.getIdPauta(), dto.getCpfUsuario());
        
        if (!pautaService.isPautaValida((int) dto.getIdPauta().longValue())){
            
            LOGGER.debug("Pauta não localizada para votação idPauta = {}", dto.getCpfUsuario());
            throw new NotFoundException("Pauta não localizada id " + dto.getIdPauta());
        } else if(!sessaoVotacaoService.isSessaoVotacaoValida(dto.getIdSessaoVotacao())){
            
            LOGGER.debug("Tentativa de voto para sessão encerrada idSessaoVotacao {}", dto.getIdSessaoVotacao());
            throw new SessaoEncerradaException("Sessão de votação já encerrada");
            
        } else if (!usuarioService.isValidaParticipacaoUsuarioVotacao(dto.getCpfUsuario(), dto.getIdPauta())) {

            LOGGER.debug("Associado tentou votar mais de uma vez idUsuario {}" , dto.getCpfUsuario());
            throw new VotoInvalidoException("Não é possivel votar mais de uma vez na mesma pauta");
        }

        return Boolean.TRUE;
    }
    /**
     * Se os dados informados para o voto, forem considerados validos
     * entao o voto é computado e persistido na base de dados.
     *
     * @param dto - @{@link VotoDTO}
     * @return - String
     */

    public String votar(VotoDTO dto){
        if(isValidaVoto(dto)){
            LOGGER.debug("Dados validos para voto idSessao = {} , idCpfUsuario = {}", dto.getIdSessaoVotacao(), dto.getIdPauta(), dto.getCpfUsuario());

            VotacaoDTO votacaoDTO = new VotacaoDTO(null,
                    dto.getIdPauta(),
                    dto.getIdSessaoVotacao(),
                    dto.getVoto(),
                    null,
                    null);

            registrarVoto(votacaoDTO);

            registrarUsuarioVotou(dto);
        }
        return null;
    }

   /**
            * Apos voto ser computado. O associado e registrado na base de dados a fim de
     * evitar que o mesmo possa votar novamente na mesma sessao de votacao e na mesma pauta.
            * <p>
     * A opcao de voto nao e persistido na base de dados.
     *
             * @param dto - @{@link VotoDTO}
     */

   public void registrarUsuarioVotou(VotoDTO dto){
       UsuarioDto usuarioDto = new UsuarioDto(null, dto.getCpfUsuario(), dto.getIdPauta());
       usuarioService.salvarUsuario(usuarioDto);
   }
    /**
     * @param dto - @{@link VotacaoDTO}
     */

    public void registrarVoto(VotacaoDTO dto){
        LOGGER.debug("Salvando o voto para o idPauta = {}", dto.getIdPauta());
        repository.save(VotacaoDTO.toEntity(dto));
    }

    /**
     * Realiza a busca e contagem dos votos positivos e negativos para determinada sessao e pauta de votacao.
     *
     * @param idPauta         - @{@link br.com.desafiosolutis.model.Pauta} ID
     * @param idSessaoVotacao - @{@link br.com.desafiosolutis.model.SessaoVotacao} ID
     * @return - @{@link VotacaoDTO}
     */

    public VotacaoDTO buscarResultadoVotacao(Integer idPauta, Integer idSessaoVotacao){
        LOGGER.debug("Contabilizando os votos para idPauta = {}, idSessaoVotacao = {}", idPauta, idSessaoVotacao);
        VotacaoDTO dto = new VotacaoDTO();

        dto.setIdPauta(idPauta);
        dto.setIdSessaoVotacao(idSessaoVotacao);

        dto.setQuantidadeVotosSim(repository.countVotacaoByIdPautaAndIdSessaoVotacaoAndVoto(idPauta,idSessaoVotacao, Boolean.TRUE));
        dto.setQuantidadeVotosNao(repository.countVotacaoByIdPautaAndIdSessaoVotacaoAndVoto(idPauta, idSessaoVotacao, Boolean.FALSE));

        return dto;
    }
    /**
     * Realiza a montagem dos objetos referente ao resultado de determinada sessao e pauta de votacao.
     * <p>
     * Contagem somente e realizada apos a finalizacao da sessao.
     *
     * @param idPauta         - @{@link br.com.desafiosolutis.model.Pauta} ID
     * @param idSessaoVotacao - @{@link br.com.desafiosolutis.model.SessaoVotacao} ID
     * @return - @{@link ResultadoDTO
     *
     */

    public ResultadoDTO buscarDadosResultadoVotacao(Integer idPauta, Integer idSessaoVotacao){

        if (isValidaSeDadosExiste(idPauta, idSessaoVotacao) && sessaoVotacaoService.isSessaoValidaParaContagem(idSessaoVotacao)){
            LOGGER.debug("Construindo o objeto de retorno do resultado para idPauta = {}, idSessaoVotacao = {}", idPauta, idSessaoVotacao);
            PautaDto pautaDto = pautaService.buscarPautaPeloId(idPauta);
            VotacaoDTO votacaoDTO = buscarResultadoVotacao(idPauta, idSessaoVotacao);
            return new ResultadoDTO(pautaDto, votacaoDTO);
        }
        throw new NotFoundException("Sessao de votação ainda está aberta, não é possivel obter o resultado a contagem do resultado");
    }

    /**
     * @param idPauta         - @{@link br.com.desafiosolutis.model.Pauta} ID
     * @param idSessaoVotacao - @{@link br.com.desafiosolutis.model.SessaoVotacao} ID
     * @return - boolean
     */

    public boolean isValidaSeDadosExiste(Integer idPauta, Integer idSessaoVotacao){
        return sessaoVotacaoService.isSessaoVotacaoExiste(idSessaoVotacao) && pautaService.isPautaValida(idPauta);
    }
}
