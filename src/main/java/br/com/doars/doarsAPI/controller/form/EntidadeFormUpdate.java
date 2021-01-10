package br.com.doars.doarsAPI.controller.form;

import br.com.doars.doarsAPI.domain.enuns.DiasSemana;
import br.com.doars.doarsAPI.domain.enuns.TipoEntidade;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class EntidadeFormUpdate {

    @NotNull
    @ApiModelProperty(value = "Indentificador interno da entidade.")
    private Long id;

    @NotNull
    @ApiModelProperty(value = "Nome da entidade.")
    private String nome;

    @NotNull
    @ApiModelProperty(value = "CNPJ da entidade.")
    private String cnpj;

    @NotNull
    @ApiModelProperty(value = "Tipo de entidade.")
    private TipoEntidade tipoEntidade;

    @NotNull
    @ApiModelProperty(value = "Texto de descrição sobre a entidade.")
    private String descricao;

    @NotNull
    @ApiModelProperty(value = "Dias de funcionamento da entiade.")
    private Set<DiasSemana> diasSemanaList;

    @ApiModelProperty(value = "Horário inicial de funcionamento da entidade.")
    private LocalDateTime horaInicialFuncionamento;

    @ApiModelProperty(value = "Horário final de funcionamento da entidade.")
    private LocalDateTime horaFinalFuncionamento;

    @Valid
    private UsuarioForm usuario;

    @Valid
    private EnderecoForm endereco;

    @Valid
    private ContatoForm contato;

}
