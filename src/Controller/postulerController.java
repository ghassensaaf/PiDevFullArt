package Controller;

import entite.Candidature;
import entite.artiste;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.ConnectionUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class postulerController implements Initializable {

    @FXML
    private TableView<Candidature> tabcandidature;

    @FXML
    private TableColumn<Candidature, Integer> colid;

    @FXML
    private TableColumn<Candidature, String> colcontenu;

    @FXML
    private TableColumn<Candidature, Integer> colprix;

    @FXML
    private TableColumn<Candidature, Date> coldate;

    @FXML
    private Label clientlogin;

    @FXML
    private Button btnretour;

    @FXML
    private TextField txtidann;
    @FXML
    private TextField taher;

    @FXML
    private Label lbltitreann;

    @FXML
    private Button btnaddann;

    @FXML
    private Button btneditann;

    @FXML
    private Button btndeleteann;
    artiste aa=null;
    private Connection conn=null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    private ObservableList<Candidature> list;
    int idann=-1;

    public void initData(int i, String ll) {
        txtidann.setText(String.valueOf(i));
        clientlogin.setText(ll);

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn= ConnectionUtil.conDB();

    }

    private void populateTableCandidature() throws SQLException {
        list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM candidature WHERE id_artiste =? and id_annonce= ? ";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,taher.getText());
            preparedStatement.setString(2, txtidann.getText());
            resultSet=preparedStatement.executeQuery();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        while (resultSet.next()) {
            Candidature candidature=new Candidature(resultSet.getInt("id_candidature"),resultSet.getInt("id_annonce"),resultSet.getString("contenu"),resultSet.getInt("prix"),resultSet.getInt("etat"),resultSet.getInt("id_artiste"),resultSet.getTimestamp("date"));
            list.add(candidature);
        }
        colid.setCellValueFactory(new PropertyValueFactory<>("id_candidature"));
        colcontenu.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        colprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tabcandidature.setItems(list);


    }
    @FXML
    private TextField txtcontcan;

    @FXML
    private TextField txtprix;
    private void ajoutercandidature() throws SQLException {
        String sql = "INSERT into candidature (contenu, prix, etat, id_artiste,id_annonce) " +
                "values (?,?,?,?,?) ";
        String sql1 ="update annonce set nb_candidature=nb_candidature+1 ";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, txtcontcan.getText() );
            preparedStatement.setString(2, txtprix.getText());
            preparedStatement.setString(3, "0");
            preparedStatement.setString(4, taher.getText());
            preparedStatement.setString(5, txtidann.getText());
            conn.createStatement().executeUpdate(sql1);



            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        populateTableCandidature();
    }

    @FXML
    void btnaction(ActionEvent event) throws SQLException {
        if(event.getSource()==btnretour)
        {
            try {

                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/artiste.fxml"));
                Parent p=loader.load();
                Scene scene=new Scene(p);

                artisteController controller = loader.getController();
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
        else if(event.getSource()==btnaddann)
        {
            ajoutercandidature();
        }
        else if(event.getSource()==btndeleteann)
        {
            supprimerCandidature();
        }
        else if(event.getSource()==btneditann)
        {
            editcandidature();
        }

    }


    @FXML
    void refresh1(MouseEvent event) throws SQLException {
        aa=getartisteid();
        taher.setText(String.valueOf(aa.getId_artiste()));
        populateTableCandidature();

    }
    @FXML
    private TextField txtidcan;
    @FXML
    void showselected(MouseEvent event) {
        Candidature c=tabcandidature.getSelectionModel().getSelectedItem();
        if(c!=null)
        {
            txtcontcan.setText(String.valueOf(c.getContenu()));
            txtprix.setText(String.valueOf(c.getPrix()));
            txtidcan.setText(String.valueOf(c.getId_candidature()));

        }

    }
    private artiste getartisteid() throws SQLException {
        String sql ="SELECT * FROM artiste where login= ?";
        artiste a=null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, clientlogin.getText());
            resultSet=preparedStatement.executeQuery();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        if (resultSet.next())
        {
            a = new artiste(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),resultSet.getInt(7),resultSet.getString(8),resultSet.getString(9),resultSet.getString(10),resultSet.getString(11));
        }
        return a;
    }
    private void supprimerCandidature() throws SQLException {
        String sql="delete from candidature where id_candidature= ?";
        String sql1 ="update annonce set nb_candidature=nb_candidature-1 ";
        try {
            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, txtidcan.getText());


            preparedStatement.executeUpdate();
            conn.createStatement().executeUpdate(sql1);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        populateTableCandidature();
    }
    private void editcandidature() throws SQLException {
//        Annonce annonce = new Annonce(1,txttitre.getText(),txtdesc.getText(),Integer.parseInt(txtprixmin.getText()),Integer.parseInt(txtprixmax.getText()), Date.from(Instant.from(txtdate.getValue().atStartOfDay(ZoneId.systemDefault()))),txtadresse.getText(),true,0,2);
        String sql="UPDATE candidature set  contenu= ?, prix = ?  where id_candidature=?";

        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, txtcontcan.getText());
            preparedStatement.setString(2, txtprix.getText());
            preparedStatement.setString(3, txtidcan.getText());



            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        populateTableCandidature();
        tabcandidature.refresh();
    }


}
