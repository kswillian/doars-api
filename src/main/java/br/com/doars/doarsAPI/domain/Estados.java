package br.com.doars.doarsAPI.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.DynamicUpdate;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Data;

import java.math.BigDecimal;
import javax.persistence.*;
import java.util.List;

@Data
@Entity
@ToString
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo( generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id", scope = Estados.class)
public class Estados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String sigla;

    @Column(precision = 19, scale = 10)
    private BigDecimal latitude;

    @Column(precision = 19, scale = 10)
    private BigDecimal longitude;

    @ManyToOne
    @JoinColumn(name="pais_id")
    private Pais pais;

    @OneToMany(mappedBy = "estados", targetEntity = Municipios.class,
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Municipios> municipios;

}
