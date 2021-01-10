package br.com.doars.doarsAPI.domain;

import br.com.doars.doarsAPI.domain.enuns.DiasSemana;
import br.com.doars.doarsAPI.domain.enuns.TipoEntidade;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;;
import org.hibernate.annotations.DynamicUpdate;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.*;

@Data
@Entity
@DynamicUpdate
@JsonIdentityInfo( generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id", scope = Entidade.class)
public class Entidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String cnpj;

    private TipoEntidade tipoEntidade;

    @Lob
    private String descricao;

    @ElementCollection(fetch = FetchType.LAZY, targetClass = DiasSemana.class)
    @Enumerated(EnumType.STRING)
    @Column(name = "diaSemana", length = 7)
    @JoinTable(name = "entidade_diasSemana")
    private Set<DiasSemana> diasSemanaList;

    private LocalDateTime horaInicialFuncionamento;

    private LocalDateTime horaFinalFuncionamento;

    @Embedded
    private Endereco endereco;

    @Embedded
    private Contato contato;

    private LocalDateTime dataRegistro = LocalDateTime.now();

}
