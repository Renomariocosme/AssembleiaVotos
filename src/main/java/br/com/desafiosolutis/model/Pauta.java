package br.com.desafiosolutis.model;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_pauta")
public class Pauta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	@NotNull
	private String nomePauta;
	@NotNull(message = "O campo descricao pode ser nulo")
	@Column(name = "descricao")
	private String descricao;
	@Column(name = "data_votacao")
	private LocalDateTime dataVotacao = LocalDateTime.now();

	@Column(name = "hora_votacao")
	private LocalDateTime horaVotacao = LocalDateTime.now();
	
	public Pauta() {
		
	}
	
	public Pauta(String nomePauta, String descricao, LocalDateTime dataVotacao) {
		this.nomePauta = nomePauta;
		this.descricao = descricao;
		this.dataVotacao = dataVotacao;
		this.horaVotacao = horaVotacao;
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

	public Long getId() {
		
		return id;
	}


	public LocalDateTime getHoraVotacao() {
		return dataVotacao;
	}
	public LocalDateTime setHoraVotacao(){
		return horaVotacao;
	}
}
