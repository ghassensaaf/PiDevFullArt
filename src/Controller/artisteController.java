package Controller;

import entite.Annonce;
import entite.concert;
import entite.evenement;
import entite.type_pub;
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

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class artisteController implements Initializable {

    @FXML
    private ComboBox<Integer> txttypepub;

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
    @FXML
    private TextField idconcert;

    @FXML
    private TextField lieuconcert;

    @FXML
    private TextField idrecherche;

    @FXML
    private DatePicker dateconcert;

    @FXML
    private TableView<concert> tabconcert;

    @FXML
    private TableColumn<concert, Integer> colconcert;

    @FXML
    private TableColumn<concert, Integer> colartiste;

    @FXML
    private TableColumn<concert, String> colplace;

    @FXML
    private Button addconcert;

    @FXML
    private Button editconcert;

    @FXML
    private Button deleteconcert;

    @FXML
    private Button consulterconcert;


    private Connection conn = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement1 = null;
    private ObservableList<publication> list;
    private ObservableList<concert> list1;

    public String initData(String login) {
        artistlogin.setText(login);
        return login;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = ConnectionUtil.conDB();
        try {
            ArrayList <type_pub> listepub=gettypepub();
            for (type_pub p: listepub)
            {
                txttypepub.getItems().add(p.getId_type());
            }

            afficherPublication();
            afficherconcert();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void afficherPublication() throws SQLException {
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
        else if (event.getSource() == addconcert) {
            try {
                ajouterconcert();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                lieuconcert.setText("");
                dateconcert.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                txtcontenupub.setText("");
            }
        }

        else if (event.getSource() == deleteconcert) {
            try {
                supprimerconcert();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                lieuconcert.setText("");
                dateconcert.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                txtcontenupub.setText("");
            }
        }
        else if(event.getSource()==editconcert)
        {
            try {
                modifierconcert();
                Alert al=new Alert(Alert.AlertType.CONFIRMATION);
                al.setContentText("Concert bien modifié");
                al.show();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            finally {

                lieuconcert.setText("");
                dateconcert.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
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
            preparedStatement.setString(2, txttypepub.getValue().toString());
            preparedStatement.setString(3, txttitrepub.getText());
            preparedStatement.setString(4, txtcontenupub.getText());
            preparedStatement.setString(5, "0");

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        afficherPublication();
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
        afficherPublication();
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
        afficherPublication();
    }

    private void afficherconcert() throws SQLException {
        list1 = FXCollections.observableArrayList();
        String sql = "SELECT * FROM concert";
        resultSet = conn.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            concert crt = new concert(resultSet.getInt("id_concert"), resultSet.getInt("id_artiste"), resultSet.getString("lieu"), resultSet.getDate("date"));
            list1.add(crt);
        }
        colconcert.setCellValueFactory(new PropertyValueFactory<>("id_concert"));
        colartiste.setCellValueFactory(new PropertyValueFactory<>("id_artiste"));
        colplace.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tabconcert.setItems(list1);

    }

    private void ajouterconcert() throws SQLException {
        String sql = "INSERT into concert ( id_artiste, lieu, date) " +
                "values (?,?,?) ";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, "2");
            preparedStatement.setString(2,lieuconcert.getText());
            preparedStatement.setString(3, dateconcert.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        afficherconcert();
    }

    @FXML
    void click1(MouseEvent event) {
        concert c = tabconcert.getSelectionModel().getSelectedItem();
        if (c != null) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            idconcert.setText(String.valueOf(c.getId_concert()));
            lieuconcert.setText(c.getLieu());
            dateconcert.setValue(LocalDate.parse(format.format(c.getDate())));
        }
    }

    private void supprimerconcert() throws SQLException {
        String sql="delete from concert where id_concert = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, idconcert.getText());
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        afficherconcert();
    }


    private void modifierconcert() throws SQLException {
        String sql="UPDATE concert set  id_artiste= ? , lieu= ? , date = ?  where id_concert= ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, "2");
            preparedStatement.setString(2, lieuconcert.getText());
            preparedStatement.setString(3, dateconcert.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            preparedStatement.setString(4, idconcert.getText());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        afficherconcert();
    }

    ArrayList<type_pub> gettypepub() throws SQLException {
        ArrayList<type_pub>  liste = new ArrayList<>();
        String sql =" select * from type_pub ";
        resultSet=conn.createStatement().executeQuery(sql);
        while(resultSet.next())
        {
            type_pub p=new type_pub(resultSet.getInt(1),resultSet.getString(2));
            liste.add(p);
        }
        return liste;
    }
}

