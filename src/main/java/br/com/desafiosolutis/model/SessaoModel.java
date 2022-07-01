package br.com.desafiosolutis.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(exclude = {"pautas"})
@Table(name = "sessao_de_votos")
public class SessaoModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "abertura_sessao")
    private LocalDateTime dataOpen;

    @Column(name = "fechamento_sessao")
    private LocalDateTime dataClose;

    @OneToOne
    @JoinColumn(name = "id_pauta")
    private Pauta pauta;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "sessaoModel", cascade = CascadeType.ALL)
    private Collection<VotoModel> votos = new LinkedHashSet<VotoModel>();





}
