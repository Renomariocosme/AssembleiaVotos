package br.com.desafiosolutis.repository;

import java.util.List;
import java.util.Optional;

import br.com.desafiosolutis.model.SessaoVotacao;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.desafiosolutis.model.Pauta;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Integer> {

	List<Pauta> findByNomePauta(String nomePauta);

	boolean existsById(Integer id);

	//Optional<SessaoVotacao> findByPauta(Pauta pauta);

	Optional<Pauta> findById(Integer id);
}
