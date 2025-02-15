package com.example.projetojavafx;

import com.example.projetojavafx.model.dao.DAOFactory;
import com.example.projetojavafx.model.entities.Aluno;
import com.example.projetojavafx.util.Alertas;
import com.example.projetojavafx.util.Restricoes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class CadastroAlunoController implements Initializable {
    @FXML
    private TextField nome;
    @FXML
    private TextField cpf;
    @FXML
    private DatePicker data;
    @FXML
    private ImageView foto;
    @FXML
    private Button salvar;

    File file;
    @FXML
    void onFotoClick(){
        FileChooser fc = new FileChooser();
        file = fc.showOpenDialog(ApplicationController.getStage().getScene().getWindow());
        if(file!=null){
            foto.setImage(new Image(file.toURI().toString()));
        }


    }
    @FXML
    void onSalvarClick() throws IOException {
        Aluno aluno = new Aluno();
        aluno.setNome(nome.getText());
        aluno.setCPF(cpf.getText());
        LocalDate localDate = data.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        aluno.setDataNascimento(date);
        if(file!=null){
            byte[] fileBytes = Files.readAllBytes(file.toPath());
            aluno.setFoto(fileBytes);
        }
        DAOFactory.createAlunoDao().inserir(aluno);
        Alertas.mostrarAlerta(null,null,"Aluno cadastrado com sucesso!", Alert.AlertType.INFORMATION);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Restricoes.CPF(cpf);
    }
}
