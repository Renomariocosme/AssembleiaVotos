package br.com.desafiosolutis.controller.form;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginForm {

    private String email;
    private String senha;

    public UsernamePasswordAuthenticationToken converter(){
        return new UsernamePasswordAuthenticationToken(email, senha);
    }
}
