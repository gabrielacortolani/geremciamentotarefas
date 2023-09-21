package com.gerenciamentotarefa.service.impl;

import com.gerenciamentotarefa.exception.RegraNegocioException;
import com.gerenciamentotarefa.model.entity.Tarefa;
import com.gerenciamentotarefa.model.enums.Status;
import com.gerenciamentotarefa.repository.TarefaRepository;
import com.gerenciamentotarefa.service.TarefaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        Tarefa tarefa = buscarTarefaPorId(id);

        if (tarefa.getStatus() == Status.FINALIZADO) {
            throw new RegraNegocioException("Tarefa já finalizada");
        }

        tarefa.setStatus(Status.FINALIZADO);
        tarefa.setDataDeAtualizacao(LocalDate.now());
        return repository.save(tarefa);
    }

    @Override
    public Tarefa reabirTarefa(Long id) {
        Tarefa tarefa = buscarTarefaPorId(id);
        if (tarefa.getStatus() == Status.PENDENTE) {
            throw new RegraNegocioException("Tarefa ainda está pendente");
        }
        tarefa.setStatus(Status.PENDENTE);
        tarefa.setDataDeAtualizacao(LocalDate.now());

        return repository.save(tarefa);
    }

    @Override
    public Tarefa atualizarTarefa(Long id, Tarefa tarefaAtualizada) {
        Tarefa tarefa = buscarTarefaPorId(id);
        merge( tarefaAtualizada, tarefa);
        tarefa.setDataDeAtualizacao(LocalDate.now());
        return repository.save(tarefa);
    }

    @Override
    public void deleteTarefa(Long id) {
        Tarefa tarefa = buscarTarefaPorId(id);
        repository.delete(tarefa);
    }

    private void merge(Tarefa source, Tarefa target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}
