package br.univille.projfso2024a.service;

import java.util.Date;
import java.util.List;

import br.univille.projfso2024a.entity.Agendamento;

public interface AgendamentoService {
    Agendamento save(Agendamento agendamento);
    List<Agendamento> getAllByDate(Date data);

}
