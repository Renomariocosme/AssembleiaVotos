package br.com.desafiosolutis.controller;

import br.com.desafiosolutis.model.Usuario;
import br.com.desafiosolutis.repository.UsuarioRepository;
import br.com.desafiosolutis.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
@RequiredArgsConstructor
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final UsuarioService service;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos(){
        List<Usuario> usuarios = service.listarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id){
        Optional<Usuario> usuario = service.buscarPorId(id);

        if(usuario.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(usuario.get());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Usuario> cadastrar(@Valid @RequestBody Usuario usuario){
        Usuario salvarUsuario = service.salvar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvarUsuario);
    }

    @PutMapping
    public ResponseEntity<Usuario> atualizar(@Valid @RequestBody Usuario usuarioDto) {
        Usuario salvar = service.salvar(usuarioDto);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioDto);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
       service.deletar(id);
       return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
