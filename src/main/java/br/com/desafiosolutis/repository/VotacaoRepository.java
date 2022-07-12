package br.com.desafiosolutis.repository;

import br.com.desafiosolutis.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotacaoRepository extends JpaRepository<Voto, Integer> {


}
