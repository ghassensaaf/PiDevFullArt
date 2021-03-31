package Controller;


import entite.evenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sun.security.provider.MD5;
import util.ConnectionUtil;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    private PasswordField mdp;
    @FXML
    private Button taswira;

    @FXML
    private Button btn_inscrit;
    @FXML
    private ImageView imageView;

    private Image img;

    @FXML
    private TextArea textArea;

    private Connection conn;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = ConnectionUtil.conDB();
        try {
            ajouterClient();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
    public static String convertPassMd5(String pass) {
        String password = null;
        MessageDigest mdEnc;
        try {
            mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(pass.getBytes(), 0, pass.length());
            pass = new BigInteger(1, mdEnc.digest()).toString(16);
            while (pass.length() < 32) {
                pass = "0" + pass;
            }
            password = pass;
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        return password;
    }
    private void ajouterClient() throws SQLException {
        String sql = "INSERT into client (login,pwd,nom,prenom,mail,tel,photo,adresse) " +
                "values (?,?,?,?,?,?,?,?) ";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, login.getText());
            preparedStatement.setString(2, convertPassMd5(mdp.getText()));
            preparedStatement.setString(3, nom.getText());
            preparedStatement.setString(4, prenom.getText());
            preparedStatement.setString(5, mail.getText());
            preparedStatement.setString(6, tel.getText());
            preparedStatement.setString(7, textArea.getText());
            preparedStatement.setString(8, adresse.getText());


            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }


    @FXML
    private Label erreur;

    public void Action_inscrit(javafx.event.ActionEvent event) {
        if (event.getSource() == btn_inscrit) {

            if (nom.getText().length() == 0) {
                nom.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
                new animatefx.animation.Shake(nom).play();
                erreur.setText("Champ Obligatoire");
                erreur.setStyle("-fx-text-fill: red");
                new animatefx.animation.FadeInDown(erreur).play();
            } else {
                nom.setStyle(null);
                erreur.setText("");
            }

            if (prenom.getText().length() == 0) {
                prenom.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
                new animatefx.animation.Shake(prenom).play();
                erreur.setText("Champ Obligatoire");
                erreur.setStyle("-fx-text-fill: red");
                new animatefx.animation.FadeInDown(erreur).play();
            } else {
                prenom.setStyle(null);
                erreur.setText("");
            }

            if (adresse.getText().length() == 0) {
                adresse.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
                new animatefx.animation.Shake(adresse).play();
                erreur.setText("Champ Obligatoire");
                erreur.setStyle("-fx-text-fill: red");
                new animatefx.animation.FadeInDown(erreur).play();
            } else {
                adresse.setStyle(null);
                erreur.setText("");
            }

            if (tel.getText().length() == 0) {
                tel.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
                new animatefx.animation.Shake(tel).play();
                erreur.setText("Champ Obligatoire");
                erreur.setStyle("-fx-text-fill: red");
                new animatefx.animation.FadeInDown(erreur).play();
            } else {
                tel.setStyle(null);
                erreur.setText("");
            }

            if (login.getText().length() == 0) {
                login.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
                new animatefx.animation.Shake(login).play();
                erreur.setText("Champ Obligatoire");
                erreur.setStyle("-fx-text-fill: red");
                new animatefx.animation.FadeInDown(erreur).play();
            } else {
                login.setStyle(null);
                erreur.setText("");
            }

            if (mdp.getText().length() == 0) {
                mdp.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
                new animatefx.animation.Shake(mdp).play();
                erreur.setText("Champ Obligatoire");
                erreur.setStyle("-fx-text-fill: red");
                new animatefx.animation.FadeInDown(erreur).play();
            } else {
                mdp.setStyle(null);
                erreur.setText("");
            }

            if (mail.getText().length() == 0) {
                mail.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
                new animatefx.animation.Shake(mail).play();
                erreur.setText("Champ Obligatoire");
                erreur.setStyle("-fx-text-fill: red");
                new animatefx.animation.FadeInDown(erreur).play();
            } else {
                mail.setStyle(null);
                erreur.setText("");


                try {
                    ajouterClient();
                    Alert aa = new Alert(Alert.AlertType.CONFIRMATION);
                    aa.setContentText("Inscription réussite");
                    aa.show();

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/fxml/login.fxml"));
                    Parent p = loader.load();
                    Scene scene = new Scene(p);


                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.close();
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException | SQLException ex) {
                    System.err.println(ex.getMessage());
                }
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



    @FXML
    void taswira(ActionEvent event) {

      FileChooser fc = new FileChooser();
      File selectedFile = fc.showOpenDialog(null);

      if(selectedFile != null) {
      textArea.setText(selectedFile.getAbsolutePath());
      img = new Image(selectedFile.toURI().toString(),160,160,true,true,true);

      imageView.setImage(img);
      imageView.setFitHeight(160);
      imageView.setFitWidth(160);



      }




    }
















        }




