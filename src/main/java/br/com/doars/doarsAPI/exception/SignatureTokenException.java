package br.com.doars.doarsAPI.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class SignatureTokenException extends RuntimeException{

    public SignatureTokenException() {
        super("Token inválido.");
    }

}
