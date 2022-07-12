package br.com.desafiosolutis.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.desafiosolutis.model.Pauta;
import br.com.desafiosolutis.model.enumereted.StatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

@ApiModel(value = "PautaDto")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PautaDto {

	@ApiModelProperty(value = "ID Pauta", required = true)
	private Integer id;

	private String nomePauta;

	@ApiModelProperty(value = "Descrição sobre o que será voltado")
	@NotBlank(message = "Descrição deve ser preenchido")
	private String descricao;

	@Enumerated
	public StatusEnum status;

	@Column(name = "vencedor")
	private String vencedor;


	public static Pauta toEntity(PautaDto pautaDto){
		return Pauta.builder()
				.id((pautaDto.getId()))
				.descricao(pautaDto.getDescricao())
				.nomePauta(pautaDto.getNomePauta())
				.build();
	}

	public static PautaDto toDto(Pauta pauta) {
		return PautaDto.builder()
				.id(Math.toIntExact(pauta.getId()))
				.descricao(pauta.getDescricao())
				.nomePauta(pauta.getNomePauta())
				.build();

	}

	public  PautaDto(Pauta pauta) {

	}

	public static List<PautaDto> converter(List<Pauta> pauta) {
		return pauta.stream().map(PautaDto :: new).collect(Collectors.toList());
	}



	
}
