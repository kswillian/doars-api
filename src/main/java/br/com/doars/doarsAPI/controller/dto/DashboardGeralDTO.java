package br.com.doars.doarsAPI.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardGeralDTO {

    private Long entidades;

    private Long doadores;

    private Long solicitacoes;

    private String dataAtualizacao;

}
