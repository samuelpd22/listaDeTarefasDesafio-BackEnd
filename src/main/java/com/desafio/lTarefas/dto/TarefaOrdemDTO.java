package com.desafio.lTarefas.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TarefaOrdemDTO {

    private Long id;
    private Integer ordemApresentacao;


    public Integer getOrdemApresentacao() {
        return ordemApresentacao;
    }

    public void setOrdemApresentacao(Integer ordemApresentacao) {
        this.ordemApresentacao = ordemApresentacao;
    }
}
