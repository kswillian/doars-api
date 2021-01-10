package br.com.doars.doarsAPI.exception;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class ExceptionDetails {

    protected String titulo;
    protected int status;
    protected String detalhes;
    protected LocalDateTime timestamp;
    protected String mensagem;

}
