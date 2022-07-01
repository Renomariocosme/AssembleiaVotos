package br.com.desafiosolutis.dto;


import br.com.desafiosolutis.model.EnumRole;
import br.com.desafiosolutis.model.Usuario;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@ApiModel(value = "UsuarioDto")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDto {

    private Long id;

    @ApiModelProperty(value = "CPF precisa ser válido")
    @CPF(message = "Não é um CPF existante")
    @NotBlank(message = "CPF do associado deve ser preenchido")
    private String CPF;
    private String email;
    private String nome;

    @ApiModelProperty(value = "ID da pauta a ser votada")
    @NotNull(message = "oidPauta deve ser preenchido")
    private Integer oidPauta;

    @Enumerated(EnumType.STRING)
    private EnumRole tipo = EnumRole.USUARIO;


    public static UsuarioDto toEntity(Usuario usuario){
        return UsuarioDto.builder()
                .id((Long) usuario.getId())
                .CPF(usuario.getCpf())
                .oidPauta(usuario.getOidPauta())
                .build();
    }

    private static String getCpf() {
        return getCpf();
    }


    public UsuarioDto(Usuario usuarioDto){
        this.id = id;
        this.CPF = CPF;
        this.email = email;
        this.nome = nome;


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
