package Controller;

import entite.publication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import util.ConnectionUtil;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class detailPubController implements Initializable {

    @FXML
    private TextField detailpub;
    @FXML
    private Label login;

    private Connection conn;

    public void initData(int i,String ll)
    {
        detailpub.setText(String.valueOf(i));
        login.setText(ll);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = ConnectionUtil.conDB();
    }
}
