package com.desafio.lTarefas.dto;

import java.time.LocalDate;

public record TarefaDTO(
        String nomeTarefa,
        Double custo,
        LocalDate dataLimite,
        Integer ordemApresentacao)
        {
}
