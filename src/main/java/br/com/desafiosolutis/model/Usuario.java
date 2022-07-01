package br.com.desafiosolutis.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static br.com.desafiosolutis.model.EnumRole.USUARIO;

@Getter
@Setter
@Entity
@Table(name = "tb_usuario")
public class Usuario {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String cpf;
	private String nome;
	@Column(unique = true)
	private String email;

	@Enumerated(EnumType.STRING)
	private EnumRole tipo = USUARIO;


	public Usuario(String cpf, String email, String nome) {
		this.cpf = cpf;
		this.email = email;
		this.nome = nome;
	}	
	
	public Usuario() {
		
	}

	public Usuario(String cpf, String nome) {
	}

	public Object getId() {

		return id;
	}

	public String getEmail() {
		
		return email;
	}

}
