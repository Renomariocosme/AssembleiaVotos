package br.com.desafiosolutis.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.com.desafiosolutis.model.Pauta;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@ApiModel(value = "PautaDto")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PautaDto {

	@ApiModelProperty(value = "ID Pauta", required = true)
	private Long id;
	private String nomePauta;
	@ApiModelProperty(value = "Descrição sobre o que será voltado")
	@NotBlank(message = "Descrição deve ser preenchido")
	private String descricao;



	public static Pauta toEntity(PautaDto pautaDto){
		return Pauta.builder()
				.id(pautaDto.getId())
				.descricao(pautaDto.getDescricao())
				.build();
	}

	public static PautaDto toDto(Pauta pauta) {
		return PautaDto.builder()
				.id(pauta.getId())
				.descricao(pauta.getDescricao())
				.build();

	}

	public PautaDto(Pauta pauta) {

	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNomePauta() {
		return nomePauta;
	}
	public void setNomePauta(String nomePauta) {
		this.nomePauta = nomePauta;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public static List<PautaDto> converter(List<Pauta> pauta) {
		return pauta.stream().map(PautaDto :: new).collect(Collectors.toList());
	}
	
	
}
