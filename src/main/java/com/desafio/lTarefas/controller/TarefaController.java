package com.desafio.lTarefas.controller;

import com.desafio.lTarefas.dto.TarefaDTO;
import com.desafio.lTarefas.dto.TarefaEditDTO;
import com.desafio.lTarefas.entity.TarefaEntity;
import com.desafio.lTarefas.service.TarefaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping ("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService service;


    @GetMapping("/lista")
    public ResponseEntity<TarefaEntity> listaDeTarefas(){
        return new ResponseEntity(service.listaTodos(), HttpStatus.OK);
    }

    @GetMapping("/buscaPorId/{id}")
    public ResponseEntity<TarefaEntity> listaPorId(@PathVariable Long id){
        Optional<TarefaEntity> tarefaExiste = service.buscarPorId(id);

        if (tarefaExiste.isPresent()) {
            return ResponseEntity.ok(tarefaExiste.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/cadastra")
    public ResponseEntity<List<TarefaEntity>> cadastra(@RequestBody TarefaDTO data){
        TarefaEntity novaTarefa = service.cadastrarTarefa(data);
        return new ResponseEntity(novaTarefa, HttpStatus.OK);
    }

    @PutMapping("/atualiza/{id}")
    public ResponseEntity<TarefaEntity> editaTarefa(@PathVariable Long id, @RequestBody TarefaEditDTO data) {
        try {
            TarefaEntity tarefaAtualizada = service.editaTarefa(id, data);
            return ResponseEntity.ok(tarefaAtualizada);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }




    @DeleteMapping("/deleta/{id}")
    public ResponseEntity<String> apagaTarefa(@PathVariable Long id) {
        Optional<TarefaEntity> tarefaExiste = service.buscarPorId(id);

        if (tarefaExiste.isPresent()) {
            service.deletaTarefaPorId(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }

        }

    }
