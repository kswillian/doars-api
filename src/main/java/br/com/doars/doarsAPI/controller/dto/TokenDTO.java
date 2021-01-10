package br.com.doars.doarsAPI.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class TokenDTO {

    @ApiModelProperty(value = "Tipo de Token.")
    private String tipo;

    @ApiModelProperty(value = "Token para autenticação.")
    private String token;

    @ApiModelProperty(value = "Data de geração do token.")
    private LocalDateTime dataGeracao;

    @ApiModelProperty(value = "Data de geração do token.")
    private LocalDateTime dataExpiracao;

}
