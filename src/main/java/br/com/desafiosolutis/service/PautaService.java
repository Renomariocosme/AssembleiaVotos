package br.com.desafiosolutis.service;


import br.com.desafiosolutis.advice.NotFoundException;
import br.com.desafiosolutis.dto.PautaDto;
import br.com.desafiosolutis.model.Pauta;
import br.com.desafiosolutis.repository.PautaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class PautaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PautaService.class);

    @Autowired
    private final PautaRepository repository;


    public PautaService(PautaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public PautaDto salvar(PautaDto pautaDto){
        return PautaDto.toDto(repository.save(PautaDto.toEntity(pautaDto)));
    }


    public PautaDto buscarPautaPeloId(Long id){
        Optional<Pauta> pautaOptional = repository.findById(id);

        if (!pautaOptional.isPresent()){
            LOGGER.error("Pauta não localize para o id {}", id);
            throw new NotFoundException("Pauta não localizada pelo o id" + id);
        }

        return PautaDto.toDto(pautaOptional.get());
    }

    public boolean isPautaValida(Long id){
        if(repository.existsById(id)){
            return Boolean.TRUE;
        }else{
            LOGGER.error("Pauta não localizada para o id em {}", id);
            throw new NotFoundException("Pauta não localizada para o id" + id);
        }
    }
}
