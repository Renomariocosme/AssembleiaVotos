package br.com.desafiosolutis.dto;

import br.com.desafiosolutis.model.Pauta;
import br.com.desafiosolutis.model.enumereted.StatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@ApiModel(value = "PautaDTOResposta")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PautaRespostaDTO {

    @ApiModelProperty(value = "ID Pauta", required = true)
    private Integer id;

    private String nomePauta;

    @ApiModelProperty(value = "Descrição sobre o que será voltado")
    @NotBlank(message = "Descrição deve ser preenchido")
    private String descricao;

    private String vencedor;

    private StatusEnum status;

    private Integer total = 0;

    private Integer quantidadeVotoSim = 0;

    private Integer quantidadeVotoNao = 0;

    private Double percentualSim = 0.00;

    private Double percentualNao = 0.00;



    public static PautaRespostaDTO toDto(PautaDto pautaDto){
        return PautaRespostaDTO.builder()
                .id((pautaDto.getId()))
                .descricao(pautaDto.getDescricao())
                .nomePauta(pautaDto.getNomePauta())
                .vencedor(pautaDto.getVencedor())
                .build();
    }

    public static PautaRespostaDTO converteParaDto(Pauta pauta){

        return PautaRespostaDTO.builder()
                .id(pauta.getId())
                .nomePauta(pauta.getNomePauta())
                .descricao(pauta.getDescricao())
                .status(pauta.getStatus())
                .vencedor(String.valueOf(pauta.getTotal()))
                .quantidadeVotoSim(pauta.getQuantidadeVotoSim())
                .quantidadeVotoNao(pauta.getQuantidadeVotoNao())
                .percentualSim(pauta.getPercentualSim())
                .percentualNao(pauta.getPercentualNao()).build();
    }


}
