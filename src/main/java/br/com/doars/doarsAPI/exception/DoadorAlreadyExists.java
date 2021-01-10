package br.com.doars.doarsAPI.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DoadorAlreadyExists extends RuntimeException{

    public DoadorAlreadyExists(String message) {
        super(message);
    }

}
