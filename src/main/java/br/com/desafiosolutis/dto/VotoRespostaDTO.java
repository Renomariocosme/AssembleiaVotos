package br.com.desafiosolutis.dto;

import br.com.desafiosolutis.model.enumereted.VotoOpcao;
import lombok.Data;

import javax.persistence.Enumerated;

@Data
public class VotoRespostaDTO {

    private Integer sessaoVotacaoId;

    @Enumerated
    private VotoOpcao votoOpcao;

    private String cpfUsuario;



}
