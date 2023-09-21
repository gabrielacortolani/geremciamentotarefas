package com.gerenciamentotarefa.service;

import com.gerenciamentotarefa.exception.RegraNegocioException;
import com.gerenciamentotarefa.model.entity.Tarefa;
import com.gerenciamentotarefa.model.enums.Status;
import com.gerenciamentotarefa.service.impl.TarefaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @Autowired
    TarefaServiceImpl service;

    private Tarefa tarefa; // Campo para armazenar a tarefa

    @BeforeEach
    public void setUp() {
        // Codigo para sempre criar uma tarefa, pois todo método irá usar uma nova tarefa
        tarefa = service.salvarTarefa(criaTarefa());
    }

    @Test
    public void deveSalvarUmaTarefa(){
        //Metodo para garantir que uma tarefa foi salva
        assertNotNull(tarefa);
    }

    @Test
    public void novaTarefaDeveIniciarComStatusPendente(){
        // Metodo para garantir que toda tarefa é criada com status = PENDENTE
        assertEquals(Status.PENDENTE, tarefa.getStatus());
    }

    @Test
    public void deveDispararExcecaoDeIdNaoEncontradaNoMetodoBuscarTarefaPorIdQuandoNaoExistir(){
        //Metodo para garantir que irá retornar Id não encontrado caso uma tarefa não exista
       RegraNegocioException exception = assertThrows(RegraNegocioException.class, () -> service.buscarTarefaPorId(99999L));
       valoresIguais("Id não encontrado", exception.getMessage());
    }

    @Test
    public void deveDispararExcecaoDeIdNaoEncontradaQuantoTentarExcluirTarefaNaoExistente(){
        //Metodo para garantir que irá retornar Id não encontrado quando tentar excluir uma tarefa que não existe
        RegraNegocioException exception = assertThrows(RegraNegocioException.class, () -> service.deleteTarefa(99999L));
        // Verifique se a mensagem da exceção é a esperada
        valoresIguais("Id não encontrado", exception.getMessage());
    }

    @Test
    public void deveExcluirTarefa(){
        //Metodo para garantir que uma tarefa é excluida
        service.deleteTarefa(tarefa.getId());
        RegraNegocioException exception = assertThrows(RegraNegocioException.class, () -> service.buscarTarefaPorId(tarefa.getId()));
        // Verifique se a mensagem da exceção é a esperada
        valoresIguais("Id não encontrado", exception.getMessage());
    }

    @Test
    public void naoDeveReabriTarefaPendente(){
        //Metodo para garantir que uma tarefa não pode ser reaberta se estiver pendente
        RegraNegocioException exception = assertThrows(RegraNegocioException.class, () -> service.reabirTarefa(tarefa.getId()));
        valoresIguais("Tarefa ainda está pendente", exception.getMessage());
    }

    @Test
    public void naoDeveFinalizarTarefaJaFinalizada(){
        //Metodo para garantir que não seja finaliza novamente uma tarefa que já foi finalizada
        service.finalizarTarefa(tarefa.getId());
        RegraNegocioException exception = assertThrows(RegraNegocioException.class, () -> service.finalizarTarefa(tarefa.getId()));
        valoresIguais("Tarefa já finalizada", exception.getMessage());
    }

    @Test
    public void validarSeAtualizacaoEstaSendoFeitaParaOsCamposPassados(){
        //Metodo para garantir que os campos de uma tarefa estão sendo alterados
        Tarefa novaTarefa = Tarefa.builder().nome("Tarefa Teste 2")
                .observacao("Verificar se o campo foi alterado")
                .build();

        Tarefa tarefaAtualizada = service.atualizarTarefa(tarefa.getId(), novaTarefa);
        valoresIguais(novaTarefa.getNome(), tarefaAtualizada.getNome());
        valoresIguais(novaTarefa.getObservacao(), tarefaAtualizada.getObservacao());
    }

    private static void valoresIguais(String mensagemEsperada, String exception) {
        //Metodo para garantir os valores são iguais
        assertEquals(mensagemEsperada, exception);
    }

    public static Tarefa criaTarefa(){
        //Metodo para retornar uma tarefa;
        return Tarefa.builder().nome("Tarefa Teste")
                .descricao("Tarefa de teste ")
                .observacao("Tarefa para realizar testes")
                .build();
    }
    
    

}
