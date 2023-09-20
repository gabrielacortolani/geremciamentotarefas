package com.gerenciamentotarefa.controller;

import com.gerenciamentotarefa.model.entity.Tarefa;
import com.gerenciamentotarefa.service.impl.TarefaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tarefa")
public class TarefaController {

    @Autowired
    TarefaServiceImpl service;

    @PostMapping
    Tarefa salvarTarefa(@RequestBody Tarefa tarefa){
        return service.salvarTarefa(tarefa);
    }

    @GetMapping
    List<Tarefa> buscarTarefas(){
        return service.buscarTarefas();
    }

    @GetMapping("/{id}")
    Tarefa buscaPorId(@PathVariable Long id){
        return service.buscarTarefaPorId(id);
    }

    @PutMapping("/{id}/finalizar")
    Tarefa finalizaTarefa(@PathVariable Long id){
        return service.finalizarTarefa(id);
    }

    @PutMapping("/{id}/reabrir")
    Tarefa reabrirTarefa(@PathVariable Long id){
        return service.reabirTarefa(id);
    }

    @DeleteMapping("/{id}")
    void deleteTarefa(@PathVariable Long id){
        service.deleteTarefa(id);
    }

}
