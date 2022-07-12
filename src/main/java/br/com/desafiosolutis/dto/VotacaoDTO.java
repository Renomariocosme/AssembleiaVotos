package br.com.desafiosolutis.dto;

import br.com.desafiosolutis.model.Voto;
import br.com.desafiosolutis.model.enumereted.VotoOpcao;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@ApiModel(value = "VotacaoDTO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VotacaoDTO {


    @ApiModelProperty(value = "ID da votação aberta")
    private Integer id;

    @ApiModelProperty(value = "ID da pauta de votação aberta")
    private Integer idPauta;

    @ApiModelProperty(value = "ID da sessão de votação aberta")
    private Integer idSessaoVotacao;

    @Enumerated(EnumType.STRING)
    private VotoOpcao votoOpcao;

    @ApiModelProperty(value = "Quantidade de votos positivos")
    private Integer quantidadeVotosSim = 0;

    @ApiModelProperty(value = "Quantidade de votos negativos")
    private Integer quantidadeVotosNao = 0;

    public static Voto  toEntity(VotacaoDTO dto){
        return Voto.builder()
                .id(dto.getId())
                .votoOpcao(dto.votoOpcao)
                .build();
    }
}
