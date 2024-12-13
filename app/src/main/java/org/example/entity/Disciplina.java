package org.example.entity;

public class Disciplina {

  private String nome;
  private String codigo;
  private int cargaHoraria;

  public Disciplina() {
  }

  public Disciplina(String nome, String codigo, int cargaHoraria) {
    this.nome = nome;
    this.codigo = codigo;
    this.cargaHoraria = cargaHoraria;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public int getCargaHoraria() {
    return cargaHoraria;
  }

  public void setCargaHoraria(int cargaHoraria) {
    this.cargaHoraria = cargaHoraria;
  }
}
