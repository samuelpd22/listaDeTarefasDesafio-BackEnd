package com.desafio.lTarefas.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public record TarefaEditDTO(String nomeTarefa, Double custo, LocalDate dataLimite) {



}
