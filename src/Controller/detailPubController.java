package Controller;

import com.sun.org.apache.bcel.internal.generic.INEG;
import entite.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import util.ConnectionUtil;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class detailPubController implements Initializable {
    @FXML
    private TableView<jaime> tabJaimeArtiste;

    @FXML
    private TableView<jaime> tableJaimeClient;

    @FXML
    private TableView<commentaire> tab_commentaire;

    @FXML
    private TableColumn<commentaire, Integer> colid_com;

    @FXML
    private TableColumn<commentaire, String> colContenu_com;

    @FXML
    private TableColumn<commentaire, Integer> colnb_like_com;
    @FXML
    private TextArea txt_com;
    @FXML
    private Button btn_supprimer_com;

    @FXML
    private Button btn_modifier_com;

    @FXML
    private Button btn_ajouter_com;

    @FXML
    private TextField detailpub;
    @FXML
    private Label login;
    @FXML
    private TextField id_comm;


    @FXML
    private TableColumn<commentaire, Integer> coljaimeArtiste;


    @FXML
    private TableColumn<commentaire, Integer> coljaimeClient;
    @FXML
    private Label idPub;
    private Connection conn;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    private ObservableList<commentaire> list;
    private ObservableList<jaime> list1;


    public void initData(int i, String ll) {
        detailpub.setText(String.valueOf(i));
        login.setText(ll);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = ConnectionUtil.conDB();
        try {

            populateTablecommentaire();
            populateTablejaimeArtiste();
            populateTablejaimeClient();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /*********************COMMENTAIRE*******************/
    private void populateTablecommentaire() throws SQLException {
        list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM commentaire WHERE id_pub=?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, detailpub.getText());

            resultSet=preparedStatement.executeQuery();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        while (resultSet.next()) {
            commentaire rec = new commentaire(resultSet.getInt("id_commentaire"), resultSet.getString("contenu"), resultSet.getInt("nb_like"));
            list.add(rec);
        }
        colid_com.setCellValueFactory(new PropertyValueFactory<>("id_commentaire"));
        colContenu_com.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        colnb_like_com.setCellValueFactory(new PropertyValueFactory<>("nb_like"));
        tab_commentaire.setItems(list);

    }

    /*************AJOUTER COMMENTAIRE**************/
    private void ajouterCommentaire() throws SQLException {
        String sql = "INSERT into commentaire (contenu,nb_like,id_pub) " +
                "values (?,?,?) ";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, txt_com.getText());
            preparedStatement.setString(2, "0");
            preparedStatement.setString(3, detailpub.getText());

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        populateTablecommentaire();
        populateTablejaimeArtiste();
    }

    @FXML
    void Action_com(ActionEvent event) {
        if (event.getSource() == btn_ajouter_com) {
            try {
                ajouterCommentaire();
                Alert aa = new Alert(Alert.AlertType.CONFIRMATION);
                aa.setContentText("Commentaire ajoutée");
                aa.show();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                txt_com.setText("");
                id_comm.setText("");
            }
        }
        else if (event.getSource() == btn_modifier_com) {
            try {
                editCommentaire();
                Alert aa = new Alert(Alert.AlertType.CONFIRMATION);
                aa.setContentText("Commentaire modifié");
                aa.show();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                txt_com.setText("");
                id_comm.setText("");

            }
        }
        else if (event.getSource() == btn_supprimer_com) {
            try {
                deleteCommentaire();
                Alert aa = new Alert(Alert.AlertType.CONFIRMATION);
                aa.setContentText("Commentaire supprimer");
                aa.show();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                txt_com.setText("");
                id_comm.setText("");
            }
        }
    }


    @FXML
    void showselected(MouseEvent event) {
        commentaire a=tab_commentaire.getSelectionModel().getSelectedItem();
        if(a!=null)
        {
            txt_com.setText(String.valueOf(a.getContenu()));
            id_comm.setText(String.valueOf(a.getId_commentaire()));



        }

    }
    private void editCommentaire() throws SQLException {
//        Annonce annonce = new Annonce(1,txttitre.getText(),txtdesc.getText(),Integer.parseInt(txtprixmin.getText()),Integer.parseInt(txtprixmax.getText()), Date.from(Instant.from(txtdate.getValue().atStartOfDay(ZoneId.systemDefault()))),txtadresse.getText(),true,0,2);
        String sql="UPDATE commentaire set  contenu= ?  where id_commentaire=?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, txt_com.getText());
            preparedStatement.setString(2, id_comm.getText());



            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        populateTablecommentaire();
        tab_commentaire.refresh();
    }

    private void deleteCommentaire() throws SQLException {
//        Annonce annonce = new Annonce(1,txttitre.getText(),txtdesc.getText(),Integer.parseInt(txtprixmin.getText()),Integer.parseInt(txtprixmax.getText()), Date.from(Instant.from(txtdate.getValue().atStartOfDay(ZoneId.systemDefault()))),txtadresse.getText(),true,0,2);
        String sql="delete from commentaire where id_commentaire = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, id_comm.getText());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        populateTablecommentaire();
    }

    private void populateTablejaimeArtiste() throws SQLException {
        list1 = FXCollections.observableArrayList();
        String sql = "SELECT * FROM jaime WHERE id_pub=? and id_artiste IS NOT NULL ";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, detailpub.getText());

            resultSet=preparedStatement.executeQuery();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        while (resultSet.next()) {
            jaime rec = new jaime(resultSet.getInt("id_like"), resultSet.getInt("id_artiste"), resultSet.getInt("id_client") , resultSet.getInt("id_pub"));
            list1.add(rec);

        }
        coljaimeArtiste.setCellValueFactory(new PropertyValueFactory<>("id_artiste"));
        tabJaimeArtiste.setItems(list1);

    }

    private void populateTablejaimeClient() throws SQLException {
        list1 = FXCollections.observableArrayList();
        String sql = "SELECT * FROM jaime WHERE id_pub=? and id_client IS NOT NULL ";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, detailpub.getText());

            resultSet=preparedStatement.executeQuery();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        while (resultSet.next()) {
            jaime rec = new jaime(resultSet.getInt("id_like"), resultSet.getInt("id_artiste"), resultSet.getInt("id_client") , resultSet.getInt("id_pub"));
            list1.add(rec);

        }
        coljaimeClient.setCellValueFactory(new PropertyValueFactory<>("id_client"));
        tableJaimeClient.setItems(list1);

    }



    @FXML
    void refresh(MouseEvent event) throws SQLException {

        populateTablejaimeArtiste();
        populateTablecommentaire();
        populateTablejaimeClient();

    }
}



