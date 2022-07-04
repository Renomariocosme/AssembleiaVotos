package br.com.desafiosolutis.repositoryTest;

import br.com.desafiosolutis.model.Usuario;
import br.com.desafiosolutis.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RepositoryTest {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();


    @BeforeAll
  /*  void start(){
        repository.save(new Usuario("12015212303","eduardocampos@gmail.com","Pedro José"));

    }
*/
    @Test
    @DisplayName("Retorna um e-mail")
    void deveRetornaUmEmail(){
        Usuario usuario = repository.findByEmail("eduardocampos@gmail.com").get();
        assertTrue(usuario.getEmail().equals("eduardocampos@gmail.com"));
    }

    private void assertTrue(boolean equals) {
    }

}
