package br.com.desafiosolutis.controller;

import java.util.List;

import br.com.desafiosolutis.dto.PautaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.desafiosolutis.model.Pauta;
import br.com.desafiosolutis.repository.PautaRepository;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/pauta")
public class PautaController {

	@Autowired
	private PautaRepository pautaRepository;
	
	@GetMapping
	public List<Pauta> listar(String nomePauta){
		return pautaRepository.findAll();
		
	}
	@PostMapping
	@Transactional
	public ResponseEntity<Pauta> adicionar(@Valid @RequestBody Pauta pautaDto){
		return ResponseEntity.status(HttpStatus.CREATED).body(pautaRepository.save(pautaDto));
	}

}
