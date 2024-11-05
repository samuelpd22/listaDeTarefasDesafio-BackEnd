package com.desafio.lTarefas.repository;

import com.desafio.lTarefas.entity.TarefaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TarefaRepository extends JpaRepository<TarefaEntity, Long> {


    Optional<TarefaEntity> findBynomeTarefa(String nome);

    @Query("SELECT MAX(t.ordemApresentacao) FROM TarefaEntity t")
    Integer findMaxOrdemApresentacao();

    Optional<TarefaEntity> findByNomeTarefa(String nomeTarefa);


    TarefaEntity findByOrdemApresentacao(Integer ordemApresentacao);

    @Query("SELECT t FROM TarefaEntity t WHERE LOWER(TRIM(t.nomeTarefa)) LIKE LOWER(CONCAT('%', :nomeTarefa, '%'))")
    List<TarefaEntity> findByNomeTarefaIgnoreSpacesAndCase(@Param("nomeTarefa") String nomeTarefa);
}





