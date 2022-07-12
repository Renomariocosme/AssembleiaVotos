package br.com.desafiosolutis.dto;

import br.com.desafiosolutis.model.enumereted.VotoOpcao;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel(value = "VotoDTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VotoDTO {

    @ApiModelProperty(value = "ID da pauta da votação aberta")
    @NotNull(message = "oidPauta deve ser preenchido")
    private Integer idPauta;

    @ApiModelProperty(value = "ID da sessão de votação aberta")
    @NotNull(message = "idSessaoVotacao deve ser preenchido")
    private Integer idSessaoVotacao;

    @Enumerated(EnumType.STRING)
    private VotoOpcao votoOpcao;

    @ApiModelProperty(value = "CPF valido do eleitor")
    @NotBlank(message = "cpf do usuario deve ser preenchido")
    private String cpfUsuario;
}
