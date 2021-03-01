package Controller;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import util.ConnectionUtil;
import javafx.scene.control.Label;

public class LoginController implements Initializable{


    @FXML
    private Button btnlogin;

    @FXML
    private TextField txtlogin;

    @FXML
    private PasswordField pwd;

    @FXML
    void login(ActionEvent event) {

    }
    @FXML
    private Label lblErrors;

    Connection conn =null;


@Override
    public void initialize(URL url, ResourceBundle rb) {
        conn=ConnectionUtil.conDB();
        if (conn == null) {
            lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Server Error : Check");
        } else {
            lblErrors.setTextFill(Color.GREEN);
            lblErrors.setText("Server is up : Good to go");
        }
    }

    private void setLblError(Color color, String text) {
        lblErrors.setTextFill(color);
        lblErrors.setText(text);
        System.out.println(text);
    }
}
