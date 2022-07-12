package br.com.desafiosolutis.controller;

import br.com.desafiosolutis.dto.SessaoVotacaoAbrirDTO;
import br.com.desafiosolutis.model.SessaoVotacao;
import br.com.desafiosolutis.service.SessaoVotacaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/v1/sessoes-votacao")
@Api(value = "Sessao Votacao", tags = "Sessao Votacao")
@Slf4j
public class SessaoVotacaoController {


    private final SessaoVotacaoService service;

    @Autowired
    public SessaoVotacaoController(SessaoVotacaoService service){
        this.service = service;
    }



    @ApiOperation(value = "Abrir uma sessão de votação, referente a determinada pauta")
    @PostMapping(value = "/abrir-sessao")
    public ResponseEntity<SessaoVotacao> abrirSessaoVotacao(@Valid @RequestBody SessaoVotacaoAbrirDTO sessaoVotacaoAbrirDTO){
        return new ResponseEntity<>(service.abrirSessaoVotacao(sessaoVotacaoAbrirDTO), HttpStatus.OK);
    }



}
