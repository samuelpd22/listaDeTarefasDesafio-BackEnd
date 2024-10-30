package com.desafio.lTarefas.repository;

import com.desafio.lTarefas.entity.TarefaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TarefaRepository extends JpaRepository<TarefaEntity, Long> {


    Optional<TarefaEntity> findBynomeTarefa(String nome);

    @Query("SELECT MAX(t.ordemApresentacao) FROM TarefaEntity t")
    Integer findMaxOrdemApresentacao();

}
