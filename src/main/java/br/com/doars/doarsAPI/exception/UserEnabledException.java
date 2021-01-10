package br.com.doars.doarsAPI.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserEnabledException extends RuntimeException{

    public UserEnabledException() {
        super("Usuário não habilitado, favor verificar o seu email.");
    }

}
