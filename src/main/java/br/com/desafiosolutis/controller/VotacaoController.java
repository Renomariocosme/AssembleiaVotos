package br.com.desafiosolutis.controller;

import br.com.desafiosolutis.dto.ResultadoDTO;
import br.com.desafiosolutis.dto.VotoDTO;
import br.com.desafiosolutis.dto.VotoRespostaDTO;
import br.com.desafiosolutis.model.Voto;
import br.com.desafiosolutis.service.VotacaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/votacoes")
@Api(value = "Votacao", tags = "Votacao")
public class VotacaoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VotacaoController.class);

    private final VotacaoService service;

    @Autowired
    public VotacaoController(VotacaoService service){
        this.service = service;
    }

    @ApiOperation(value = "Votar em determinada pauta, enquanto a sessão de votação estiver aberta")
    @PostMapping(value = "/votar")
    public ResponseEntity<Voto> votar(@Valid @RequestBody VotoRespostaDTO dto){
        LOGGER.info("Usuario votando usuario = {}", dto.getCpfUsuario());

        LOGGER.info("Voto usuario finalizado usuario = {}", dto.getCpfUsuario());
        return  new  ResponseEntity<>(service.votando(dto), HttpStatus.CREATED);
    }

}
