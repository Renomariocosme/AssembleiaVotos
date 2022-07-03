package br.com.desafiosolutis.repository;

import br.com.desafiosolutis.model.SessaoVotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SessaoVotacaoRepository extends JpaRepository<SessaoVotacao, Integer> {

    @Query("select s from SessaoVotacao s where s.ativa=:ativo")
    List<SessaoVotacao> buscarTodasSessoesEmAndamento(Boolean ativo);

    Boolean existsByIdAndAndAtiva(Integer id, Boolean ativa);

    boolean existsById(Integer id);

}

