package br.univille.projfso2024a.service;

import java.util.Date;

import br.univille.projfso2024a.entity.Agendamento;

public interface AgendamentoService {
    Agendamento save(Agendamento agendamento);
    Agendamento getByDate(Date data);

}
