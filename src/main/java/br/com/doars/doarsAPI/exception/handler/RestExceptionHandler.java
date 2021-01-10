package br.com.doars.doarsAPI.exception.handler;

import br.com.doars.doarsAPI.exception.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.validation.FieldError;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DoadorAlreadyExists.class)
    public ResponseEntity<DoadorAlreadyExists> handleDoadorAlreadyExists(DoadorAlreadyExists e){
        return new ResponseEntity(
                ExceptionDetails.builder()
                        .titulo("Bad Request")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .detalhes(e.getClass().getName())
                        .mensagem(e.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(EntidadeEmailAlreadyExists.class)
    public ResponseEntity<EntidadeEmailAlreadyExists> handleEntidadeEmailAlreadyExists(EntidadeEmailAlreadyExists e){
        return new ResponseEntity(
                ExceptionDetails.builder()
                        .titulo("Bad Request")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .detalhes(e.getClass().getName())
                        .mensagem(e.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(EntidadeCNPJAlreadyExists.class)
    public ResponseEntity<EntidadeCNPJAlreadyExists> handleEntidadeCNPJAlreadyExists(EntidadeCNPJAlreadyExists e){
        return new ResponseEntity(
                ExceptionDetails.builder()
                        .titulo("Bad Request")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .detalhes(e.getClass().getName())
                        .mensagem(e.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(SignatureTokenException.class)
    public ResponseEntity<SignatureTokenException> handleSignatureTokenException(SignatureTokenException e){
        return new ResponseEntity(
                ExceptionDetails.builder()
                        .titulo("Bad Request")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .detalhes(e.getClass().getName())
                        .mensagem(e.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<AuthException> handleAuthException(AuthException e){
        return new ResponseEntity(
                ExceptionDetails.builder()
                        .titulo("Bad Request")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .detalhes(e.getClass().getName())
                        .mensagem(e.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(UserEnabledException.class)
    public ResponseEntity<UserEnabledException> handleUserEnabledException(UserEnabledException e){
        return new ResponseEntity(
                ExceptionDetails.builder()
                        .titulo("Bad Request")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .detalhes(e.getClass().getName())
                        .mensagem(e.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ViolationException.class)
    public ResponseEntity<ViolationException> handleDataIntegrityViolationException(ViolationException e){
        return new ResponseEntity(
                ExceptionDetails.builder()
                        .titulo("Bad Request")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .detalhes(e.getClass().getName())
                        .mensagem(e.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResourceNotFoundException> handleResourceNotFound(ResourceNotFoundException e){
        return new ResponseEntity(
                ExceptionDetails.builder()
                        .titulo("Not found")
                        .status(HttpStatus.NOT_FOUND.value())
                        .detalhes(e.getClass().getName())
                        .mensagem(e.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.NOT_FOUND
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

        List<String> fields = new ArrayList<>();
        List<String> fieldsMessage = new ArrayList<>();

        for(FieldError fieldError: fieldErrors){
            fields.add(fieldError.getField());
            fieldsMessage.add(fieldError.getDefaultMessage().toString());
        }


        return new ResponseEntity<>(
                ValidationExceptionDetails.builder()
                        .titulo("Bad Request")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .detalhes(e.getClass().getName())
                        .mensagem(e.getMessage())
                        .campos(fields)
                        .mensagemCampos(fieldsMessage)
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.BAD_REQUEST
        );

    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .titulo("Bad Request")
                .status(status.value())
                .detalhes(ex.getClass().getName())
                .mensagem(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity(exceptionDetails, headers, status);
    }

}
