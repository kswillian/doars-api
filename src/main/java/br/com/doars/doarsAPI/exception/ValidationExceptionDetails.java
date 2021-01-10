package br.com.doars.doarsAPI.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class ValidationExceptionDetails extends ExceptionDetails{

    private List<String> campos;
    private List<String> mensagemCampos;

}
