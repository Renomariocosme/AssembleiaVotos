package br.com.desafiosolutis.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

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

    @ApiModelProperty(value = "Voto")
    @NotNull(message = "Voto deve ser preenchido")
    private Boolean voto;

    @ApiModelProperty(value = "CPF valido do eleitor")
    @CPF(message = "Não é um CPF valido")
    @NotBlank(message = "cpf do usuario deve ser preenchido")
    private String cpfUsuario;
}
