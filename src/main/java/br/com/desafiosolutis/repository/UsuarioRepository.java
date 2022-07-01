package br.com.desafiosolutis.repository;

import br.com.desafiosolutis.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByNome(String nome);

    Optional<Usuario> findByEmail(String email);


}
