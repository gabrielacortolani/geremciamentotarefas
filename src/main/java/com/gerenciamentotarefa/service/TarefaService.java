package com.gerenciamentotarefa.service;

import com.gerenciamentotarefa.model.entity.Tarefa;

import java.util.List;
import java.util.Optional;

public interface TarefaService {
    Tarefa salvarTarefa(Tarefa tarefa);
    List<Tarefa> buscarTarefas();

     Tarefa buscarTarefaPorId(Long id);

     Tarefa finalizarTarefa(Long id);

    Tarefa reabirTarefa(Long id);

    Tarefa atualizarTarefa(Long id, Tarefa tarefaAtualizada);

    void deleteTarefa(Long id);
}
