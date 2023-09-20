package com.gerenciamentotarefa.service.impl;

import com.gerenciamentotarefa.exception.RegraNegocioException;
import com.gerenciamentotarefa.model.entity.Tarefa;
import com.gerenciamentotarefa.model.enums.Status;
import com.gerenciamentotarefa.repository.TarefaRepository;
import com.gerenciamentotarefa.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TarefaServiceImpl implements TarefaService {

    @Autowired
    TarefaRepository repository;

    @Override
    public Tarefa salvarTarefa(Tarefa tarefa) {
        tarefa.setStatus(Status.PENDENTE);
        tarefa.setDataDeCriacao(LocalDate.now());
        return repository.save(tarefa);
    }

    @Override
    public List<Tarefa> buscarTarefas() {
        return repository.findAll();
    }

    @Override
    public Tarefa buscarTarefaPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Id não encontrado"));
    }

    @Override
    public Tarefa finalizarTarefa(Long id) {
        Tarefa tarefa = repository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Id não encontrado"));

        tarefa.setStatus(Status.FINALIZADO);
        tarefa.setDataDeAtualizacao(LocalDate.now());

        return repository.save(tarefa);
    }

    @Override
    public Tarefa reabirTarefa(Long id) {
        Tarefa tarefa = repository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Id não encontrado"));

        tarefa.setStatus(Status.PENDENTE);
        tarefa.setDataDeAtualizacao(LocalDate.now());

        return repository.save(tarefa);
    }

    @Override
    public void deleteTarefa(Long id) {
        Tarefa tarefa = repository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Id não encontrado"));
        repository.delete(tarefa);
    }
}
