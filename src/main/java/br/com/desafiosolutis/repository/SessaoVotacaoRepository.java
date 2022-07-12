package br.com.desafiosolutis.repository;

import br.com.desafiosolutis.model.Pauta;
import br.com.desafiosolutis.model.SessaoVotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessaoVotacaoRepository extends JpaRepository<SessaoVotacao, Integer> {


  //  Boolean existsByIdAndAtiva(Integer id, Boolean ativa);

    boolean existsById(Integer id);

    Optional<SessaoVotacao> findById(Integer id);

    boolean existsByPautaId(Integer idPauta);

}

