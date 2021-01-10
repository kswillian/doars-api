package br.com.doars.doarsAPI.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.DynamicUpdate;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.*;

@Data
@Entity
@DynamicUpdate
@JsonIdentityInfo( generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id", scope = SolicitacaoDoacao.class)
public class SolicitacaoDoacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "entidade_id")
    private Entidade entidade;

    private String descricao;

    @OneToMany
    @JoinColumn(name = "tipo_sanguineo_id")
    private Set<TipoSanguineo> tipoSanguineosList;

    private Integer doadoresNotificados;

    private Long distancia;

    private LocalDateTime dataRegistro = LocalDateTime.now();

}
