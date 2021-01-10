package br.com.doars.doarsAPI.domain;

import br.com.doars.doarsAPI.domain.enuns.Sexo;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.DynamicUpdate;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@DynamicUpdate
@JsonIdentityInfo( generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id", scope = Doador.class)
public class Doador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Sexo sexo;

    @Embedded
    private Endereco endereco;

    @Embedded
    private Contato contato;

    @ManyToOne
    @JoinColumn(name = "id_tipoSanguineo")
    private TipoSanguineo tipoSanguineo;

    private LocalDateTime dataRegistro = LocalDateTime.now();

}
