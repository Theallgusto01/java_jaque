package com.example.projetojavafx;
package com.example.projetojavafx;

import com.example.projetojavafx.model.dao.DAOFactory;
import com.example.projetojavafx.model.entities.Aluno;
import com.example.projetojavafx.model.entities.Professor;
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
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class CadatroProfessorController {

    @FXML
    private TextField nome;
    @FXML
    private TextField matricula;
    @FXML
    private ImageView foto;
    @FXML
    private Button salvar;


    File file;
    @FXML
    void onFotoClick(){
        FileChooser fc = new FileChooser();
        file = fc.showOpenDialog(ApplicationController.getStage().getScene().getWindow());
        if (file!=null){
            foto.setImage(new Image(file.toURI().toString()));
        }
    }



    void onSalvarClick() throws IOException{
        Professor professor = new Professor();
        professor.setNome(nome.getText());
        professor.setMatricula(Integer.valueOf(matricula.getText()));
        if (file!=null){
            byte[] fileBytes = Files.readAllBytes(file.toPath());
            professor.setFoto(fileBytes);
        }
        DAOFactory.createProfessorDao().inserir(professor);
        /
        // Alertas.mostrarAlerta();
    }

}
