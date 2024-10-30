package com.desafio.lTarefas.entity;

import com.desafio.lTarefas.dto.TarefaEditDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.text.DateFormat;
import java.time.LocalDate;

@Entity
@Table ( name = "tb_listaTarefas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TarefaEntity {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da tarefa é obrigatório.")
    @Size(max = 100, message = "O nome da tarefa não pode exceder 100 caracteres.")
    private String nomeTarefa;

    @DecimalMin(value = "0.00", message = "O custo deve ser um valor positivo.")
    private Double custo;

    @Future(message = "A data limite deve ser uma data futura.")
    private LocalDate dataLimite;

    @Column(unique = true)
    private Integer ordemApresentacao;

    public void atualizarComDTO(TarefaEditDTO data) {
        this.nomeTarefa = data.nomeTarefa();
        this.custo = data.custo();
        this.dataLimite = data.dataLimite();
    }


}
