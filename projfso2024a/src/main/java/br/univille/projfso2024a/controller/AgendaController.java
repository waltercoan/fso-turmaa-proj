package br.univille.projfso2024a.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @GetMapping({"","/","/{mes}/{ano}"})
    public ModelAndView index(@PathVariable(name="mes",required = false) Integer mes,
                                @PathVariable(name="ano",required = false) Integer ano) {

        HashMap<String,Object> dados = new HashMap<>();
        
        var agenda = new Agenda();
        if(mes == null || ano == null){
            var today = LocalDate.now();
            mes = today.getMonthValue();
            ano = today.getYear();
        }
        if(mes > 12){
            mes = 1;
            ano = ano + 1;
        }
        if(mes < 1){
            mes = 12;
            ano = ano - 1;
        }
        agenda.setMes(mes);
        agenda.setAno(ano);

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
                    var agendamentos = service.getAllByDate(new Date(dayValue.getYear()-1900, dayValue.getMonth().ordinal(), dayValue.getDayOfMonth()));
                    if(agendamentos.size() != 0)
                        dia.getListaAgendamentos().addAll(agendamentos);
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
