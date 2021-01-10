package br.com.doars.doarsAPI.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntidadeCNPJAlreadyExists extends RuntimeException{

    public EntidadeCNPJAlreadyExists(String message) {
        super(message);
    }

}
