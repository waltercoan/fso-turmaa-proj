package br.univille.projfso2024a.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.univille.projfso2024a.entity.Cidade;
import br.univille.projfso2024a.entity.Cliente;
import br.univille.projfso2024a.service.CidadeService;
import br.univille.projfso2024a.service.ClienteService;

@Controller
@RequestMapping("/cidades")
public class CidadeController {
    @Autowired
    private CidadeService service;

    @GetMapping
    public ModelAndView index(){
        var listaCidades = service.getAll();
        return new ModelAndView("cidade/index","listaCidades",listaCidades);
    }

    @GetMapping("/novo")
    public ModelAndView novo(){
        var cidade = new Cidade();

        HashMap<String,Object> dados = new HashMap<>();
        dados.put("cidade",cidade);

        return new ModelAndView("cidade/form",dados);
    }

    @PostMapping
    public ModelAndView save(Cidade cidade){
        service.save(cidade);
        return new ModelAndView("redirect:/cidades");
    }

    @GetMapping("/alterar/{id}")
    public ModelAndView alterar(@PathVariable("id") long id){
        var cidade = service.getById(id);
        
        HashMap<String,Object> dados = new HashMap<>();
        dados.put("cidade",cidade);

        return new ModelAndView("cidade/form", dados);
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") long id){
        service.delete(id);
        return new ModelAndView("redirect:/cidades");
    }
}
