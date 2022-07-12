package br.com.desafiosolutis.service;


import br.com.desafiosolutis.advice.NotFoundException;
import br.com.desafiosolutis.dto.PautaDto;
import br.com.desafiosolutis.dto.PautaRespostaDTO;
import br.com.desafiosolutis.model.Pauta;
import br.com.desafiosolutis.model.SessaoVotacao;
import br.com.desafiosolutis.model.Voto;
import br.com.desafiosolutis.model.enumereted.StatusEnum;
import br.com.desafiosolutis.repository.PautaRepository;
import br.com.desafiosolutis.repository.SessaoVotacaoRepository;
import br.com.desafiosolutis.repository.VotacaoRepository;
import org.apache.commons.math3.util.Precision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class PautaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PautaService.class);

    @Autowired
    private final PautaRepository repository;
    private final SessaoVotacaoRepository sessaoVotacaoRepository;
    private final VotacaoRepository votacaoRepository;



    public Pauta salvarPauta(Pauta pauta){
        repository.save(pauta);
        return pauta;
    }


    public PautaRespostaDTO buscarPautaPeloId(Integer id){
        Optional<Pauta>  pautaOptional = repository.findById(id);

        if (!pautaOptional.isPresent()){
            LOGGER.info("Pauta não localizada para o id = {}", id);
            throw new NotFoundException("Pauta não localizada pelo o id" + id);
        }


        return respostaDTO(PautaDto.toDto(pautaOptional.get()));
    }

    public boolean isPautaValida(Integer id){
        if(repository.existsById(id)){
            return Boolean.TRUE;
        }else{
            LOGGER.info("Pauta não localizada para o id = {}", id);
            throw new NotFoundException("Pauta não localizada para o id" + id);
        }
    }

    public List<Pauta> buscarTodasPautas(){

        return repository.findAll();
    }

    private Optional<SessaoVotacao> getSessaoVotacao(Pauta pauta){
        return sessaoVotacaoRepository.findById(pauta.getId());
    }

    public void Percentual(Pauta pauta){

    pauta.setPercentualNao(Precision.round((Double.valueOf(pauta.getQuantidadeVotoNao())/ pauta.getQuantidadeVotoNao())*100, 2));
    pauta.setPercentualSim(Precision.round((Double.valueOf(pauta.getQuantidadeVotoSim())/ pauta.getQuantidadeVotoSim()) *100,2));


    }

    public PautaRespostaDTO respostaDTO(PautaDto dto){
       Pauta pauta =  repository.findById(dto.getId()).orElse(null);

      PautaRespostaDTO pautaRespostaDTO = PautaRespostaDTO.builder()
                .nomePauta(dto.getNomePauta())
                .descricao(dto.getDescricao())
                .vencedor(calcularVencedor(pauta))
                .build();

       return pautaRespostaDTO;
    }

    public String calcularVencedor(Pauta pauta){
        if (pauta.getPercentualSim() > pauta.getPercentualNao()){
            return "SIM";

        } else if (pauta.getPercentualSim() < pauta.getPercentualNao()){
            return "NÃO";
        } else {
            return "EMPATE";
        }
    }

    public void Status(Pauta pauta){

        pauta.setStatus(StatusEnum.FECHADO);

        repository.save(pauta);
    }

    public Pauta criandoPauta(PautaDto dto){
        dto.setStatus(StatusEnum.ABERTO);

       Pauta pauta = PautaDto.toEntity(dto);


      return repository.save(pauta);
    }


}

