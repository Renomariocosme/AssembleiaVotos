package br.com.desafiosolutis.model.enumereted;

import org.springframework.security.core.GrantedAuthority;

public enum EnumRole implements GrantedAuthority {

     ROLE_ADMIN,
     ROLE_COOPERADO;



    @Override
    public String getAuthority() {
        return null;
    }
}
