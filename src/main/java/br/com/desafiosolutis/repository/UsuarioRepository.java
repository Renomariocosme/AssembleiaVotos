package br.com.desafiosolutis.repository;

import br.com.desafiosolutis.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByNome(String nome);

    Optional<Usuario> findByEmail(String email);

    Boolean existsByCpfUsuario(String cpfUsuario);

    Optional<Usuario> findByCpfUsuario(String cpfUsuario);

}
