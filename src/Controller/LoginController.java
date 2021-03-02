
package Controller;

        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.Node;
        import javafx.scene.Scene;
        import javafx.scene.control.Button;
        import javafx.scene.control.Label;
        import javafx.scene.control.PasswordField;
        import javafx.scene.control.RadioButton;
        import javafx.scene.control.TextField;
        import javafx.scene.control.ToggleGroup;
        import javafx.scene.paint.Color;
        import javafx.stage.Stage;
        import util.ConnectionUtil;

        import javax.swing.*;
        import java.io.IOException;
        import java.net.URL;
        import java.sql.Connection;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button btnlogin;

    @FXML
    private TextField txtlogin;

    @FXML
    private PasswordField pwd;

    @FXML
    private Label lblDB;

    @FXML
    private RadioButton rd_artiste;

    @FXML
    private RadioButton rd_client;

    @FXML
    private RadioButton rd_admin;

    @FXML
    private Label lblErrors;

    @FXML
    private ToggleGroup user;
    Connection conn=null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @FXML
    void login(ActionEvent event) {
        if(logIn().equals("admin"))
        {
            try {

                //add you loading or delays - ;-)
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                //stage.setMaximized(true);
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/reclamation.fxml")));
                stage.setScene(scene);
                stage.show();

            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
        else if(logIn().equals("client"))
        {
            try {

                //add you loading or delays - ;-)
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                //stage.setMaximized(true);
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/message.fxml")));
                stage.setScene(scene);
                stage.show();

            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
        else if(logIn().equals("artiste"))
        {
            try {

                //add you loading or delays - ;-)
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                //stage.setMaximized(true);
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/interaction.fxml")));
                stage.setScene(scene);
                stage.show();

            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }


    }
    String logIn()
    {
        String login= txtlogin.getText();
        String mdp=pwd.getText();
        if(login.equals("") || mdp.equals(""))
        {
            lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Remplir les champs");
        }
        else if(rd_admin.isSelected())
        {
            String sql = "SELECT * FROM admin Where login = ? and pwd = ?";
            try {
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, mdp);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    setLblError(Color.TOMATO, "Enter Correct Email/Password");
                } else {
                    setLblError(Color.GREEN, "Login Successful..Redirecting..");
                    return "admin";
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
        else if(rd_artiste.isSelected())
        {
            String sql = "SELECT * FROM artiste Where login = ? and pwd = ?";
            try {
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, mdp);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    setLblError(Color.TOMATO, "Enter Correct Email/Password");
                } else {
                    setLblError(Color.GREEN, "Login Successful..Redirecting..");
                    return "artiste";
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
        else if(rd_client.isSelected())
        {
            String sql = "SELECT * FROM client Where login = ? and pwd = ?";
            try {
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, mdp);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    setLblError(Color.TOMATO, "Enter Correct Email/Password");
                } else {
                    setLblError(Color.GREEN, "Login Successful..Redirecting..");
                    return "client";
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
        else
        {
            setLblError(Color.TOMATO,"Select User Type");
        }
        return "no";
    }


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
    private void setLblError(Color color, String text) {
        lblErrors.setTextFill(color);
        lblErrors.setText(text);
        System.out.println(text);
    }
}

