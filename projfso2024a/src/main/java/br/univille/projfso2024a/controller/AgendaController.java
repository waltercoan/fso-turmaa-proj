package br.univille.projfso2024a.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.univille.projfso2024a.entity.Agendamento;
import br.univille.projfso2024a.service.AgendamentoService;
import br.univille.projfso2024a.viewmodel.Agenda;
import br.univille.projfso2024a.viewmodel.Dia;
import br.univille.projfso2024a.viewmodel.Semana;

@Controller
@RequestMapping("/agenda")
public class AgendaController {

    @Autowired
    private AgendamentoService service;
    @GetMapping
    public ModelAndView index(){

        HashMap<String,Object> dados = new HashMap<>();
        
        var agenda = new Agenda();
        agenda.setMes(6);
        agenda.setAno(2024);

        var data = LocalDate.of(agenda.getAno(), agenda.getMes(), 1);
        for(int semanacalend=1; semanacalend <= 6; semanacalend++){
            var semana = new Semana();
            agenda.getListaSemanas().add(semana);
            for(int diacalend=1; diacalend <= 7; diacalend++){
                var dia = new Dia();
                semana.getListaDias().add(dia);
                LocalDate dayValue = null;
                var dayWeek = data.getDayOfWeek();
                if(dayWeek.getValue() == diacalend){
                    dayValue = data;
                    var agendamento = service.getByDate(new Date(dayValue.getYear()-1900, dayValue.getMonth().ordinal(), dayValue.getDayOfMonth()));
                    if(agendamento != null)
                        dia.getListaAgendamentos().add(agendamento);
                    data = data.plusDays(1);
                    dia.setDia(dayValue.getDayOfMonth());
                    dia.setMes(dayValue.getMonth().ordinal());
                    dia.setAno(dayValue.getYear()-1900);
                }
                
            }
        }
        dados.put("agenda", agenda);

        return new ModelAndView("agenda/index",dados);
    }
    
}
