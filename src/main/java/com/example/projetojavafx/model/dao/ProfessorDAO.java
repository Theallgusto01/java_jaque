package com.example.projetojavafx.model.dao;

import com.example.projetojavafx.model.entities.Professor;


import java.util.List;

public interface ProfessorDAO {

    void inserir(Professor a);

    Professor exibirPorId(Integer id);

    void deletar(Integer id);

    void atualizar(Professor a);

    List<Professor> buscarTodos();
}
