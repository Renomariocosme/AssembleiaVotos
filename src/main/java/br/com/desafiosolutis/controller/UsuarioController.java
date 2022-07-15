package br.com.desafiosolutis.controller;

import br.com.desafiosolutis.dto.TokenDTO;
import br.com.desafiosolutis.dto.UserLoginDTO;
import br.com.desafiosolutis.model.Usuario;
import br.com.desafiosolutis.repository.UsuarioRepository;
import br.com.desafiosolutis.security.TokenService;
import br.com.desafiosolutis.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final UsuarioService service;

    private TokenService tokenService;

    private AuthenticationManager authenticationManager;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos(){
        List<Usuario> usuarios = service.listarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Integer id){
        Optional<Usuario> usuario = service.buscarPorId(id);

        if(usuario.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(usuario.get());
    }

    @PostMapping
    public ResponseEntity<Usuario> cadastrar(@Valid @RequestBody Usuario usuario){
        LOGGER.info("Cadastrando o usuario no banco de dados" + buscarPorId(usuario.getId()));
        Usuario salvarUsuario = service.salvar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvarUsuario);
    }

    @PutMapping
    public ResponseEntity<Usuario> atualizar(@Valid @RequestBody Usuario usuarioDto) {
        Usuario salvar = service.salvar(usuarioDto);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioDto);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
       service.deletar(id);
       return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/criar")
    public ResponseEntity<Usuario> criar(@Valid @RequestBody Usuario usuario){
        LOGGER.info("CRIANDO USUÁRIO");
        return new ResponseEntity(service.criarUsuario(usuario), HttpStatus.CREATED);
    }

        

}
