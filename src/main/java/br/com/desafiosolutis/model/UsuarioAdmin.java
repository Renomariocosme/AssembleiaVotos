package br.com.desafiosolutis.model;


import br.com.desafiosolutis.model.enumereted.EnumRole;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class UsuarioAdmin {

	
	private String usuario;
	
	private	String senha;
	
	private	String token;

	@Enumerated(EnumType.STRING)
	private EnumRole tipo = EnumRole.ADMIN;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}
