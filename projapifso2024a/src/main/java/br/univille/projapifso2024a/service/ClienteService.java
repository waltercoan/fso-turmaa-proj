package br.univille.projapifso2024a.service;

import java.util.List;
import br.univille.projapifso2024a.entity.Cliente;

public interface ClienteService {
    void save(Cliente cliente);
    Cliente getById(long id);
    List<Cliente> getAll();
}
