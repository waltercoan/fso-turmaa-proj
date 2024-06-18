package br.univille.projfso2024a.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.univille.projfso2024a.entity.Agendamento;
import br.univille.projfso2024a.repository.AgendamentoRepository;
import br.univille.projfso2024a.service.AgendamentoService;

@Service
public class AgendamentoServiceImpl 
    implements AgendamentoService {

    @Autowired
    private AgendamentoRepository repository;
    @Override
    public Agendamento save(Agendamento agendamento) {
        return repository.save(agendamento);
    }

    @Override
    public Agendamento getByDate(Date data) {
        return repository.findByData(data);
    }
    
}
