package org.example.repository;

import java.util.List;
import java.util.Optional;
import org.example.entity.Aluno;
import org.example.entity.Turma;

public class AlunoRepositoryFake implements AlunoRepository {

  private Aluno aluno;

  public AlunoRepositoryFake() {
    this.aluno = new Aluno(20221111, "Alisson");
  }

  @Override
  public Optional<Aluno> findByNumeroMatricula(int numeroMatricula) {
    if (numeroMatricula == aluno.getMatricula()) {
      return Optional.of(aluno);
    }

    return Optional.empty();
  }

  @Override
  public List<Turma> findAllTurmas() {
    throw new UnsupportedOperationException("Unimplemented method 'findAllTurmas'");
  }
}
