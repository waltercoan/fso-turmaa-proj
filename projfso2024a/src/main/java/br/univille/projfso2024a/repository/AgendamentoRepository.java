package br.univille.projfso2024a.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.univille.projfso2024a.entity.Agendamento;

@Repository
public interface AgendamentoRepository 
    extends JpaRepository<Agendamento,Long>{
    
    Agendamento findByData(Date data);
}
