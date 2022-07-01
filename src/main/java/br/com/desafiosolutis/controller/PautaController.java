package br.com.desafiosolutis.controller;

import java.util.List;

import br.com.desafiosolutis.dto.PautaDto;
import br.com.desafiosolutis.service.PautaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.desafiosolutis.model.Pauta;
import br.com.desafiosolutis.repository.PautaRepository;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/pautas")
@Api(value = "Pauta", tags = "Pauta")
public class PautaController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PautaController.class);

	private final PautaService service;

	@Autowired
	private PautaRepository repository;

	@Autowired
	public PautaController(PautaService service) {
		this.service = service;
	}

	@ApiOperation(value ="Buscar a pauta utilizando o ID")
	@GetMapping(value = "/{id}")
	public ResponseEntity<PautaDto> buscarPautaPeloOId(@PathVariable("id") Long id){
		LOGGER.debug("Buscando a pauta pelo o ID = {id}", id);
		return ResponseEntity.ok(service.buscarPautaPeloId(id));


	}
	@ApiOperation(value ="Cricar uma pauta para ser votada")
	@PostMapping
	public ResponseEntity<PautaDto> salvarPauta(@Valid @RequestBody PautaDto pautaDto){
		LOGGER.debug("Salvando a pauta = {id}", pautaDto.getDescricao());
		pautaDto = service.salvar(pautaDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(pautaDto);
	}



}
