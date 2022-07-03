package br.com.desafiosolutis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.desafiosolutis.model.Pauta;

public interface PautaRepository extends JpaRepository<Pauta, Integer> {



	List<Pauta> findByNomePauta(String nomePauta);

	boolean existsById(Integer id);
}
