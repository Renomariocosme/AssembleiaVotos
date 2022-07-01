package br.com.desafiosolutis.service;

import br.com.desafiosolutis.advice.Exception;
import br.com.desafiosolutis.model.Usuario;
import br.com.desafiosolutis.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UsuarioService {

  @Autowired
  private UsuarioRepository repository;

   public Usuario salvar(Usuario usuario){

       boolean existeEmail = false;

       Optional<Usuario> email = repository.findByEmail(usuario.getEmail());

       if(email.isPresent()){
           if(!email.get().getId().equals(usuario.getId())){
            existeEmail = true;
           }
       }

       if(existeEmail){
         throw new  Exception("E-mail já cadastrado!!");
       }


       return repository.save(usuario);
   }

   public List<Usuario> listarTodos(){
       return  repository.findAll();
   }

    public Optional<Usuario> buscarPorId(Long id){
       return repository.findById(id);
    }

   public void deletar(Long id){
       repository.deleteById(id);
   }

}
