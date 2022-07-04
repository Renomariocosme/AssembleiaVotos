package br.com.desafiosolutis.controller;

import br.com.desafiosolutis.dto.ResultadoDTO;
import br.com.desafiosolutis.dto.VotoDTO;
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
    public ResponseEntity<String> votar(@Valid @RequestBody VotoDTO dto){
        LOGGER.debug("Usuario votando usuario = {}", dto.getCpfUsuario());
        String mensagem = service.votar(dto);
        LOGGER.debug("Voto usuario finalizado usuario = {}", dto.getCpfUsuario());
        return ResponseEntity.status(HttpStatus.CREATED).body(mensagem);
    }

    @ApiOperation(value = "Resultado da votacao, somente após a finalização da sessao de votação")
    @GetMapping(value = "/resultado/{idPauta}/{idSessaoVotacao}")
    public ResponseEntity<ResultadoDTO> resultadoVotocao(@PathVariable("idPauta") Integer idPauta, @PathVariable ("idSessaoVotacao") Integer idSessaoVotacao){
        LOGGER.debug("Buscando resultado da votacao idPauta = {}. idSessaoVotacao = {}", idPauta, idSessaoVotacao);
        ResultadoDTO dto = service.buscarDadosResultadoVotacao(idPauta, idSessaoVotacao);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}
