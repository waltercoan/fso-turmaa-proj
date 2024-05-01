package br.univille.projfso2024a.service;

import java.util.List;
import br.univille.projfso2024a.entity.Cliente;

public interface ClienteService {
    void save(Cliente cliente);
    Cliente getById(long id);
    List<Cliente> getAll();
    Cliente delete(long id);
}
