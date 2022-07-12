package br.com.desafiosolutis.model;

import br.com.desafiosolutis.model.enumereted.StatusEnum;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_sessao_votacao")
public class SessaoVotacao  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "data_hora_inicio")
    private LocalDateTime dataInicio;

    @Column(name = "data_hora_fim")
    private LocalDateTime dataFim;


    @OneToOne
    @JoinColumn(name = "id_pauta")
    private Pauta pauta;

    @Column(name = "duracao")
    private Integer duracao = 0;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "sessaoVotacao", cascade = CascadeType.ALL)
    private List<Voto> voto = new ArrayList<>();


    public boolean sessaoExpirada(){
         return this.getDataFim() != null && this.getDataFim().isBefore(LocalDateTime.now());
    }

    public boolean Aberta(){
        return getPauta().getStatus().equals(StatusEnum.valueOf("ABERTO"));
    }
}
