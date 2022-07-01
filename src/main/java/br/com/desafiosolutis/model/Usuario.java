package br.com.desafiosolutis.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static br.com.desafiosolutis.model.EnumRole.USUARIO;

@Getter
@Setter
@Entity
@Table(name = "tbl_usuario")
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

	@Id
	@Column(name = "oid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "cpf_eleitor")
	private String cpf;
	@Column(name = "cpf")
	private String nome;
	@Column(unique = true)
	private String email;

	@Enumerated(EnumType.STRING)
	private EnumRole tipo = USUARIO;

	@Column(name = "oid_pauta")
	private Integer oidPauta;


	public Usuario(String cpf, String email, String nome ) {
		this.cpf = cpf;
		this.email = email;
		this.nome = nome;
	}

	public Usuario(String cpf, String nome) {
	}

	public static Object builder() {
		return builder();
	}

	public Object getId() {

		return id;
	}

	public String getEmail() {
		
		return email;
	}
	public String getCpf() {

		return cpf;
	}

}
