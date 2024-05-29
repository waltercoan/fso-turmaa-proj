package br.univille.projapifso2024a.service;

import java.util.List;

import br.univille.projapifso2024a.entity.Cidade;


public interface CidadeService {
    void save(Cidade cidade);
    Cidade getById(long id);
    List<Cidade> getAll();
    Cidade delete(long id);
}
