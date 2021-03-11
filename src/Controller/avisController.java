package Controller;
import entite.avis;
import entite.commentaire;
import entite.reclamation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.ConnectionUtil;

import javax.swing.event.ChangeListener;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class avisController implements Initializable {

    @FXML
    private TextField lotfi;

    @FXML
    private TableView<avis> tab_avis;

    @FXML
    private TableColumn<avis, Integer> colid_avis;

    @FXML
    private TableColumn<avis, String> colContenu_avis;

    @FXML
    private TableColumn<avis, Integer> colnote_avis;

    @FXML
    private Label login;

    @FXML
    private Label id_artiste;
    @FXML
    private Label clientlogin;

    @FXML
    private TextField txtid_art;

    @FXML
    private TextArea txt_avis;

    @FXML
    private Button btn_supprimer_avis;

    @FXML
    private Button btn_modifier_avis;

    @FXML
    private Button btn_ajouter_avis;

    @FXML
    private TextField id_avis;
    @FXML
    private TextField recherche_avis;

    @FXML
    private Button btnretour;
    @FXML
    private Label label;

    @FXML
    private Slider note;


    private Connection conn;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    private ObservableList<avis> list;

    public void initData(int i, String ll) {
        txtid_art.setText(String.valueOf(i));
        clientlogin.setText(ll);


    }


    public void initialize(URL location, ResourceBundle resources)  {
        conn = ConnectionUtil.conDB();
        try {
            populateTableavis();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }




    private void populateTableavis() throws SQLException {
        list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM avis where id_artiste=?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,txtid_art.getText() );

            resultSet=preparedStatement.executeQuery();


        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        while (resultSet.next()) {
            avis av = new avis(resultSet.getInt("id_avis"), resultSet.getInt("note"), resultSet.getString("contenu"));
            list.add(av);
        }
        colid_avis.setCellValueFactory(new PropertyValueFactory<>("id_avis"));
        colnote_avis.setCellValueFactory(new PropertyValueFactory<>("note"));
        colContenu_avis.setCellValueFactory(new PropertyValueFactory<>("contenu"));

        tab_avis.setItems(list);

    }


    @FXML
    void harek(MouseEvent event) {
        int sliderValue = (int) note.getValue();
        label.setText(sliderValue +" ");
    }

    private void ajouterAvis() throws SQLException {
        String sql = "INSERT into avis (id_artiste,note,contenu) " +
                "values (?,?,?) ";
        try {
            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, txtid_art.getText());
            preparedStatement.setString(2, label.getText());
            preparedStatement.setString(3, txt_avis.getText());


            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        populateTableavis();
    }




    @FXML
    void refresh(MouseEvent event) throws SQLException {

      populateTableavis();
    }


    public void Action_avis(javafx.event.ActionEvent event) {
        if (event.getSource() == btn_ajouter_avis) {
            try {
                ajouterAvis();
                Alert aa = new Alert(Alert.AlertType.CONFIRMATION);
                aa.setContentText("Avis ajouté");
                aa.show();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                txt_avis.setText("");
                id_avis.setText("");
                note.setValue(0);
                label.setText("");
            }
        }
        else if (event.getSource() == btn_supprimer_avis) {
            try {
                deleteAvis();
                Alert aa = new Alert(Alert.AlertType.CONFIRMATION);
                aa.setContentText("Avis supprimé");
                aa.show();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                txt_avis.setText("");
                id_avis.setText("");
                note.setValue(0);
                label.setText("");
            }
        }
        else if (event.getSource() == btn_modifier_avis) {
            try {
                editAvis();
                Alert aa = new Alert(Alert.AlertType.CONFIRMATION);
                aa.setContentText("Avis modifié");
                aa.show();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                txt_avis.setText("");
                id_avis.setText("");
                note.setValue(0);
                label.setText("");
            }
        }
    }
    @FXML
    void showselected(MouseEvent event) {
        avis a=tab_avis.getSelectionModel().getSelectedItem();
        if(a!=null)
        {
            id_avis.setText(String.valueOf(a.getId_avis()));
            txt_avis.setText(String.valueOf(a.getContenu()));
            label.setText(String.valueOf(a.getNote()));
            note.setValue(a.getNote());

        }

    }

    private void deleteAvis() throws SQLException {

        String sql="delete from avis where id_avis = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, id_avis.getText());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        populateTableavis();
    }
    private void editAvis() throws SQLException {

        String sql="UPDATE avis set  note= ? , contenu=? where id_avis=?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, label.getText());
            preparedStatement.setString(2, txt_avis.getText());
            preparedStatement.setString(3, id_avis.getText());




            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        populateTableavis();
        tab_avis.refresh();
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
    void search1(KeyEvent event) {
        FilteredList filter = new FilteredList(list, e->true);
        recherche_avis.textProperty().addListener((observable, oldValue, newValue )-> {


            filter.setPredicate((Predicate<? super avis>) (avis avis)->{
                if(newValue.isEmpty() || newValue==null) {
                    return true;
                }
                else if(avis.getContenu().contains(newValue)) {
                    return true;
                }

                return false;
            });
        });

        SortedList sort = new SortedList(filter);
        sort.comparatorProperty().bind(tab_avis.comparatorProperty());
        tab_avis.setItems(sort);

    }
}
