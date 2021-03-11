package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.ConnectionUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CnxClient implements Initializable {

    @FXML
    private TextField nom;

    @FXML
    private TextField prenom;

    @FXML
    private TextField mail;

    @FXML
    private TextField adresse;

    @FXML
    private TextField tel;

    @FXML
    private TextField login;

    @FXML
    private TextField mdp;

    @FXML
    private TextField photo;

    @FXML
    private Button btn_inscrit;

    private Connection conn;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = ConnectionUtil.conDB();


    }
    private void ajouterClient() throws SQLException {
        String sql = "INSERT into client (login,pwd,nom,prenom,mail,tel,photo,adresse) " +
                "values (?,?,?,?,?,?,?,?) ";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, login.getText());
            preparedStatement.setString(2,mdp.getText());
            preparedStatement.setString(3, nom.getText());
            preparedStatement.setString(4, prenom.getText());
            preparedStatement.setString(5, mail.getText());
            preparedStatement.setString(6, tel.getText());
            preparedStatement.setString(7, photo.getText());
            preparedStatement.setString(8, adresse.getText());


            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }



    public void Action_inscrit(javafx.event.ActionEvent event) {
        if (event.getSource() == btn_inscrit) {
            try {
                ajouterClient();
                Alert aa = new Alert(Alert.AlertType.CONFIRMATION);
                aa.setContentText("Inscription réussite");
                aa.show();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                nom.setText("");
                prenom.setText("");
                adresse.setText("");
                mail.setText("");
                photo.setText("");
                mdp.setText("");
                login.setText("");
                tel.setText("");
            }
        }
    }
    @FXML
    void back_to_client_home(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/login.fxml"));
            Parent p=loader.load();
            Scene scene=new Scene(p);

            LoginController controller = loader.getController();

            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }


}
