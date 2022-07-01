package br.com.desafiosolutis.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.com.desafiosolutis.model.Pauta;

import javax.persistence.Column;

public class PautaDto {
	
	private Long id;
	private String nomePauta;
	private String descricao;
	@Column(name = "data_votacao")
	private LocalDateTime dataVotacao = LocalDateTime.now();

	@Column(name = "hora_votacao")
	private LocalDateTime horaVotacao = LocalDateTime.now();

	public PautaDto(Pauta pauta) {
		this.id = pauta.getId();
		this.nomePauta = pauta.getNomePauta();
		this.descricao = pauta.getDescricao();
		this.dataVotacao = pauta.getDataVotacao();
		this.horaVotacao = pauta.getHoraVotacao();
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
	public LocalDateTime getDataVotacao() {
		return dataVotacao;
	}
	public void setDataVotacao(LocalDateTime dataVotacao) {
		this.dataVotacao = dataVotacao;
	}

	public LocalDateTime getHoraVotacao() {
		return horaVotacao;
	}

	public void setHoraVotacao(LocalDateTime horaVotacao) {
		this.horaVotacao = horaVotacao;
	}

	public static List<PautaDto> converter(List<Pauta> pauta) {
		return pauta.stream().map(PautaDto :: new).collect(Collectors.toList());
	}
	
	
}
