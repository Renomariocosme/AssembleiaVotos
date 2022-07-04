package br.com.desafiosolutis.service;

import br.com.desafiosolutis.advice.Exception;
import br.com.desafiosolutis.dto.UsuarioDto;
import br.com.desafiosolutis.model.Usuario;
import br.com.desafiosolutis.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UsuarioService {


    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioService.class);
    private final UsuarioRepository repository;


    @Autowired
    public UsuarioService(UsuarioRepository repository){
        this.repository = repository;
    }

    /**
     * Realiza a validacao se o associado ja votou na pauta informada pelo seu ID.
     * <p>
     * Se nao existir um registro na base, entao e considerado como valido para seu voto ser computado
     *
     * @param cpfUsuario @{@link br.com.desafiosolutis.model.Usuario} CPF Valido
     * @param idPauta     @{@link br.com.desafiosolutis.model.Pauta} ID
     * @return - boolean
     */

    public boolean isValidaParticipacaoUsuarioVotacao(String cpfUsuario, Integer idPauta){
        LOGGER.debug("Validando participação do associado na votacao da pauta id = {}", idPauta);
        if (repository.existsByCpfUsuarioAndIdPauta(cpfUsuario, idPauta)){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
    /**
    * @param dto @{@link br.com.desafiosolutis.dto.UsuarioDto}
     */

    public void salvarUsuario(UsuarioDto dto){
        LOGGER.debug("Registrando particapacao do usuario na votacao idUsuario = {} , idPauta = {}", dto.getCpfUsuario(), dto.getIdPauta());
        repository.save(UsuarioDto.toEntity(dto));
    }


    public List<Usuario> listarTodos(){
        return repository.findAll();
    }

    public Optional<Usuario> buscarPorId(Integer id){
        return repository.findById(id);
    }

    public void deletar(Integer id){
        repository.deleteById(id);
    }

    public Usuario salvar(Usuario usuario){
        return repository.save(usuario);
    }
}
