package br.univille.projapifso2024a.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.univille.projapifso2024a.entity.Cliente;
import br.univille.projapifso2024a.service.ClienteService;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteControllerAPI {
    
    @Autowired
    private ClienteService service;

    @GetMapping
    public ResponseEntity<List<Cliente>> getAll(){
        var listaClientes = service.getAll();
        return new ResponseEntity<List<Cliente>>(listaClientes,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Cliente> post(@RequestBody Cliente cliente){
        if(cliente.getId() == 0){
            service.save(cliente);
            return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> put(@PathVariable long id,
                                    @RequestBody Cliente cliente){
        var clienteAntigo = service.getById(id);
        if (clienteAntigo == null){
            return ResponseEntity.notFound().build();
        }
        clienteAntigo.setNome(cliente.getNome());
        clienteAntigo.setEndereco(cliente.getEndereco());
        clienteAntigo.setDataNascimento(cliente.getDataNascimento());

        service.save(clienteAntigo);
        return new ResponseEntity<Cliente>(clienteAntigo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cliente> delete(@PathVariable long id){
        var cliente = service.getById(id);
        if(cliente == null){
            return ResponseEntity.notFound().build();
        }

        service.delete(id);
        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }

}

