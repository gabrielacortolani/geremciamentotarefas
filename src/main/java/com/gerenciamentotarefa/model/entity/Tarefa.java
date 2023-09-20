package com.gerenciamentotarefa.model.entity;

import com.gerenciamentotarefa.model.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name = "tarefa")
@Entity(name = "tarefa")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tarefa {

    @Id
    @Column(name = "id")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private Status status;
    @Column(name = "observacao")
    private String observacao;
    @Column(name = "data_criacao")
    private LocalDate dataDeCriacao;
    @Column(name = "data_atualizacao")
    private LocalDate dataDeAtualizacao;
}
