package br.com.doars.doarsAPI.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ViolationException extends RuntimeException{

    public ViolationException() {
        super("Este item não pode ser excluído pois está vinculado a outro(s) registro(s)");
    }

}
