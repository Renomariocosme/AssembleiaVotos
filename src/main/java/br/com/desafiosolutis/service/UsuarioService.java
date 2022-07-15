package br.com.desafiosolutis.service;

import br.com.desafiosolutis.config.ValidarCpf;
import br.com.desafiosolutis.dto.UsuarioDto;
import br.com.desafiosolutis.model.Usuario;
import br.com.desafiosolutis.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Service
public class UsuarioService {


    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioService.class);
    private final UsuarioRepository repository;

    private final ValidarCpf validarCpf;


    @Autowired
    public UsuarioService(UsuarioRepository repository, ValidarCpf validarCpf){
        this.repository = repository;
        this.validarCpf = validarCpf;
    }


    public boolean isValidaParticipacaoUsuarioVotacao(String cpfUsuario, Integer idPauta){
        LOGGER.debug("Validando participação do associado na votacao da pauta id = {}", idPauta);
        if (repository.existsByCpfUsuario(cpfUsuario)){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public void salvarUsuario(UsuarioDto dto){
        LOGGER.debug("Registrando particapacao do usuario na votacao idUsuario = {} , idPauta = {}", dto.getCpfUsuario());
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

    public Usuario getByCpf(String cpfUsuario){
        return repository.findByCpfUsuario(cpfUsuario).orElse(null);
    }

    private String encryptPassword(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }


    public Optional<Usuario> criarUsuario(Usuario usuario){
        if (repository.findByCpfUsuario(usuario.getCpfUsuario()).isPresent()){
           LOGGER.error("CPF já cadastrado = {}", usuario.getCpfUsuario());
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"CPF JÁ CADASTRADO!");
        }
        if (repository.findByEmail(usuario.getEmail()).isPresent()){
            LOGGER.error("Email já cadastrado = {}", usuario.getEmail());
        }
        usuario.setSenha(encryptPassword(usuario.getSenha()));
        LOGGER.info("SALVANDO USUARIO");
        return Optional.of(repository.save(usuario));
    }






}


