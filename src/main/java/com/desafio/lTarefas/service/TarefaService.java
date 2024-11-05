package com.desafio.lTarefas.service;

import com.desafio.lTarefas.dto.TarefaDTO;
import com.desafio.lTarefas.dto.TarefaEditDTO;
import com.desafio.lTarefas.entity.TarefaEntity;
import com.desafio.lTarefas.repository.TarefaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TarefaService {


    @Autowired
    private TarefaRepository repository;


    public List<TarefaEntity> listaTodos(){
        return repository.findAll();
    }


    public Optional<TarefaEntity> buscarPorId(Long id) {
        Optional<TarefaEntity> existeTarefa = repository.findById(id);

        if (existeTarefa.isPresent()) {
            return existeTarefa;
        } else {
            throw new EntityNotFoundException("Tarefa não encontrada");
        }
    }


    public List<TarefaEntity> pesquisarPorNome(String nomeTarefa){
        return repository.findByNomeTarefaIgnoreSpacesAndCase(nomeTarefa);
    }



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



    public TarefaEntity editaTarefa(Long id, TarefaEditDTO data) {
        Optional<TarefaEntity> tarefaExistenteById = repository.findById(id);

        if (tarefaExistenteById.isPresent()) {
            TarefaEntity tarefaExistenteByNome = tarefaExistenteById.get();


            if (!tarefaExistenteByNome.getNomeTarefa().equals(data.nomeTarefa())) {
                Optional<TarefaEntity> tarefaComMesmoNome = repository.findByNomeTarefa(data.nomeTarefa());

                if (tarefaComMesmoNome.isPresent()) {
                    throw new IllegalArgumentException("Já existe uma tarefa com o nome: " + data.nomeTarefa());
                }
            }

            //Atualiza os dados da tarefa
            tarefaExistenteByNome.atualizarComDTO(data);
            return repository.save(tarefaExistenteByNome);

        } else {
            throw new EntityNotFoundException("Tarefa não encontrada com o ID: " + id);
        }
    }



    public boolean moverParaCima(Long id) {
        TarefaEntity tarefa = repository.findById(id).orElse(null);
        if (tarefa == null || tarefa.getOrdemApresentacao() == 1) {
            return false; //indica que n pode ser movida
        }

        TarefaEntity tarefaAcima = repository.findByOrdemApresentacao(tarefa.getOrdemApresentacao() - 1);
        if (tarefaAcima != null) {
            //ordem temporaria
            tarefaAcima.setOrdemApresentacao(-1); //diminui valor da tarefaAcima
            repository.save(tarefaAcima);

            int ordemAtual = tarefa.getOrdemApresentacao();
            tarefa.setOrdemApresentacao(ordemAtual - 1);
            tarefaAcima.setOrdemApresentacao(ordemAtual);

            repository.save(tarefa);
            repository.save(tarefaAcima);
            return true; //Sucesso
        }
        return false; //Falha ao mover a tarefa
    }


    public boolean moverParaBaixo(Long id) {
        TarefaEntity tarefa = repository.findById(id).orElse(null);
        if (tarefa == null) {
            return false; //indica que n pode ser movida
        }

        //Encontre a tarefa abaixo
        TarefaEntity tarefaAbaixo = repository.findByOrdemApresentacao(tarefa.getOrdemApresentacao() + 1);
        if (tarefaAbaixo != null) {

            //Use uma ordem temporária para evitar duplicidade
            tarefaAbaixo.setOrdemApresentacao(-1);
            repository.save(tarefaAbaixo);

            int ordemAtual = tarefa.getOrdemApresentacao();
            tarefa.setOrdemApresentacao(ordemAtual + 1);
            tarefaAbaixo.setOrdemApresentacao(ordemAtual);

            repository.save(tarefa);
            repository.save(tarefaAbaixo);
            return true; //Sucesso
        }
        return false; //Falha ao mover a tarefa
    }




    public void deletaTarefaPorId(Long id){
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Tarefa com ID " + id + " não encontrada.");
        }
    }









}






