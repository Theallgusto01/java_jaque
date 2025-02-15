package com.example.projetojavafx.model.dao;

import com.example.projetojavafx.model.entities.Aluno;

import java.util.List;

public interface AlunoDAO {

    void inserir(Aluno a);

    Aluno exibirPorId(Integer id);

    void deletar(Integer id);

    void atualizar(Aluno a);

    List<Aluno> buscarTodos();
}
