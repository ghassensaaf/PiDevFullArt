package Controller;
import java.text.SimpleDateFormat;
import entite.Annonce;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import util.ConnectionUtil;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
public class ClientIController implements Initializable {
    @FXML
    private Button add;

    @FXML
    private Button edit;

    @FXML
    private Button delete;

    @FXML
    private TextField txttitre;

    @FXML
    private ComboBox<?> txteve;

    @FXML
    private TextField txtprixmin;

    @FXML
    private TextField txtprixmax;

    @FXML
    private DatePicker txtdate;

    @FXML
    private TextField txtadresse;
    @FXML
    private TextArea txtdesc;
    @FXML
    private TextField txtid;
    @FXML
    private TextField txtrecherche;
    @FXML
    private TableView<Annonce> tableAnnonce;

    @FXML
    private TableColumn<Annonce, Integer> colid;

    @FXML
    private TableColumn<Annonce, String> coltitre;

    @FXML
    private TableColumn<Annonce, Integer> colprixmin;

    @FXML
    private TableColumn<Annonce, Integer> colprixmax;

    @FXML
    private TableColumn<Annonce, String > coletat;

    @FXML
    private TableColumn<Annonce, Integer> colnbcandidat;

    private Connection conn=null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    private ObservableList <Annonce> list;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = ConnectionUtil.conDB();
        try {
            populateTableAnnonce();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void populateTableAnnonce() throws SQLException {
        list= FXCollections.observableArrayList();
        String sql ="SELECT * FROM annonce";
        resultSet=conn.createStatement().executeQuery(sql);
        while(resultSet.next())
        {
            Annonce annonce=new Annonce(resultSet.getInt("id_annonce"),resultSet.getInt("id_client"),resultSet.getString("titre"),resultSet.getString("description"),resultSet.getInt("prix_min"),resultSet.getInt("prix_max"),resultSet.getDate("date"),resultSet.getString("adresse"),resultSet.getBoolean("etat"),resultSet.getTimestamp("date_annonce"),resultSet.getInt("nb_candidature"),resultSet.getInt("id_type_eve"));
            list.add(annonce);
        }
        colid.setCellValueFactory(new PropertyValueFactory<>("id_annonce"));
        coltitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        colprixmax.setCellValueFactory(new PropertyValueFactory<>("prix_max"));
        colprixmin.setCellValueFactory(new PropertyValueFactory<>("prix_min"));
        coletat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        colnbcandidat.setCellValueFactory(new PropertyValueFactory<>("nb_candidature"));
        tableAnnonce.setItems(list);

    }
    @FXML
    void btnaction(ActionEvent event) {
            if(event.getSource()==add)
            {
                try {
                    addAnnonce();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                finally {
                    txtid.setText("");
                    txttitre.setText("");
                    txtadresse.setText("");
                    txtdate.setValue(LocalDate.now());
                    txtdesc.setText("");
                    txtprixmax.setText("");
                    txtprixmin.setText("");
                }
            }
            else if(event.getSource()==delete)
            {
                try {
                    deleteAnnonce();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                finally {
                    txtid.setText("");
                    txttitre.setText("");
                    txtadresse.setText("");
                    txtdate.setValue(LocalDate.now());
                    txtdesc.setText("");
                    txtprixmax.setText("");
                    txtprixmin.setText("");
                }
            }
            else if(event.getSource()==edit)
            {
                try {
                    editAnnonce();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                finally {
                    txtid.setText("");
                    txttitre.setText("");
                    txtadresse.setText("");
                    txtdate.setValue(LocalDate.now());
                    txtdesc.setText("");
                    txtprixmax.setText("");
                    txtprixmin.setText("");
                }
            }


    }
    private void addAnnonce() throws SQLException {
//        Annonce annonce = new Annonce(1,txttitre.getText(),txtdesc.getText(),Integer.parseInt(txtprixmin.getText()),Integer.parseInt(txtprixmax.getText()), Date.from(Instant.from(txtdate.getValue().atStartOfDay(ZoneId.systemDefault()))),txtadresse.getText(),true,0,2);
        String sql = "INSERT into annonce (id_client, titre, description, prix_min, prix_max, date, adresse, etat, nb_candidature, id_type_eve) " +
//                "values (1,'salem','sbe5ir',10,25,'2020-12-12','azefazef',true,0,4) ";
                "values (?,?,?,?,?,?,?,?,?,?) ";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, "1");
            preparedStatement.setString(2, txttitre.getText());
            preparedStatement.setString(3, txtdesc.getText());
            preparedStatement.setString(4, txtprixmin.getText());
            preparedStatement.setString(5, txtprixmax.getText());
            preparedStatement.setString(6, txtdate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            preparedStatement.setString(7, txtadresse.getText());
            preparedStatement.setString(8, "1");
            preparedStatement.setString(9, "0");
            preparedStatement.setString(10, "3");
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        populateTableAnnonce();
    }
    @FXML
    void showselected(MouseEvent event) {
        Annonce a=tableAnnonce.getSelectionModel().getSelectedItem();
        if(a!=null)
        {
            DateFormat format= new SimpleDateFormat("yyyy-MM-dd");
            txtid.setText(String.valueOf(a.getId_annonce()));
            txttitre.setText(a.getTitre());
            txtadresse.setText(a.getAdresse());
            txtdate.setValue(LocalDate.parse(format.format(a.getDate())));
            txtdesc.setText(a.getDescription());
            txtprixmax.setText(String.valueOf(a.getPrix_max()));
            txtprixmin.setText(String.valueOf(a.getPrix_min()));
        }

    }
    private void deleteAnnonce() throws SQLException {
//        Annonce annonce = new Annonce(1,txttitre.getText(),txtdesc.getText(),Integer.parseInt(txtprixmin.getText()),Integer.parseInt(txtprixmax.getText()), Date.from(Instant.from(txtdate.getValue().atStartOfDay(ZoneId.systemDefault()))),txtadresse.getText(),true,0,2);
        String sql="delete from annonce where id_annonce = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, txtid.getText());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        populateTableAnnonce();
    }
    private void editAnnonce() throws SQLException {
//        Annonce annonce = new Annonce(1,txttitre.getText(),txtdesc.getText(),Integer.parseInt(txtprixmin.getText()),Integer.parseInt(txtprixmax.getText()), Date.from(Instant.from(txtdate.getValue().atStartOfDay(ZoneId.systemDefault()))),txtadresse.getText(),true,0,2);
        String sql="UPDATE annonce set  titre= ? , id_type_eve= ? , prix_min = ?, prix_max = ?, date= ?, adresse=?, description= ? where id_annonce=?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, txttitre.getText());
            preparedStatement.setString(2, "2");
            preparedStatement.setString(3, txtprixmin.getText());
            preparedStatement.setString(4, txtprixmax.getText());
            preparedStatement.setString(5, txtdate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            preparedStatement.setString(6, txtadresse.getText());
            preparedStatement.setString(7, txtdesc.getText());
            preparedStatement.setString(8, txtid.getText());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        populateTableAnnonce();
    }



}
