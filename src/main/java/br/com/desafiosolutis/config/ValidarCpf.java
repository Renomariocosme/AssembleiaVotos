package br.com.desafiosolutis.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "cpfValidator", url = "https://cpf-api-almfelipe.herokuapp.com/cpf/12345678901")
public interface ValidarCpf {


    @RequestMapping(method = RequestMethod.GET, value = "/cpf/{cpf}")
    ValidarCpf validar(@PathVariable String cpf);
}
