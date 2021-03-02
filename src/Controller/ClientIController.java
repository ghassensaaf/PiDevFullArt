package Controller;
import java.sql.*;
import java.text.SimpleDateFormat;
import entite.Annonce;
import entite.publication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import util.ConnectionUtil;

import java.net.URL;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ClientIController implements Initializable {
    @FXML private Button add;

    @FXML private Button edit;

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
    @FXML
    private TableColumn<Annonce, String> colconsulter;
    @FXML
    private TableView<publication> tabpub;

    @FXML
    private TableColumn<publication, Integer> colid2;

    @FXML
    private TableColumn<publication, Integer> coltype;

    @FXML
    private TableColumn<publication, String> coltitre2;

    @FXML
    private TableColumn<publication, String> colcontenu;

    @FXML
    private TableColumn<publication, Timestamp> coldate;

    @FXML
    private TableColumn<publication, Integer> col_like;
    private Connection conn=null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    private ObservableList <Annonce> list;
    private ObservableList <publication> list2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = ConnectionUtil.conDB();
        try {
            populateTableAnnonce();
            populateTablePublication();
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
//        Button row
        javafx.util.Callback<TableColumn<Annonce,String>, TableCell<Annonce,String>> cellFactory=(param)->{
            final TableCell<Annonce,String> cell= new TableCell<Annonce,String>(){
                @Override
                public void updateItem(String item,boolean empty){
                    super.updateItem(item,empty);
                    if(empty){
                        setGraphic(null);
                        setText(null);
                    }
                    else {
                        final Button btnconsulter=new Button("Consulter");
                        btnconsulter.setOnAction(event -> {
                            Annonce a=getTableView().getItems().get(getIndex());
                            Alert aa=new Alert(Alert.AlertType.INFORMATION);
                            aa.setContentText("titre: "+ a.getTitre()+ "\ndescription : "+a.getDescription());
                            aa.show();
                        });
                        setGraphic(btnconsulter);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        colconsulter.setCellFactory(cellFactory);
    }
    @FXML
    void btnaction(ActionEvent event) {
            if(event.getSource()==add)
            {
                try {
                    addAnnonce();
                    Alert aa=new Alert(Alert.AlertType.CONFIRMATION);
                    aa.setContentText("Annonce ajoutée");
                    aa.show();
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
                    Alert aa=new Alert(Alert.AlertType.CONFIRMATION);
                    aa.setContentText("Annonce supprimée");
                    aa.show();
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
                    Alert aa=new Alert(Alert.AlertType.CONFIRMATION);
                    aa.setContentText("Annonce modifiée");
                    aa.show();
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
    private void populateTablePublication() throws SQLException {
        list2= FXCollections.observableArrayList();
        String sql ="SELECT * FROM publication";
        resultSet=conn.createStatement().executeQuery(sql);
        while(resultSet.next())
        {
            publication pub=new publication(resultSet.getInt("id_pub"),resultSet.getInt("id_artiste"),resultSet.getInt("id_type"),resultSet.getString("titre"),resultSet.getString("contenu"),resultSet.getTimestamp("date_pub"),resultSet.getInt("nb_like"));
            list2.add(pub);
        }
        colid2.setCellValueFactory(new PropertyValueFactory<>("id_pub"));
        coltitre2.setCellValueFactory(new PropertyValueFactory<>("titre"));
        colcontenu.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        coltype.setCellValueFactory(new PropertyValueFactory<>("id_type"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("date_pub"));
        col_like.setCellValueFactory(new PropertyValueFactory<>("nb_like"));
        tabpub.setItems(list2);

    }



}
