package com.example.projetojavafx.model.dao.impl;

import com.example.projetojavafx.db.DB;
import com.example.projetojavafx.model.dao.ProfessorDAO;
import com.example.projetojavafx.model.entities.Professor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAOJDBC implements ProfessorDAO {
    private Connection conn;

    public ProfessorDAOJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void inserir(Professor a) {
        PreparedStatement st=null;
        try {
            st =
                    conn.prepareStatement("insert into aluno(nome,datanascimento,foto)" +
                            "values(?,?,?)",Statement.RETURN_GENERATED_KEYS);
            st.setString(1,a.getNome());
            st.setBytes(4,a.getFoto());
            int linhasAfetadas=st.executeUpdate();


            if (linhasAfetadas > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int matricula = rs.getInt(1);
                    a.setMatricula(matricula);
                }
            } else {
                throw new RuntimeException("Erro inesperado! Nenhuma linha afetada!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public Professor exibirPorId(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st=conn.prepareStatement("SELECT * FROM aluno WHERE matricula = ?");

            st.setInt(1, id);

            rs = st.executeQuery();
            if (rs.next()) {
                Professor a = new Professor();
                a.setMatricula(rs.getInt("matricula"));
                a.setNome(rs.getString("nome"));
                a.setFoto(rs.getBytes("foto"));

                return a;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao procurar aluno por ID", e);

        }finally {
            DB.closeResultset(rs);
            DB.closeStatement(st);


        }

    }

    @Override
    public void atualizar(Professor a) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE aluno " +
                            "SET nome = ?, " +
                            "cpf = ?,"+
                            "datanascimento = ?,"+
                            "foto = ?"+
                            "WHERE matricula = ?");

            st.setString(1,a.getNome());
            st.setBytes(4,a.getFoto());
            st.setInt(5,a.getMatricula());
            st.executeUpdate();
            System.out.println(a.getMatricula());

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar aluno", e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deletar(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "DELETE FROM aluno WHERE matricula = ?");

            st.setInt(1, id);

            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir aluno", e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Professor> buscarTodos() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM aluno ORDER BY Matricula");
            rs = st.executeQuery();

            List<Professor> lista = new ArrayList<>();

            while (rs.next()) {
                Professor a = new Professor();
                a.setMatricula(rs.getInt("matricula"));
                a.setNome(rs.getString("nome"));
                a.setFoto(rs.getBytes("foto"));
                lista.add(a);
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(st);
            DB.closeResultset(rs);
        }
    }
}