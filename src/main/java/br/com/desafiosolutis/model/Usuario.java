package br.com.desafiosolutis.model;


import lombok.*;

import javax.persistence.*;

import static br.com.desafiosolutis.model.EnumRole.USUARIO;

@Data
@Builder
@Entity
@Table(name = "tbl_usuario")
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "cpf_usuario")
	private String cpfUsuario;

	@Column(name = "nome_usuario")
	private String nome;

	@Column(unique = true)
	private String email;

	@Column(name = "id_pauta")
	private Integer idPauta;


}
