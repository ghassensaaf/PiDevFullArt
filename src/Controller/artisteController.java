package Controller;

import entite.Annonce;
import entite.publication;
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
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class artisteController implements Initializable {

    @FXML
    private ComboBox<?> txttypepub;

    @FXML
    private TextField txttitrepub;

    @FXML
    private TextField idpub;

    @FXML
    private TextArea txtcontenupub;

    @FXML
    private TableView<publication> tabpub;

    @FXML
    private TableColumn<publication, Integer> colid;

    @FXML
    private TableColumn<publication, Integer> coltype;

    @FXML
    private TableColumn<publication, String> coltitre;

    @FXML
    private TableColumn<publication, String> colcontenu;

    @FXML
    private TableColumn<publication, Timestamp> coldate;

    @FXML
    private TableColumn<publication, Integer> col_like;

    @FXML
    private Button addpub;

    @FXML
    private Button editpub;

    @FXML
    private Button deletepub;
    @FXML
    private Label artistlogin;

    private Connection conn = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement1 = null;
    private ObservableList<publication> list;

    public String initData(String login) {
        artistlogin.setText(login);
        return login;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = ConnectionUtil.conDB();
        try {
            populateTablePublication();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void populateTablePublication() throws SQLException {
        list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM publication";
        resultSet = conn.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            publication pub = new publication(resultSet.getInt("id_pub"), resultSet.getInt("id_artiste"), resultSet.getInt("id_type"), resultSet.getString("titre"), resultSet.getString("contenu"), resultSet.getTimestamp("date_pub"), resultSet.getInt("nb_like"));
            list.add(pub);
        }
        colid.setCellValueFactory(new PropertyValueFactory<>("id_pub"));
        coltitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        colcontenu.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        coltype.setCellValueFactory(new PropertyValueFactory<>("id_type"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("date_pub"));
        col_like.setCellValueFactory(new PropertyValueFactory<>("nb_like"));
        tabpub.setItems(list);

    }

    @FXML
    void btnaction(ActionEvent event) {
        if (event.getSource() == addpub) {
            try {
                addPub();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                txttypepub.getAccessibleText();
                txttitrepub.setText("");
                txtcontenupub.setText("");
            }
        }
        else if (event.getSource() == deletepub) {
            try {
                supprimerpub();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                txttypepub.getAccessibleText();
                txttitrepub.setText("");
                txtcontenupub.setText("");
            }
        }

        else if(event.getSource()==editpub)
        {
            try {
                modifierpub();
                Alert al=new Alert(Alert.AlertType.CONFIRMATION);
                al.setContentText("publication bien modifié");
                al.show();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            finally {
                txttypepub.getAccessibleText();
                txttitrepub.setText("");
                txtcontenupub.setText("");
            }
        }



    }

    private void addPub() throws SQLException {
        String sql = "INSERT into publication ( id_artiste, id_type, titre, contenu,nb_like) " +
                "values (?,?,?,?,?) ";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, "2");
            preparedStatement.setString(2, "3");
            preparedStatement.setString(3, txttitrepub.getText());
            preparedStatement.setString(4, txtcontenupub.getText());
            preparedStatement.setString(5, "0");

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        populateTablePublication();
    }

    @FXML
    void click(MouseEvent event) {
        publication p = tabpub.getSelectionModel().getSelectedItem();
        if (p != null) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            idpub.setText(String.valueOf(p.getId_pub()));
            txttitrepub.setText(p.getTitre());
            txtcontenupub.setText(p.getContenu());
        }
    }

    private void supprimerpub() throws SQLException {
        String sql="delete from publication where id_pub = ?";
        String sql1="delete from jaime where id_pub= ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement1 = conn.prepareStatement(sql1);
            preparedStatement.setString(1, idpub.getText());
            preparedStatement1.setString(1, idpub.getText());
            preparedStatement1.executeUpdate();
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        populateTablePublication();
    }

    private void modifierpub() throws SQLException {
        String sql="UPDATE publication set  id_type= ? , titre= ? , contenu = ?  where id_pub= ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, "3");
            preparedStatement.setString(2, txttitrepub.getText());
            preparedStatement.setString(3, txtcontenupub.getText());
            preparedStatement.setString(4, idpub.getText());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        populateTablePublication();
    }
}

