package org.example.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.example.entity.Aluno;
import org.example.entity.Matricula;
import org.example.entity.Turma;
import org.example.exception.DomainException;
import org.example.exception.NotFoundException;
import org.example.repository.AlunoRepository;
import org.example.repository.AlunoRepositoryFake;
import org.example.repository.TurmaRepository;
import org.example.repository.TurmaRepositoryFake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MatriculaServiceTest {

  MatriculaService matriculaService;
  AlunoRepository alunoRepository;
  TurmaRepository turmaRepository;

  @BeforeEach
  void Setup() {
    alunoRepository = new AlunoRepositoryFake();
    turmaRepository = new TurmaRepositoryFake();
    matriculaService = new MatriculaService(alunoRepository, turmaRepository);
  }

  @Test
  void TestVerdade() {
    assertThat(true).isTrue();
  }

  @Test
  void TestFalso() {
    assertThat(false).isFalse();
  }

  @Test
  void TestHaUmaInstanciaDeMatriculaServices() {
    assertThat(matriculaService).isNotNull();
  }

  @Test
  void TestAlunoNaoExisteLançaExceçãoNotFoundException() {
    assertThatThrownBy(() -> matriculaService.matricular(123, "tds-2024-2"))
        .isInstanceOf(NotFoundException.class)
        .hasMessage("Aluno 123 não encontrado");
  }

  @Test
  void TestTurmaNaoExisteLançaExceçãoNotFoundException() {
    assertThatThrownBy(() -> matriculaService.matricular(20221111, "tda-2024-2"))
        .isInstanceOf(NotFoundException.class)
        .hasMessage("Turma tda-2024-2 não encontrada");
  }

  @Test
  void TestMatricularAlunoComSucesso() {
    Optional<Aluno> aluno = alunoRepository.findByNumeroMatricula(20221111);
    Optional<Turma> turma = turmaRepository.findByCodigo("tds-2024-2");

    if (aluno.isPresent() && turma.isPresent()) {
      Matricula matricula = matriculaService.matricular(aluno.get().getMatricula(), turma.get().getCodigo());

      assertNotNull(matricula);
      assertEquals(aluno.get().getMatricula(), matricula.getAluno().getMatricula());
      assertEquals(Matricula.Status.REGULAR, matricula.getStatus());
    }
  }

  @Test
  void TestDeveLancarExcecaoQuandoAlunoJaMatriculadoNaTurma() {
    Optional<Aluno> aluno = alunoRepository.findByNumeroMatricula(20221111);
    Optional<Turma> turma = turmaRepository.findByCodigo("tds-2024-2");

    if (aluno.isPresent() && turma.isPresent()) {
      Integer numeroMatriculaAluno = aluno.get().getMatricula();
      String codigoTurma = turma.get().getCodigo();

      Matricula matricula = matriculaService.matricular(numeroMatriculaAluno, codigoTurma);
      turma.get().getMatriculas().add(matricula);

      assertThatThrownBy(() -> matriculaService.matricular(numeroMatriculaAluno, codigoTurma))
          .isInstanceOf(DomainException.class)
          .hasMessage("Aluno " + numeroMatriculaAluno + " já matriculado na turma " + codigoTurma);
    }
  }

  @Test
  void TestDeveLancarExcecaoQuandoNaoHaVagasENaoHaHistoricoNaDisciplina() {
    Optional<Turma> turma = turmaRepository.findByCodigo("tds-2024-2");

    if (turma.isPresent()) {
      turma.get().setVagas(0);

      assertThatThrownBy(() -> matriculaService.matricular(20221111, "tds-2024-2"))
          .isInstanceOf(DomainException.class)
          .hasMessage("Não há vagas na turma tds-2024-2");
    }
  }

}
