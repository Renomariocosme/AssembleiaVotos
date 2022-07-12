package br.com.desafiosolutis.model;

import br.com.desafiosolutis.model.enumereted.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_pauta")
public class Pauta {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nome_pauta")
	private String nomePauta;

	@Column(name = "descricao")
	private String descricao;

	@Enumerated
	private StatusEnum status;

	@Column(name = "total")
	private Integer total;

	@Column(name = "voto_sim")
	private Integer quantidadeVotoSim = 0;

	@Column(name = "voto_não")
	private Integer quantidadeVotoNao = 0;

	@Column(name = "percentualSim")
	private Double percentualSim = 0.0;

	@Column(name = "percentualNao")
	private Double percentualNao = 0.0;




}
