package br.univille.projfso2024a.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.univille.projfso2024a.entity.Cidade;
import br.univille.projfso2024a.repository.CidadeRepository;
import br.univille.projfso2024a.service.CidadeService;

@Service
public class CidadeServiceImpl implements CidadeService{

    @Autowired
    private CidadeRepository repository;

    @Override
    public List<Cidade> getAll() {
        return repository.findAll();
    }

    @Override
    public void save(Cidade cidade) {
        repository.save(cidade);
    }

    @Override
    public Cidade getById(long id) {
        return repository.getById(id);
    }

    @Override
    public Cidade delete(long id) {
        var cidade = getById(id);
        repository.delete(cidade);
        return cidade;
    }
    
}
