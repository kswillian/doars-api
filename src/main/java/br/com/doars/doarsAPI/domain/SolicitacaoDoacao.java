package br.com.doars.doarsAPI.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;

@Data
@Entity
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
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

    @ManyToMany
    private List<TipoSanguineo> tipoSanguineosList;

    @ManyToMany
    private List<Doador> doadores;

    private Integer doadoresNotificados;

    private Long distancia;

    private LocalDateTime dataRegistro = LocalDateTime.now();

    private Boolean ativo = true;

}
