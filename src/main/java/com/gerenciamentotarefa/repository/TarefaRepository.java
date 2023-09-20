package com.gerenciamentotarefa.repository;

import com.gerenciamentotarefa.model.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
}
