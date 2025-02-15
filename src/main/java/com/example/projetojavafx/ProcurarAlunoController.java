package com.example.projetojavafx;


import com.example.projetojavafx.model.dao.DAOFactory;
import com.example.projetojavafx.model.entities.Aluno;
import com.example.projetojavafx.util.Alertas;
import com.example.projetojavafx.util.Restricoes;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class ProcurarAlunoController implements Initializable {
        @FXML
        private TextField nome;

        @FXML
        private ComboBox matricula;
        @FXML
        private TextField cpf;

        @FXML
        private DatePicker data;

        @FXML
        private ImageView foto;

        @FXML
        private Button buscar;
    @FXML
    private Button atualizar;

    @FXML
    private Button deletar;

    private Aluno aluno;



        @FXML
        public void onBuscarClick(){

            if(matricula.getValue()!=null) {
                aluno = DAOFactory.createAlunoDao().exibirPorId((Integer) matricula.getValue());
                if (aluno != null) {
                    nome.setText(aluno.getNome());
                    cpf.setText(aluno.getCPF());
                    LocalDate date = LocalDate.parse(aluno.getDataNascimento().toString());
                    data.setValue(date);
                    if (aluno.getFoto() != null) {
                        Image image = new Image(new ByteArrayInputStream(aluno.getFoto()));
                        foto.setImage(image);
                    }
                    nome.setEditable(true);
                    cpf.setEditable(true);
                    data.setEditable(true);
                    atualizar.setVisible(true);
                    deletar.setVisible(true);
                } else
                    Alertas.mostrarAlerta(null, null, "Aluno não encontrado!", Alert.AlertType.ERROR);
            }
        }

    public void onAtualizarClick(){
        aluno.setNome(nome.getText());
        aluno.setCPF(cpf.getText());
        LocalDate localDate = data.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        aluno.setDataNascimento(date);
        if(file!=null){
            byte[] fileBytes = new byte[0];
            try {
                fileBytes = Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            aluno.setFoto(fileBytes);
        }
       DAOFactory.createAlunoDao().atualizar(aluno);
        Alertas.mostrarAlerta(null,null,"Aluno atualizado com sucesso!", Alert.AlertType.INFORMATION);

    }

    public void onDeletarClick(){

        DAOFactory.createAlunoDao().deletar(aluno.getMatricula());
        apagarCampos();
        Alertas.mostrarAlerta(null,null,"Aluno deletado com sucesso!", Alert.AlertType.INFORMATION);
        atualizar.setVisible(false);
        deletar.setVisible(false);
        matricula.getItems().remove(aluno.getMatricula());




    }

    public void apagarCampos(){

            nome.clear();
            cpf.clear();
            data.setValue(null);
            foto.setImage(null);
            matricula.setValue(null);
    }


    File file;
    @FXML
    void onFotoClick(){
        FileChooser fc = new FileChooser();
        file = fc.showOpenDialog(ApplicationController.getStage().getScene().getWindow());
        if(file!=null){
            foto.setImage(new Image(file.toURI().toString()));
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        atualizar.setVisible(false);
        deletar.setVisible(false);
        nome.setEditable(false);
        cpf.setEditable(false);
        data.setEditable(false);

        List<Aluno> lista = DAOFactory.createAlunoDao().buscarTodos();
        List<Integer> matriculas = new ArrayList<Integer>();
        for (Aluno aluno : lista) {
            matriculas.add(aluno.getMatricula());
        }
        ObservableList<Integer> obs = FXCollections.observableArrayList(matriculas);


        matricula.setItems(obs);

        Restricoes.CPF(cpf);



    }
}



