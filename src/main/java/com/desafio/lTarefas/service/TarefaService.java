package com.desafio.lTarefas.service;

import com.desafio.lTarefas.dto.TarefaDTO;
import com.desafio.lTarefas.dto.TarefaEditDTO;
import com.desafio.lTarefas.entity.TarefaEntity;
import com.desafio.lTarefas.repository.TarefaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {


    @Autowired
    private TarefaRepository repository;

    public TarefaEntity cadastrarTarefa(TarefaDTO data){
        Optional<TarefaEntity> tarefaExistente = repository.findBynomeTarefa(data.nomeTarefa());
        if (tarefaExistente.isPresent()){
            throw new IllegalArgumentException("Uma tarefa com esse nome já existe.");
        }
        Integer maiorOrdem = repository.findMaxOrdemApresentacao();
        Integer novaOrdem = (maiorOrdem != null) ? maiorOrdem + 1 : 1;

        TarefaEntity novaTarefa = new TarefaEntity();
        BeanUtils.copyProperties(data,novaTarefa);
        novaTarefa.setOrdemApresentacao(novaOrdem);

        return repository.save(novaTarefa);
    }


    public List<TarefaEntity> listaTodos(){
        return repository.findAll();
    }

    public Optional<TarefaEntity> buscarPorId(Long id){
        return repository.findById(id);
    }


    public void deletaTarefaPorId(Long id){
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Tarefa com ID " + id + " não encontrada.");
        }
    }

    public TarefaEntity editaTarefa(Long id, TarefaEditDTO data) {
        Optional<TarefaEntity> tarefaExistenteOpt = repository.findById(id);

        if (tarefaExistenteOpt.isPresent()) {
            TarefaEntity tarefaExistente = tarefaExistenteOpt.get();

            tarefaExistente.atualizarComDTO(data);

            return repository.save(tarefaExistente);

        } else {
            throw new EntityNotFoundException("Tarefa não encontrada com o ID: " + id);
        }
    }
}
