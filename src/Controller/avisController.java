package Controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import util.ConnectionUtil;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.Connection;
import java.util.Observable;
import java.util.ResourceBundle;
import entite.avis;

public class avisController implements Initializable {

    @FXML
    private TableView<avis> tab_avis;

    @FXML
    private TableColumn<avis, Integer> col_id_avis;

    @FXML
    private TableColumn<avis, Integer> col_id_artiste;

    @FXML
    private TableColumn<?, ?> col_note;

    @FXML
    private TableColumn<?, ?> col_contenu;

    @FXML
    private ComboBox<?> combox_artiste;

    @FXML
    private TextField txt_note;

    @FXML
    private TextArea txt_contenu;

    @FXML
    private Button btn_ajouter;

    @FXML
    private Button bnt_modifier;

    @FXML
    private Button btn_supprimer;
    @FXML
    private Label lblDB;
    @FXML
    void handleButtonAction(ActionEvent event) {

    }

    Connection conn=null;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn= ConnectionUtil.conDB();
        if (conn == null) {
            lblDB.setTextFill(Color.TOMATO);
            lblDB.setText("Server Error : Check");
        } else {
            lblDB.setTextFill(Color.GREEN);
            lblDB.setText("Connected to DB");
        }
    }




}
