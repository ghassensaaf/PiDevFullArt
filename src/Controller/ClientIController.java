package Controller;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import entite.Annonce;
import entite.client;
import entite.publication;
import entite.reclamation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.ConnectionUtil;

import java.net.URL;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ClientIController implements Initializable {
    @FXML private Button add;
    @FXML private TextField txtid_pub;
    @FXML private TextField txtid_rec;
    @FXML private Button consult_pub;
    @FXML private Button edit;

    @FXML private Label clientlogin;

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

    @FXML
    private Button btn_ajouter;

    @FXML
    private Button btn_modifier;

    @FXML
    private Button btn_supprimer;

    @FXML
    private TableView<reclamation> tableReclamation;

    @FXML
    private TableColumn<reclamation, Integer> colid_rec;

    @FXML
    private TableColumn<reclamation, String> coltitre_rec;

    @FXML
    private TableColumn<reclamation, String> colcontenu_rec;

    @FXML
    private TableColumn<reclamation, Integer> coletat_rec;

    @FXML
    private TableColumn<publication, Timestamp> coldate_rec;

    @FXML
    private TableColumn<publication, String> jaime;

    @FXML
    private TextField txttitre_rec;

    @FXML
    private TextArea txtdesc_rec;
    @FXML
    private Button consulter;

    @FXML
    private Button btnretour;

    private Connection conn=null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement1 = null;
    private ObservableList <Annonce> list;
    private ObservableList <publication> list2;
    private ObservableList <reclamation> list3;
    private Annonce a;
    public String initData(String login)
    {
        clientlogin.setText(login);
        return login;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = ConnectionUtil.conDB();
        try {
            populateTableAnnonce();
            populateTablePublication();
            populateTablereclamation();
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
            else if(event.getSource()==consulter)
            {
                try {
                    FXMLLoader loader=new FXMLLoader();
                    loader.setLocation(getClass().getResource("/fxml/detailAnnonce.fxml"));

                    Parent p=loader.load();

                    Scene scene=new Scene(p);
                    detailAnnonceController controller = loader.getController();
                    controller.initData(a,clientlogin.getText());

                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.close();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException throwables) {
                    throwables.printStackTrace();
                }
            }
            else if (event.getSource()==btnretour)
            {
                try {

                    //add you loading or delays - ;-)
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    //stage.setMaximized(true);
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/login.fxml")));
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
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
        a=tableAnnonce.getSelectionModel().getSelectedItem();
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
        javafx.util.Callback<TableColumn<publication,String>, TableCell<publication,String>> cellFactory=(param)->{
            final TableCell<publication,String> cell= new TableCell<publication,String>(){
                @Override
                public void updateItem(String item,boolean empty){
                    super.updateItem(item,empty);
                    if(empty){
                        setGraphic(null);
                        setText(null);
                    }
                    else {
                        final Button btnconsulter=new Button("j'aime");
                        btnconsulter.setOnAction(event -> {
                            publication pub=getTableView().getItems().get(getIndex());
                            String sql="UPDATE publication set  nb_like = nb_like+1 where id_pub=?";
                            String sql1="INSERT into jaime (id_artiste, id_client, id_pub, id_commentaire) values (null,1,?,null)";
                            try {
                                preparedStatement = conn.prepareStatement(sql);
                                preparedStatement1 = conn.prepareStatement(sql1);
                                preparedStatement.setString(1, String.valueOf(pub.getId_pub()));
                                preparedStatement1.setString(1, String.valueOf(pub.getId_pub()));
                                preparedStatement.executeUpdate();
                                preparedStatement1.executeUpdate();
                            } catch (SQLException ex) {
                                System.err.println(ex.getMessage());
                            }
                            try {
                                populateTablePublication();
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }

                        });
                        setGraphic(btnconsulter);
                        setText(null);
                    }

                }
            };
            return cell;
        };
        jaime.setCellFactory(cellFactory);

    }

/*****************************************RECLAMATION**********************/
private void populateTablereclamation() throws SQLException {
    list3 = FXCollections.observableArrayList();
    String sql = "SELECT * FROM reclamation";
    resultSet = conn.createStatement().executeQuery(sql);
    while (resultSet.next()) {
        reclamation rec = new reclamation(resultSet.getInt("id_reclamation"), resultSet.getString("titre"), resultSet.getString("contenu"), resultSet.getTimestamp("date"), resultSet.getInt("etat")    );
        list3.add(rec);
    }
    colid_rec.setCellValueFactory(new PropertyValueFactory<>("id_reclamation"));
    coltitre_rec.setCellValueFactory(new PropertyValueFactory<>("titre"));
    colcontenu_rec.setCellValueFactory(new PropertyValueFactory<>("contenu"));
    coldate_rec.setCellValueFactory(new PropertyValueFactory<>("date"));
    coletat_rec.setCellValueFactory(new PropertyValueFactory<>("etat"));
    tableReclamation.setItems(list3);

}
/*************************AJOUTER RECLAMATION*****************/
private void ajouterReclamation() throws SQLException {
//        Annonce annonce = new Annonce(1,txttitre.getText(),txtdesc.getText(),Integer.parseInt(txtprixmin.getText()),Integer.parseInt(txtprixmax.getText()), Date.from(Instant.from(txtdate.getValue().atStartOfDay(ZoneId.systemDefault()))),txtadresse.getText(),true,0,2);
    String sql = "INSERT into reclamation (titre, contenu,etat,id_artiste,id_client) " +
//                "values (1,'salem','sbe5ir',10,25,'2020-12-12','azefazef',true,0,4) ";
            "values (?,?,?,?,?) ";
    try {
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, txttitre_rec.getText());
        preparedStatement.setString(2, txtdesc_rec.getText());
        preparedStatement.setString(3, "0");
        preparedStatement.setString(4, "2");
        preparedStatement.setString(5, "1");
        preparedStatement.executeUpdate();
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
    populateTablereclamation();
}

    @FXML
    void Action_rec(ActionEvent event) {
        if (event.getSource() == btn_ajouter) {
            try {
                ajouterReclamation();
                Alert aa = new Alert(Alert.AlertType.CONFIRMATION);
                aa.setContentText("reclamation ajoutée");
                aa.show();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                txttitre_rec.setText("");
                txtdesc_rec.setText("");
                txtdesc_rec.setText("");

            }
        }
        if (event.getSource() == btn_modifier) {
            try {
                modifierReclamation();
                Alert aa = new Alert(Alert.AlertType.CONFIRMATION);
                aa.setContentText("reclamation modifiée");
                aa.show();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                txttitre_rec.setText("");
                txtdesc_rec.setText("");
                txtid_rec.setText("");

            }
        }
        if (event.getSource() == btn_supprimer) {
            try {
                deleteReclamation();
                Alert aa = new Alert(Alert.AlertType.CONFIRMATION);
                aa.setContentText("reclamation supprimée");
                aa.show();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                txttitre_rec.setText("");
                txtdesc_rec.setText("");
                txtid_rec.setText("");

            }
        }
    }
    @FXML
    void showselectedrec(MouseEvent event) {
        reclamation a=tableReclamation.getSelectionModel().getSelectedItem();
        if(a!=null)
        {
            txtdesc_rec.setText(String.valueOf(a.getContenu()));
            txttitre_rec.setText(a.getTitre());
            txtid_rec.setText(String.valueOf(a.getId_reclamation()));

        }

    }
    private void modifierReclamation() throws SQLException {
        String sql="UPDATE reclamation set  titre= ? , contenu= ?  where id_reclamation= ?";
        try {
            preparedStatement1 = conn.prepareStatement(sql);
            preparedStatement1.setString(1, txttitre_rec.getText());
            preparedStatement1.setString(2, txtdesc_rec.getText());
            preparedStatement1.setString(3, txtid_rec.getText());
            preparedStatement1.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        populateTablereclamation();
        tableReclamation.refresh();
    }
    private void deleteReclamation() throws SQLException {
//        Annonce annonce = new Annonce(1,txttitre.getText(),txtdesc.getText(),Integer.parseInt(txtprixmin.getText()),Integer.parseInt(txtprixmax.getText()), Date.from(Instant.from(txtdate.getValue().atStartOfDay(ZoneId.systemDefault()))),txtadresse.getText(),true,0,2);
        String sql="delete from reclamation where id_reclamation = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, txtid_rec.getText());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        populateTablereclamation();
    }
/*****************************************publication**********************/
    @FXML
    void showselectedpub(MouseEvent event) {
        publication a=tabpub.getSelectionModel().getSelectedItem();
        if(a!=null)
        {
            txtid_pub.setText(String.valueOf(a.getId_pub()));
        }

    }
    @FXML
    void btnconsulterpub(ActionEvent event) {
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/detailPub.fxml"));

            Parent p=loader.load();

            Scene scene=new Scene(p);
            detailPubController controller = loader.getController();
            controller.initData(Integer.parseInt(txtid_pub.getText()),clientlogin.getText());

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
