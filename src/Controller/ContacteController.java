package Controller;



import entite.client;
import entite.commentaire;
import entite.message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.ConnectionUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ContacteController implements Initializable {

    @FXML
    private TextArea txt_msg;

    @FXML
    private Button bny_ajouter;

    @FXML
    private Button btn_supprimer;

    @FXML
    private TableView<message> tableMessage;

    @FXML
    private TableColumn<message, Integer> col_id_msg;

    @FXML
    private TableColumn<message, String> col_contenu_msg;

    @FXML
    private TableColumn<message, Timestamp> col_date_msg;

    @FXML
    private Label clientlogin;
    @FXML
    private TextField txtid_art;

    @FXML
    private TextField id_msg;
    @FXML
    private TextField id_client;


    private client client1;

    private Connection conn=null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    private ObservableList<message> list;




    public void initData(int i, String ll) {
        txtid_art.setText(String.valueOf(i));
        clientlogin.setText(ll);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = ConnectionUtil.conDB();
        try {

            populateTablemessage();
            ajouterMessage();
            supprimerMessage();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /*********************message*******************/
    private void populateTablemessage() throws SQLException {
        list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM message WHERE id_artiste_dest=? ";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, txtid_art.getText());


            resultSet=preparedStatement.executeQuery();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        while (resultSet.next()) {
            message rec = new message(resultSet.getInt("id_message"), resultSet.getString("contenu"), resultSet.getTimestamp("date"));
            list.add(rec);
        }
        col_id_msg.setCellValueFactory(new PropertyValueFactory<>("id_message"));
        col_contenu_msg.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        col_date_msg.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableMessage.setItems(list);
        tableMessage.refresh();


    }
    private client getClient() throws SQLException {
        String sql ="SELECT * FROM client where login= ?";
        client ccc=null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, clientlogin.getText());
            resultSet=preparedStatement.executeQuery();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        if (resultSet.next())
        {
            ccc = new client(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),resultSet.getInt(7),resultSet.getString(8),resultSet.getString(9));
        }
        return ccc;
    }

    private void ajouterMessage() throws SQLException {
        String sql = "INSERT into message (contenu,id_artiste_dest,id_client_exp) " +
                "values (?,?,?) ";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, txt_msg.getText());
            preparedStatement.setString(2, txtid_art.getText());
            preparedStatement.setString(3, id_client.getText());




            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
       populateTablemessage();
    }



    private void supprimerMessage() throws SQLException {
        String sql="delete from message where id_message = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, id_msg.getText());


            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        populateTablemessage();
    }


    @FXML
    void showselectedmsg(MouseEvent event) {
        message a=tableMessage.getSelectionModel().getSelectedItem();
        if(a!=null)
        {
            txt_msg.setText(String.valueOf(a.getContenu()));
           id_msg.setText(String.valueOf(a.getId_message()));

        }

    }

    @FXML
    void Action_msg(ActionEvent event) {
        if (event.getSource() == bny_ajouter) {
            try {
                ajouterMessage();
                Alert aa = new Alert(Alert.AlertType.CONFIRMATION);
                aa.setContentText("Message envoyé ");
                aa.show();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                txt_msg.setText("");
                id_msg.setText("");

            }
        }
   else  if (event.getSource() == btn_supprimer) {
            try {
                supprimerMessage();
                Alert aa = new Alert(Alert.AlertType.CONFIRMATION);
                aa.setContentText("Message supprimé ");
                aa.show();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                txt_msg.setText("");
                id_msg.setText("");

            }
        }
        }
    @FXML
    void back_to_client_home(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/Client.fxml"));
            Parent p=loader.load();
            Scene scene=new Scene(p);

            ClientIController controller = loader.getController();
            controller.initData(clientlogin.getText());
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
    void refresh(MouseEvent event) throws SQLException {

        populateTablemessage();
        client1=getClient();
        id_client.setText(String.valueOf(client1.getId_client()));

    }

}



