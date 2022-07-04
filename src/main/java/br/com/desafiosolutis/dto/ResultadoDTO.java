package br.com.desafiosolutis.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "ResultadoDTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultadoDTO {

    @ApiModelProperty(value = "Objeto PautaDto com os dados do que foi votado")
    private PautaDto pautaDto;
    @ApiModelProperty(value = "Objeto VotacaoDTO com os dados do resultado da votação")
    private VotacaoDTO votacaoDTO;
}
