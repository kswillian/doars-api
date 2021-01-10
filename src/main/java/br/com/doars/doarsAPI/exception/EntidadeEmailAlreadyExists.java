package br.com.doars.doarsAPI.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntidadeEmailAlreadyExists extends RuntimeException{

    public EntidadeEmailAlreadyExists(String message) {
        super(message);
    }

}
