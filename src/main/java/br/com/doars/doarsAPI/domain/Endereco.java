package br.com.doars.doarsAPI.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estados_id")
    private Estados estados;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipios_id")
    private Municipios municipios;

    private String logradouro;

    private String numero;

}
