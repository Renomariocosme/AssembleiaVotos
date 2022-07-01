package br.com.desafiosolutis.dto;

import br.com.desafiosolutis.model.EnumRole;
import br.com.desafiosolutis.model.Usuario;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class UsuarioDto {

    private Long id;
    private String CPF;
    private String email;

    private String nome;

    @Enumerated(EnumType.STRING)
    private EnumRole tipo = EnumRole.USUARIO;

    public UsuarioDto(Usuario usuarioDto){
        this.id = id;
        this.CPF = CPF;
        this.email = email;
        this.nome = nome;
        this.tipo.compareTo(tipo);

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

}
