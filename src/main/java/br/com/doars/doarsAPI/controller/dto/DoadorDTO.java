package br.com.doars.doarsAPI.controller.dto;

import br.com.doars.doarsAPI.domain.Contato;
import br.com.doars.doarsAPI.domain.Doador;
import br.com.doars.doarsAPI.domain.enuns.Sexo;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class DoadorDTO {

    @ApiModelProperty(value = "Identificador interno do doador.")
    private Long id;

    @ApiModelProperty(value = "Nome do doador.")
    private String nome;

    @ApiModelProperty(value = "Sexo do doador.")
    private Sexo sexo;

    private EnderecoDTO endereco;

    private Contato contato;

    private TipoSanguineoDTO tipoSanguineo;

    @ApiModelProperty(value = "Data de registro do doador.")
    private LocalDateTime dataRegistro;

    public DoadorDTO(Doador doador) {
        this.id = doador.getId();
        this.nome = doador.getNome();
        this.sexo = doador.getSexo();
        this.endereco = new EnderecoDTO(doador.getEndereco());
        this.contato = doador.getContato();
        this.tipoSanguineo = new TipoSanguineoDTO(doador.getTipoSanguineo());
        this.dataRegistro = doador.getDataRegistro();
    }

    public static List<DoadorDTO> converterMotelToDTO(List<Doador> doadores){
        return doadores.stream().map(DoadorDTO::new).collect(Collectors.toList());
    }

    public static Page<DoadorDTO> converterMotelToDTO(Page<Doador> doadores){
        return doadores.map(DoadorDTO::new);
    }

}
