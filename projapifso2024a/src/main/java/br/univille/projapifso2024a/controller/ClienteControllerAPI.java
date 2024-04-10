package br.univille.projapifso2024a.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteControllerAPI {
    @GetMapping
    public ResponseEntity<Cliente> buscaCliente(){
        var umCliente = new Cliente("Zezinho");
        return new ResponseEntity<Cliente>(umCliente, HttpStatus.OK);
    }
}

class Cliente {
    private String nome;
    public Cliente(String nome) {
        this.nome = nome;
    }
    public String getNome(){
        return this.nome;
    }
}
