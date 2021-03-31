package br.com.doars.doarsAPI.controller;

import br.com.doars.doarsAPI.controller.dto.TokenDTO;
import br.com.doars.doarsAPI.controller.form.LoginForm;
import br.com.doars.doarsAPI.service.AutenticacaoService;
import br.com.doars.doarsAPI.service.TokenJJWTService;
import br.com.doars.doarsAPI.controller.form.AutenticacaoUsuarioForm;
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
@RequestMapping("api/v1/usuarios")
@Api(tags = "Autenticação (Usuário)")
public class AutenticacaoController {

    private AutenticacaoService autenticacaoService;
    private TokenJJWTService tokenService;

    @PostMapping("/autenticar")
    @ApiOperation(value = "Realiza a autenticação do usuário cadastrado.")
    public ResponseEntity<TokenDTO> authenticate(@RequestBody @Valid LoginForm loginForm){
        return ResponseEntity.status(HttpStatus.OK).body(
                autenticacaoService.authenticateUsuario(loginForm, tokenService));
    }

    @PostMapping("/ativar")
    @ApiOperation(value = "Realiza a ativação do usuário cadastrado.")
    public ResponseEntity activate(@RequestBody @Valid AutenticacaoUsuarioForm autenticacaoUsuarioForm){
        autenticacaoService.activateUsuario(autenticacaoUsuarioForm);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
