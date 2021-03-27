package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sun.security.provider.MD5;
import util.ConnectionUtil;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.html.ImageView;
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

    @FXML
    private ImageView photo;

    @FXML
    void taswira(ActionEvent event) {
        JFileChooser filec = new JFileChooser();
        filec.setCurrentDirectory(new File(System.getProperty("user.home")));

        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("*.Images", "jpg","png","gif");
        filec.addChoosableFileFilter(fileFilter);

        int fileState = filec.showSaveDialog(null);
        if(fileState == JFileChooser.APPROVE_OPTION){
            File selectedFile = filec.getSelectedFile();
            String path = selectedFile.getAbsolutePath();









        }


    }
}
