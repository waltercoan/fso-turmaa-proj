package br.univille.projfso2024a.viewmodel;

import java.util.ArrayList;
import java.util.List;

import br.univille.projfso2024a.entity.Agendamento;

public class Dia {
    private int dia;
    private int mes;
    private int ano;
    
    private List<Agendamento> listaAgendamentos = new ArrayList<>();

    
    public List<Agendamento> getListaAgendamentos() {
        return listaAgendamentos;
    }
    public void setListaAgendamentos(List<Agendamento> listaAgendamentos) {
        this.listaAgendamentos = listaAgendamentos;
    }
    public int getDia() {
        return dia;
    }
    public void setDia(int dia) {
        this.dia = dia;
    }
    public int getMes() {
        return mes;
    }
    public void setMes(int mes) {
        this.mes = mes;
    }
    public int getAno() {
        return ano;
    }
    public void setAno(int ano) {
        this.ano = ano;
    }
    
    
}
