package com.desafio.lTarefas.service;

import com.desafio.lTarefas.dto.TarefaDTO;
import com.desafio.lTarefas.entity.TarefaEntity;
import com.desafio.lTarefas.repository.TarefaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TarefaServiceTest {

    @Mock
    private TarefaRepository repository;

    @InjectMocks
    private TarefaService tarefaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListaTodos() {
        List<TarefaEntity> tarefas = List.of(new TarefaEntity(), new TarefaEntity());
        when(repository.findAll()).thenReturn(tarefas);

        List<TarefaEntity> result = tarefaService.listaTodos();

        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testBuscarPorId_TarefaExistente() {
        TarefaEntity tarefa = new TarefaEntity();
        tarefa.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(tarefa));

        Optional<TarefaEntity> result = tarefaService.buscarPorId(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testBuscarPorId_TarefaNaoEncontrada() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> tarefaService.buscarPorId(1L));
    }

    @Test
    void testCadastrarTarefa_TarefaExistente() {
        TarefaDTO tarefaDTO = new TarefaDTO("Tarefa Existente", 10.0, LocalDate.of(2025, 8, 8), 1);
        when(repository.findBynomeTarefa("Tarefa Existente")).thenReturn(Optional.of(new TarefaEntity()));

        assertThrows(IllegalArgumentException.class, () -> tarefaService.cadastrarTarefa(tarefaDTO));
    }

    @Test
    void testCadastrarTarefa_TarefaNova() {
        TarefaDTO tarefaDTO = new TarefaDTO("Nova Tarefa", 10.0, LocalDate.of(2025, 8, 8), 1);
        when(repository.findBynomeTarefa("Nova Tarefa")).thenReturn(Optional.empty());
        when(repository.findMaxOrdemApresentacao()).thenReturn(1);

        TarefaEntity result = tarefaService.cadastrarTarefa(tarefaDTO);

        assertNotNull(result);
        assertEquals("Nova Tarefa", result.getNomeTarefa());
        assertEquals(10.0, result.getCusto());
        assertEquals(LocalDate.of(2025, 8, 8), result.getDataLimite());
        assertEquals(1, result.getOrdemApresentacao());
        verify(repository, times(1)).save(any(TarefaEntity.class));
    }


}
