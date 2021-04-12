package br.com.doars.doarsAPI.controller;

import br.com.doars.doarsAPI.controller.dto.DoadorDTO;
import br.com.doars.doarsAPI.controller.form.DoadorForm;
import br.com.doars.doarsAPI.controller.form.EmailForm;
import br.com.doars.doarsAPI.service.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/email")
@Api(tags = "Contato")
public class EmailController {

    private EmailService emailService;

    @PostMapping
    @ApiOperation(value = "Registra um novo doador em nossa base de dados.")
    public ResponseEntity<EmailForm> register(@RequestBody @Valid EmailForm emailForm){
        emailService.sendSimpleEmailQuestion(emailForm);
        return ResponseEntity.status(HttpStatus.OK).body(emailForm);
    }

}
