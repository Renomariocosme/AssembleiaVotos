package br.com.desafiosolutis.service;

import br.com.desafiosolutis.advice.NotFoundException;
import br.com.desafiosolutis.advice.SessaoEncerradaException;
import br.com.desafiosolutis.advice.VotoInvalidoException;
import br.com.desafiosolutis.dto.*;
import br.com.desafiosolutis.model.SessaoVotacao;
import br.com.desafiosolutis.model.Usuario;
import br.com.desafiosolutis.model.Voto;
import br.com.desafiosolutis.repository.PautaRepository;
import br.com.desafiosolutis.repository.VotacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VotacaoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VotacaoService.class);

    private final VotacaoRepository repository;
    private final PautaService pautaService;
    private final SessaoVotacaoService sessaoVotacaoService;
    private final UsuarioService usuarioService;

    private final PautaRepository pautaRepository;


    @Autowired
    public VotacaoService(VotacaoRepository repository, PautaService pautaService, SessaoVotacaoService sessaoVotacaoService, UsuarioService usuarioService, PautaRepository pautaRepository) {
        this.repository = repository;
        this.pautaService = pautaService;
        this.sessaoVotacaoService = sessaoVotacaoService;
        this.usuarioService = usuarioService;
        this.pautaRepository = pautaRepository;

    }
    /**
        * metodo responsavel por realizar as validacoes antes do voto ser computado
     * e persistido na base de dados
     *
             * @param dto - @{@link VotoDTO}
     * @return - boolean
     */

    public boolean isValidaVoto(VotoDTO dto){
        LOGGER.info("Valindando os dados para voto idSessao = {} , idPauta = {} , idUsuario", dto.getIdSessaoVotacao(), dto.getIdPauta(), dto.getCpfUsuario());

        if (!pautaService.isPautaValida((int) dto.getIdPauta().longValue())){

            LOGGER.info("Pauta não localizada para votação idPauta = {}", dto.getIdPauta());
            throw new NotFoundException("Pauta não localizada id " + dto.getIdPauta());
        } else if(!sessaoVotacaoService.buscarSessaoVotacaoPeloId(dto.getIdSessaoVotacao()).get().sessaoExpirada()){

            LOGGER.info("Tentativa de voto para sessão encerrada idSessaoVotacao {}", dto.getIdSessaoVotacao());
            throw new SessaoEncerradaException("Sessão de votação já encerrada");

        } else if (!usuarioService.isValidaParticipacaoUsuarioVotacao(dto.getCpfUsuario(), dto.getIdPauta())) {

            LOGGER.info("Associado tentou votar mais de uma vez idUsuario {}" , dto.getCpfUsuario());
            throw new VotoInvalidoException("Não é possivel votar mais de uma vez na mesma pauta");
        }

        return Boolean.TRUE;
    }

    public String votar(VotoDTO dto){
        if(isValidaVoto(dto)){
            LOGGER.info("Dados validos para voto idSessao = {} , idCpfUsuario = {}", dto.getIdSessaoVotacao(), dto.getIdPauta(), dto.getCpfUsuario());

            VotacaoDTO votacaoDTO = new VotacaoDTO(null,
                    dto.getIdPauta(),
                    dto.getIdSessaoVotacao(),
                    dto.getVotoOpcao(),
                    null,
                    null);

            registrarVoto(votacaoDTO);

            registrarUsuarioVotou(dto);
        }
        return null;
    }

   public void registrarUsuarioVotou(VotoDTO dto){
       UsuarioDto usuarioDto = new UsuarioDto(null, dto.getCpfUsuario(), dto.getIdPauta());
       usuarioService.salvarUsuario(usuarioDto);
   }

    public void registrarVoto(VotacaoDTO dto){
        LOGGER.debug("Salvando o voto para o idPauta = {}", dto.getIdPauta());
        repository.save(VotacaoDTO.toEntity(dto));
    }


    public boolean isValidaSeDadosExiste(Integer idPauta, Integer idSessaoVotacao){
        return sessaoVotacaoService.isSessaoVotacaoExiste(idSessaoVotacao) && pautaService.isPautaValida(idPauta);
    }

    public Voto votando(VotoRespostaDTO dto){
        SessaoVotacao sessaoVotacao = sessaoVotacaoService.getById(dto.getSessaoVotacaoId());
        Usuario usuario = usuarioService.getByCpf(dto.getCpfUsuario());


        if (sessaoVotacao == null){
            throw new NotFoundException("Sessão não encontrada");

        }

        if (usuario== null){
            throw new NotFoundException("Usuário não encontrado");

        }

        Voto voto = Voto.builder()
                .votoOpcao(dto.getVotoOpcao())
                .horaVoto(LocalDateTime.now())
                .usuario(usuario)
                .sessaoVotacao(sessaoVotacao).build();


        return repository.save(voto);
    }
}
