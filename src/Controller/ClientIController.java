package Controller;

import entite.Annonce;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import util.ConnectionUtil;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;
import java.util.ResourceBundle;

public class ClientIController implements Initializable {

    @FXML
    private TableView<Annonce> tableAnnonce;

    @FXML
    private TableColumn<Annonce, Integer> colid;

    @FXML
    private TableColumn<Annonce, String> coltitre;

    @FXML
    private TableColumn<Annonce, Integer> colprixmin;

    @FXML
    private TableColumn<Annonce, Integer> colprixmax;

    @FXML
    private TableColumn<Annonce, String > coletat;

    @FXML
    private TableColumn<Annonce, Integer> colnbcandidat;

    private Connection conn=null;
    ResultSet resultSet = null;
    private ObservableList <Annonce> list;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = ConnectionUtil.conDB();
        try {
            populateTableAnnonce();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void populateTableAnnonce() throws SQLException {
        list= FXCollections.observableArrayList();
        String sql ="SELECT * FROM annonce";
        resultSet=conn.createStatement().executeQuery(sql);
        while(resultSet.next())
        {
            Annonce annonce=new Annonce();
            annonce.setId_annonce(resultSet.getInt("id_annonce"));
            annonce.setId_client(resultSet.getInt("id_client"));
            annonce.setTitre(resultSet.getString("titre"));
            annonce.setDescription(resultSet.getString("description"));
            annonce.setPrix_min(resultSet.getInt("prix_min"));
            annonce.setPrix_max(resultSet.getInt("prix_max"));
            annonce.setDate(resultSet.getDate("date"));
            annonce.setAdresse(resultSet.getString("adresse"));
            annonce.setEtat(resultSet.getBoolean("etat"));
            annonce.setDate_annonce(resultSet.getTimestamp("date_annonce"));
            annonce.setNb_candidature(resultSet.getInt("nb_candidature"));
            annonce.setId_type_eve(resultSet.getInt("id_type_eve"));
            list.add(annonce);
        }
        colid.setCellValueFactory(new PropertyValueFactory<>("id_annonce"));
        coltitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        colprixmax.setCellValueFactory(new PropertyValueFactory<>("prix_max"));
        colprixmin.setCellValueFactory(new PropertyValueFactory<>("prix_min"));
        coletat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        colnbcandidat.setCellValueFactory(new PropertyValueFactory<>("nb_candidature"));
        tableAnnonce.setItems(list);

    }

}
