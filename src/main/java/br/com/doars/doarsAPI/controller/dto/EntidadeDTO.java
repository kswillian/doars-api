package br.com.doars.doarsAPI.controller.dto;

import br.com.doars.doarsAPI.domain.Contato;
import br.com.doars.doarsAPI.domain.Entidade;
import br.com.doars.doarsAPI.domain.enuns.DiasSemana;
import br.com.doars.doarsAPI.domain.enuns.TipoEntidade;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class EntidadeDTO {

    @ApiModelProperty(value = "Identificador interno da entidade.")
    private Long id;

    @ApiModelProperty(value = "Nome da entidade.")
    private String nome;

    @ApiModelProperty(value = "CNPJ da entidade.")
    private String cnpj;

    @ApiModelProperty(value = "Tipo de entidade.")
    private TipoEntidade tipoEntidade;

    @ApiModelProperty(value = "Texto de descrição sobre a entidade.")
    private String descricao;

    @ApiModelProperty(value = "Dias de funcionamento da entiade.")
    private Set<DiasSemana> diasSemanaList;

    @ApiModelProperty(value = "Horário inicial de funcionamento da entidade.")
    private LocalDateTime horaInicialFuncionamento;

    @ApiModelProperty(value = "Horário final de funcionamento da entidade.")
    private LocalDateTime horaFinalFuncionamento;

    private EnderecoDTO endereco;

    private Contato contato;

    @ApiModelProperty(value = "Data de registro da entidade.")
    private LocalDateTime dataRegistro;

    public EntidadeDTO(Entidade entidade) {
        this.id = entidade.getId();
        this.nome = entidade.getNome();
        this.cnpj = entidade.getCnpj();
        this.tipoEntidade = entidade.getTipoEntidade();
        this.descricao = entidade.getDescricao();
        this.diasSemanaList = entidade.getDiasSemanaList();
        this.horaInicialFuncionamento = entidade.getHoraInicialFuncionamento();
        this.horaFinalFuncionamento = entidade.getHoraFinalFuncionamento();
        this.endereco = new EnderecoDTO(entidade.getEndereco());
        this.contato = entidade.getContato();
        this.dataRegistro = entidade.getDataRegistro();
    }

    public static List<EntidadeDTO> converterMotelToDTO(List<Entidade> entidades){
        return entidades.stream().map(EntidadeDTO::new).collect(Collectors.toList());
    }

    public static Page<EntidadeDTO> converterMotelToDTO(Page<Entidade> entidades){
        return entidades.map(EntidadeDTO::new);
    }

}
