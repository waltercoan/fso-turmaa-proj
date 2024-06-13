package br.univille.projfso2024a.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class Agenda {
    private int mes;
    private int ano;
    List<Semana> listaSemanas = new ArrayList<Semana>();
    
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
    public List<Semana> getListaSemanas() {
        return listaSemanas;
    }
    public void setListaSemanas(List<Semana> listaSemanas) {
        this.listaSemanas = listaSemanas;
    }

    
}
