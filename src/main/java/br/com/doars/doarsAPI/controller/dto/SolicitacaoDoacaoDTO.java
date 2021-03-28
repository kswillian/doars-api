package br.com.doars.doarsAPI.controller.dto;

import br.com.doars.doarsAPI.domain.SolicitacaoDoacao;
import br.com.doars.doarsAPI.util.Utilidades;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class SolicitacaoDoacaoDTO {

    @ApiModelProperty(value = "Identificador interno da solicitação.")
    private Long id;

    @ApiModelProperty(value = "Descrição da Solicitação")
    private String descricao;

    @ApiModelProperty(value = "Entidade Solicitante")
    private EntidadeSimpleDTO entidade;

    @ApiModelProperty(value = "Tipo(s) sanguineo(s) solicitado(s)")
    private List<TipoSanguineoDTO> tipoSanguineosList;

    @ApiModelProperty(value = "Número de doadores notificados")
    private Integer doadoresNotificados;

    @ApiModelProperty(value = "Raio de distancia para o disparo de notificações")
    private Long distancia;

    @ApiModelProperty(value = "Data de registro da solicitação de doação de sangue.")
    private String dataRegistro;

    @ApiModelProperty(value = "Status da solicitacao de doação.")
    private Boolean ativo;

    public SolicitacaoDoacaoDTO(SolicitacaoDoacao solicitacaoDoacao) {
        this.id = solicitacaoDoacao.getId();
        this.ativo = solicitacaoDoacao.getAtivo();
        this.distancia = solicitacaoDoacao.getDistancia();
        this.descricao = solicitacaoDoacao.getDescricao();
        this.entidade = new EntidadeSimpleDTO(solicitacaoDoacao.getEntidade());
        this.tipoSanguineosList = TipoSanguineoDTO.converterMotelToDTO(solicitacaoDoacao.getTipoSanguineosList());
        this.doadoresNotificados = solicitacaoDoacao.getDoadoresNotificados();
        this.dataRegistro = new Utilidades().formatDate(solicitacaoDoacao.getDataRegistro());
    }

    public static List<SolicitacaoDoacaoDTO> converterMotelToDTO(List<SolicitacaoDoacao> solicitacoesDoacao){
        return solicitacoesDoacao.stream().map(SolicitacaoDoacaoDTO::new).collect(Collectors.toList());
    }

    public static Page<SolicitacaoDoacaoDTO> converterMotelToDTO(Page<SolicitacaoDoacao> solicitacoesDoacao){
        return solicitacoesDoacao.map(SolicitacaoDoacaoDTO::new);
    }

}
