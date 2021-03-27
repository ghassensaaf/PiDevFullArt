
package Controller;

        import entite.artiste;
        import entite.client;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.Node;
        import javafx.scene.Parent;
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
    @FXML
    void login(ActionEvent event) {
        if(logIn().equals("admin"))
        {
            try {

                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/admin.fxml"));
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
        }
        else if(logIn().equals("client"))
        {
            try {
                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/Client.fxml"));
                Parent p=loader.load();
                Scene scene=new Scene(p);

                ClientIController controller = loader.getController();
                controller.initData(txtlogin.getText());
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                stage.setScene(scene);
                stage.show();

            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
        else if(logIn().equals("artiste"))
        {
            try {

                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/artiste.fxml"));
                Parent p=loader.load();
                Scene scene=new Scene(p);

                artisteController controller = loader.getController();
                controller.initData(txtlogin.getText());
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
                preparedStatement.setString(2, convertPassMd5(mdp));
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    setLblError(Color.TOMATO, "Enter Correct Login/Password");
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
                preparedStatement.setString(2, convertPassMd5(mdp));
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
                preparedStatement.setString(2, convertPassMd5(mdp));
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
        System.out.println(convertPassMd5("salma"));
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

    @FXML
    void Action_inscription(ActionEvent event) {
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/Cnx_Client.fxml"));
            Parent p=loader.load();
            Scene scene=new Scene(p);


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

