package br.com.desafiosolutis.repository;

import br.com.desafiosolutis.model.Votacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotacaoRepository extends JpaRepository<Votacao, Integer> {

    Integer countVotacaoByIdPautaAndIdSessaoVotacaoAndVoto(Integer idPauta, Integer idSessaoVotacao, Boolean voto);
}
