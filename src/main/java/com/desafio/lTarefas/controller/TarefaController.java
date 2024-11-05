package com.desafio.lTarefas.controller;

import com.desafio.lTarefas.dto.TarefaDTO;
import com.desafio.lTarefas.dto.TarefaEditDTO;
import com.desafio.lTarefas.entity.TarefaEntity;
import com.desafio.lTarefas.service.TarefaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping ("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService service;



    @GetMapping("/lista")
    public ResponseEntity<TarefaEntity> listaDeTarefas() {
        return new ResponseEntity(service.listaTodos(), HttpStatus.OK);
    }


    @GetMapping("/buscaPorId/{id}")
    public ResponseEntity<TarefaEntity> listaPorId(@PathVariable Long id) {
        return new ResponseEntity(service.buscarPorId(id), HttpStatus.OK);
    }


    @GetMapping("/buscarTarefa/{nomeTarefa}")
    public ResponseEntity<TarefaEntity> buscarTarefaPorNome(@PathVariable String nomeTarefa){
        return new ResponseEntity(service.pesquisarPorNome(nomeTarefa),HttpStatus.OK);
    }


    @PostMapping("/cadastra")
    public ResponseEntity<List<TarefaEntity>> cadastra(@RequestBody TarefaDTO data) {
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


    @PutMapping("/{id}/subir")
    public ResponseEntity<?> moverParaCima(@PathVariable Long id) {
        boolean sucesso = service.moverParaCima(id);
        if (sucesso) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("A tarefa não pode ser movida para cima.");
    }


    @PutMapping("/{id}/descer")
    public ResponseEntity<?> moverParaBaixo(@PathVariable Long id) {
        boolean sucesso = service.moverParaBaixo(id);
        if (sucesso) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("A tarefa não pode ser movida para baixo.");
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





