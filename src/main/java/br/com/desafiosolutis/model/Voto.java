package br.com.desafiosolutis.model;

import br.com.desafiosolutis.model.enumereted.VotoOpcao;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_voto")
public class Voto {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Deve seguir o padrão de sim ou não de opções")
    @Column(name = "voto_opcao")
    @Enumerated(EnumType.STRING)
    private VotoOpcao votoOpcao;

    @Column(name = "data_voto")
    private LocalDateTime horaVoto;

    @ManyToOne
    @JoinColumn(name = "id_sessao_votacao")
    private SessaoVotacao sessaoVotacao;

    @ManyToOne
    @JsonIgnoreProperties("listaDeVotos")
    private Usuario usuario;


}
