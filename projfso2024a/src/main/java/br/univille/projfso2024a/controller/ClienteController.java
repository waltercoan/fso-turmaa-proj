package br.univille.projfso2024a.controller;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.univille.projfso2024a.entity.Cliente;
import br.univille.projfso2024a.service.CidadeService;
import br.univille.projfso2024a.service.ClienteService;
import br.univille.projfso2024a.service.SalvarArquivosService;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;
    @Autowired
    private CidadeService cidadeService;
    @Autowired
    private SalvarArquivosService salvarArquivoService;

    @GetMapping
    public ModelAndView index(){
        var listaClientes = service.getAll();
        return new ModelAndView("cliente/index","listaClientes",listaClientes);
    }

    @GetMapping("/novo")
    public ModelAndView novo(){
        var cliente = new Cliente();
        var listaCidades = cidadeService.getAll();

        HashMap<String,Object> dados = new HashMap<>();
        dados.put("cliente",cliente);
        dados.put("listaCidades",listaCidades);

        return new ModelAndView("cliente/form",dados);
    }

    @PostMapping
    public ModelAndView save(Cliente cliente, @RequestParam("file") MultipartFile file){
        if(file.getSize() != 0){
            String caminho = salvarArquivoService.save(file);
            cliente.setFoto(caminho);
        }
        service.save(cliente);
        return new ModelAndView("redirect:/clientes");
    }

    @GetMapping("/alterar/{id}")
    public ModelAndView alterar(@PathVariable("id") long id){
        var cliente = service.getById(id);
        var listaCidades = cidadeService.getAll();
        
        HashMap<String,Object> dados = new HashMap<>();
        dados.put("cliente",cliente);
        dados.put("listaCidades",listaCidades);

        return new ModelAndView("cliente/form", dados);
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") long id){
        service.delete(id);
        return new ModelAndView("redirect:/clientes");
    }
    @GetMapping(value = "/image/{id}")
    public @ResponseBody byte[] getImage(@PathVariable("id") Cliente cliente){
        try{
            File file = new File(cliente.getFoto());
            byte[] bytes = new byte[(int) file.length()];
            try(DataInputStream dis = new DataInputStream(new FileInputStream(file));){
                dis.readFully(bytes);
            }
            return bytes;
        }catch (Exception e){
            return new byte[0];
        }
    }
}
