package br.com.desafiosolutis.dto;


import br.com.desafiosolutis.model.Usuario;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;
import javax.validation.constraints.NotNull;

@ApiModel(value = "UsuarioDto")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {

    private Integer id;

    @ApiModelProperty(value = "CPF válido referente ao usuario")
    @CPF(message = "Não é um CPF valido")
    @NotBlank(message = "CPF do usuario deve ser preenchido")
    private String cpfUsuario;

    private String email;

    @NotBlank(message = "Nome do usuario deve ser preenchido")
    private String nome;

    @ApiModelProperty(value = "ID da pauta a ser votada")
    @NotNull(message = "idPauta deve ser preenchido")
    private Integer idPauta;

    public UsuarioDto(Object o, String cpfUsuario, Integer idPauta) {
    }


    public static Usuario toEntity(UsuarioDto dto){
        return Usuario.builder()
                .id(dto.getId())
                .cpfUsuario(dto.getCpfUsuario())
                .idPauta(dto.getIdPauta())
                .build();

    }


}
