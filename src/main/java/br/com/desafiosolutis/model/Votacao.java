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
@Entity
@Table(name = "tbl_votacao")
public class Votacao {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "voto")
    private Boolean voto;

    @Column(name = "id_pauta")
    private Integer idPauta;

    @Column(name = "id_sessao_votacao")
    private Integer idSessaoVotacao;




}
