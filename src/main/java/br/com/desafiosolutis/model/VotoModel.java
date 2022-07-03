package br.com.desafiosolutis.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"sessaoModel"})
@Entity
@Table(name = "sessao_voto")
public class VotoModel implements Serializable {

    @NotNull
    @Id
    private String cpfEleitor;

    @NotNull(message = "O voto deve seguir o padrão: SIM/NÃO")
    @Column(name = "opcao_voto")
    @Enumerated(EnumType.STRING)
    private OpcaoVoto opcaoVoto;

    @Column(name = "data")
    private LocalDateTime dataHora;




}
