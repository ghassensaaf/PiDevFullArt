package Controller;


import com.sun.istack.internal.logging.Logger;
import entite.commentaire;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import entite.Email;
import javafx.stage.Stage;
import util.ConnectionUtil;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class forgot_passController implements Initializable {

    @FXML
    private TextField tfemail;

    @FXML
    private Button send_mail;
    private Connection conn;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement1 = null;
    ResultSet resultSet = null;
    @FXML
    private TextField tfcode;
    @FXML
    private PasswordField new_pass;
    @FXML
    void send_email(ActionEvent event) throws Exception {
        System.out.println("salem1");
        Email email = new Email();
        Random rand = new Random();
        int rand_int1 = rand.nextInt(899999)+100000;
        email.sendEmail(tfemail.getText(), "Récupération de mot de passe", "your code is <strong>"+String.valueOf(rand_int1)+"</strong>");
        add_code(tfemail.getText(),rand_int1);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = ConnectionUtil.conDB();
    }
    public void add_code(String email, int code)
    {
        String sql="insert into forgot (client_mail,secret_code) values(?,?)";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, String.valueOf(code));

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    @FXML
    void update_pass(ActionEvent event) throws SQLException {
        String sql="select * from forgot where client_mail= ?";
        int x=0;
        int scode=0;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, tfemail.getText());
            resultSet=preparedStatement.executeQuery();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        while (resultSet.next()) {
            x++;
            scode=resultSet.getInt("secret_code");
        }
        if(x==0)
        {
            Alert aa=new Alert(Alert.AlertType.ERROR);
            aa.setContentText("code non envoyer, renvoyer le code puis resseyez");
            aa.show();
        }
        else
        {
            if(tfcode.getText().equals(String.valueOf(scode)))
            {
                String sql1="update client set pwd = ? where mail = ?";
                String sql2="delete from forgot where client_mail = ?";

                try {
                    preparedStatement = conn.prepareStatement(sql1);
                    preparedStatement1 = conn.prepareStatement(sql2);
                    preparedStatement1.setString(1,tfemail.getText());
                    preparedStatement.setString(1, convertPassMd5(new_pass.getText()));
                    preparedStatement.setString(2, tfemail.getText());
                    preparedStatement.executeUpdate();
                    preparedStatement1.executeUpdate();
                    Alert aa=new Alert(Alert.AlertType.CONFIRMATION);
                    aa.setContentText("Mp modifié");
                    aa.show();
                    try {

                        FXMLLoader loader=new FXMLLoader();
                        loader.setLocation(getClass().getResource("/fxml/login.fxml"));
                        Parent p=loader.load();
                        Scene scene=new Scene(p);

//                adminController controller = loader.getController();
//                controller.initData(txtlogin.getText());
                        Node node = (Node) event.getSource();
                        Stage stage = (Stage) node.getScene().getWindow();
                        stage.close();
                        stage.setScene(scene);
                        stage.show();

                    } catch (IOException ex) {
                        System.err.println(ex.getMessage());
                    }
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
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
}

