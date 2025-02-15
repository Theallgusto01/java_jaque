package com.example.projetojavafx.model.dao;

import com.example.projetojavafx.db.DB;
import com.example.projetojavafx.model.dao.impl.AlunoDAOJDBC;

public class DAOFactory {
    public static AlunoDAO createAlunoDao(){

        return new AlunoDAOJDBC(DB.getConnection());
    }
}
