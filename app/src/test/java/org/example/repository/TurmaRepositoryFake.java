package org.example.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.example.entity.Aluno;
import org.example.entity.Disciplina;
import org.example.entity.Periodo;
import org.example.entity.Turma;

public class TurmaRepositoryFake implements TurmaRepository {

  private Turma turma;

  public TurmaRepositoryFake() {
    Periodo periodo = new Periodo(2024, 2);
    Disciplina disciplina = new Disciplina("Topicos em Desenvolvimento de Sistemas", "tds", 20);

    this.turma = new Turma("tds-2024-2", disciplina, periodo, 10, new ArrayList<>());
  }

  @Override
  public Optional<Turma> findByCodigo(String codigoTurma) {
    if (codigoTurma.equals(turma.getCodigo())) {
      return Optional.of(turma);
    }

    return Optional.empty();
  }

  @Override
  public List<Turma> findAllContainingAluno(Aluno aluno) {
    return List.of(turma);
  }
}
