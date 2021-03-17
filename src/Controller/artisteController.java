package Controller;

import entite.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.ConnectionUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class artisteController implements Initializable {

    @FXML
    private ComboBox<Integer> txttypepub;

    @FXML
    private TextField txttitrepub;

    @FXML
    private TextField idpub;
    @FXML
    private TextField search;

    @FXML
    private TextField search1;

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
    private TableColumn<publication, Timestamp> coldatepub;

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
    private TextField searchfield;

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
    private TableColumn<concert, Timestamp> coldate;

    @FXML
    private Button addconcert;

    @FXML
    private Button editconcert;

    @FXML
    private Button deleteconcert;

    @FXML
    private Button stat;

    @FXML
    private Button btnretour;

    @FXML
    private TextField mourad;
    @FXML
    private TableView<Annonce> tabann;

    @FXML
    private TableColumn<Annonce, Integer> colidann;

    @FXML
    private TableColumn<Annonce, Integer> coltypeann;

    @FXML
    private TableColumn<Annonce, String> coltitreann;

    @FXML
    private TableColumn<Annonce, String> coldescann;

    @FXML
    private TableColumn<Annonce, Integer> colpminann;

    @FXML
    private TableColumn<Annonce, Integer> colpmaxann;

    @FXML
    private TableColumn<Annonce, Date> coldateann;

    @FXML
    private TableColumn<Annonce, Integer> colnbcand;
    @FXML
    private TableColumn<Annonce, Integer> colclientann;

    CategoryAxis xaxis = new CategoryAxis();
    NumberAxis yaxis = new NumberAxis();
    @FXML
    private BarChart<String, Integer> barChart ;

    @FXML
    private ScatterChart<String, Integer> scatt ;

    @FXML
    private TextField txtidann;

    @FXML
    private Button btnpostuler;

    @FXML
    private Button load;

    @FXML
            private PieChart pieChart;


    @FXML
    private BubbleChart<String, Integer> bubChart;


    artiste aa = null;

    @FXML
    private TextField txtrecherche;

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
    private TableColumn<reclamation, Timestamp> coldate_rec;

    @FXML
    private TextField txtid_rec;
    @FXML
    private TextField txtid_art;

    @FXML
    private TextField txttitre_rec;

    @FXML
    private TextArea txtdesc_rec;

    @FXML
    private TextField lotfi;

    @FXML
    private TableView<client> tab_con_art;

    @FXML
    private TableColumn<client, Integer> tab_con_art_id;

    @FXML
    private TableColumn<client, String> tab_con_art_nom;

    @FXML
    private TableColumn<client, String> tab_con_art_pre;

    @FXML
    private TableColumn<client, String> tab_con_art_adresse;

    @FXML
    private TableColumn<client, String> tab_con_art_mail;

    @FXML
    private TableColumn<client, Integer> tab_con_art_tel;


    @FXML
    private Button Contacter_art;

    private artiste artiste1;

    private Connection conn = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement1 = null;
    private ObservableList<publication> list;
    private ObservableList<concert> list1;
    private ObservableList<Annonce> list2;
    private ObservableList <reclamation> list3;
    private ObservableList <client> list4;



    public String initData(String login) {
        artistlogin.setText(login);
        return login;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = ConnectionUtil.conDB();

        try {
            ArrayList<type_pub> listepub = gettypepub();
            for (type_pub p : listepub) {
                txttypepub.getItems().add(p.getId_type());
            }
            populateTableAnnonce();

            LoadChart();
            LoadChart3();
            LoadChart1();


            populateTableArtiste();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }


    private void afficherPublication() throws SQLException {
        list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM publication where id_artiste=?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, mourad.getText());
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        while (resultSet.next()) {
            publication pub = new publication(resultSet.getInt("id_pub"), resultSet.getInt("id_artiste"), resultSet.getInt("id_type"), resultSet.getString("titre"), resultSet.getString("contenu"), resultSet.getTimestamp("date_pub"), resultSet.getInt("nb_like"));
            list.add(pub);
        }
        colid.setCellValueFactory(new PropertyValueFactory<>("id_pub"));
        coltitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        colcontenu.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        coltype.setCellValueFactory(new PropertyValueFactory<>("id_type"));
        coldatepub.setCellValueFactory(new PropertyValueFactory<>("date_pub"));
        col_like.setCellValueFactory(new PropertyValueFactory<>("nb_like"));
        tabpub.setItems(list);


    }

    @FXML
    void search(KeyEvent event) {
        FilteredList filter = new FilteredList(list, e -> true);
        search.textProperty().addListener((observable, oldValue, newValue) -> {


            filter.setPredicate((Predicate<? super publication>) (publication publication) -> {
                if (newValue.isEmpty() || newValue == null) {
                    return true;
                } else if (publication.getTitre().contains(newValue)) {
                    return true;
                } else if (publication.getContenu().contains(newValue)) {
                    return true;
                } else if (publication.getDate_pub().equals(newValue)) {
                    return true;
                }

                return false;
            });
        });

        SortedList sort = new SortedList(filter);
        sort.comparatorProperty().bind(tabpub.comparatorProperty());
        tabpub.setItems(sort);

    }

    @FXML
    void btnaction(ActionEvent event) throws SQLException {
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
        } else if (event.getSource() == deletepub) {
            try {
                supprimerpub();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                txttypepub.getAccessibleText();
                txttitrepub.setText("");
                txtcontenupub.setText("");
            }
        } else if (event.getSource() == editpub) {
            try {
                modifierpub();
                Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                al.setContentText("publication bien modifié");
                al.show();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                txttypepub.getAccessibleText();
                txttitrepub.setText("");
                txtcontenupub.setText("");
            }
        } else if (event.getSource() == addconcert) {
            try {
                ajouterconcert();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                lieuconcert.setText("");
                dateconcert.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                txtcontenupub.setText("");
            }
        } else if (event.getSource() == deleteconcert) {
            try {
                supprimerconcert();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                lieuconcert.setText("");
                dateconcert.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                txtcontenupub.setText("");
            }
        } else if (event.getSource() == editconcert) {
            try {
                modifierconcert();
                Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                al.setContentText("Concert bien modifié");
                al.show();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {

                lieuconcert.setText("");
                dateconcert.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                txtcontenupub.setText("");
            }
        } else if (event.getSource() == btnretour) {
            try {
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/login.fxml")));
                stage.setScene(scene);
                stage.show();

            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }

        else if(event.getSource()==btnpostuler)
        {
            try {

                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/postuler.fxml"));
                Parent p=loader.load();
                Scene scene=new Scene(p);

                postulerController controller = loader.getController();
                controller.initData(Integer.parseInt(txtidann.getText()),artistlogin.getText());
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

    private void addPub() throws SQLException {
        String sql = "INSERT into publication ( id_artiste, id_type, titre, contenu,nb_like) " +
                "values (?,?,?,?,?) ";
        try {

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, mourad.getText());
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
        String sql = "delete from publication where id_pub = ?";
        String sql1 = "delete from jaime where id_pub= ?";
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
        String sql = "UPDATE publication set  id_type= ? , titre= ? , contenu = ?  where id_pub= ?";
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
        String sql = "SELECT * FROM concert where id_artiste=?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, mourad.getText());
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
            preparedStatement.setString(1, mourad.getText());
            preparedStatement.setString(2, lieuconcert.getText());
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
        String sql = "delete from concert where id_concert = ?";
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
        String sql = "UPDATE concert set  id_artiste= ? , lieu= ? , date = ?  where id_concert= ?";
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
        ArrayList<type_pub> liste = new ArrayList<>();
        String sql = " select * from type_pub ";
        resultSet = conn.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            type_pub p = new type_pub(resultSet.getInt(1), resultSet.getString(2));
            liste.add(p);
        }
        return liste;
    }

    private artiste getartisteid() throws SQLException {
        String sql = "SELECT * FROM artiste where login= ?";
        artiste a = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, artistlogin.getText());
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        if (resultSet.next()) {
            a = new artiste(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getInt(7), resultSet.getString(8), resultSet.getString(9), resultSet.getString(10), resultSet.getString(11));
        }
        return a;
    }

    @FXML
    void refresh(MouseEvent event) throws SQLException {

        artiste1=getartisteid();
        aa=getartisteid();
        mourad.setText(String.valueOf(aa.getId_artiste()));
        afficherPublication();
        afficherconcert();
        populateTablereclamation();

    }

    @FXML
    void search1(KeyEvent event) {

        FilteredList filter = new FilteredList(list1, e -> true);
        search1.textProperty().addListener((observable, oldValue, newValue) -> {


            filter.setPredicate((Predicate<? super concert>) (concert concert) -> {
                if (newValue.isEmpty() || newValue == null) {
                    return true;
                } else if (concert.getLieu().contains(newValue)) {
                    return true;
                } else if (concert.getDate().equals(newValue)) {
                    return true;
                }
                return false;
            });
        });

        SortedList sort = new SortedList(filter);
        sort.comparatorProperty().bind(tabconcert.comparatorProperty());
        tabconcert.setItems(sort);

    }

    private void populateTableAnnonce() throws SQLException {
        list2 = FXCollections.observableArrayList();
        String sql = "SELECT * FROM annonce where etat = true ";
        try {
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        while (resultSet.next()) {
            Annonce annonce = new Annonce(resultSet.getInt("id_annonce"), resultSet.getInt("id_client"), resultSet.getString("titre"), resultSet.getString("description"), resultSet.getInt("prix_min"), resultSet.getInt("prix_max"), resultSet.getDate("date"), resultSet.getString("adresse"), resultSet.getBoolean("etat"), resultSet.getTimestamp("date_annonce"), resultSet.getInt("nb_candidature"), resultSet.getInt("id_type_eve"));
            list2.add(annonce);
        }
        colidann.setCellValueFactory(new PropertyValueFactory<>("id_annonce"));
        coltitreann.setCellValueFactory(new PropertyValueFactory<>("titre"));
        coldescann.setCellValueFactory(new PropertyValueFactory<>("description"));
        coltypeann.setCellValueFactory(new PropertyValueFactory<>("id_type_eve"));
        colpmaxann.setCellValueFactory(new PropertyValueFactory<>("prix_max"));
        colpminann.setCellValueFactory(new PropertyValueFactory<>("prix_min"));
        colnbcand.setCellValueFactory(new PropertyValueFactory<>("nb_candidature"));
        coldateann.setCellValueFactory(new PropertyValueFactory<>("date"));
        colclientann.setCellValueFactory(new PropertyValueFactory<>("id_client"));
        tabann.setItems(list2);
    }

    @FXML
    void showselectedannonce(MouseEvent event) {
        Annonce a = tabann.getSelectionModel().getSelectedItem();
        if (a != null) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            txtidann.setText(String.valueOf(a.getId_annonce()));

        }

    }
    /*****************************************RECLAMATION**********************/
    private void populateTablereclamation() throws SQLException {
        list3 = FXCollections.observableArrayList();
        String sql = "SELECT * FROM reclamation where id_artiste=?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,mourad.getText() );
            resultSet=preparedStatement.executeQuery();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
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
        String sql = "INSERT into reclamation (titre, contenu,etat,id_artiste) " +
                "values (?,?,?,?) ";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, txttitre_rec.getText());
            preparedStatement.setString(2, txtdesc_rec.getText());
            preparedStatement.setString(3, "0");
            preparedStatement.setString(4, mourad.getText());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        populateTablereclamation();
    }
    /*************************ACTION RECLAMATION*****************/
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
    /*************************MOUSE  RECLAMATION*****************/
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
    /*************************MODIFIER RECLAMATION*****************/
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
    /*************************DELETE  RECLAMATION*****************/
    private void deleteReclamation() throws SQLException {
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
    /**************************** SEARCH RECLAMATION  ***********************/
    @FXML
    void search2(KeyEvent event) {

        FilteredList filter = new FilteredList(list3, e->true);
        txtrecherche.textProperty().addListener((observable, oldValue, newValue )-> {


            filter.setPredicate((Predicate<? super reclamation>) (reclamation reclamation)->{
                if(newValue.isEmpty() || newValue==null) {
                    return true;
                }
                else if(reclamation.getTitre().contains(newValue)) {
                    return true;
                }
                else if(reclamation.getContenu().contains(newValue)) {
                    return true;
                }
                return false;
            });
        });

        SortedList sort = new SortedList(filter);
        sort.comparatorProperty().bind(tableReclamation.comparatorProperty());
        tableReclamation.setItems(sort);

    }

    /**************************** MESSAGE ***********************/
    private void populateTableArtiste() throws SQLException {
        list4 = FXCollections.observableArrayList();
        String sql = "SELECT * FROM client";
        resultSet = conn.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            client rec = new client(resultSet.getInt("id_client"), resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("mail"), resultSet.getInt("tel"),resultSet.getString("adresse"));
            list4.add(rec);
        }
        tab_con_art_id.setCellValueFactory(new PropertyValueFactory<>("id_client"));
        tab_con_art_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tab_con_art_pre.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        tab_con_art_mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        tab_con_art_tel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        tab_con_art_adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));

        tab_con_art.setItems(list4);

    }

    @FXML
    void showselectedartiste(MouseEvent event) {
        client a=tab_con_art.getSelectionModel().getSelectedItem();
        if(a!=null)
        {
            txtid_art.setText(String.valueOf(a.getId_client()));
        }

    }

    @FXML
    void btn_contacter(ActionEvent event) {
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/ContacteArtiste.fxml"));

            Parent p=loader.load();

            Scene scene=new Scene(p);
            ContacteArtiste controller = loader.getController();
            controller.initData(Integer.parseInt(txtid_art.getText()),artistlogin.getText());

            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**************************** STAT REC  ***********************/
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    final StackedAreaChart<String, Number> sbc =
            new StackedAreaChart<>(xAxis, yAxis);
    final XYChart.Series<String, Number> series1 =
            new XYChart.Series<String, Number>();
    Stage stage=new Stage();

    @FXML
    void Btn_stat(ActionEvent event)
    {
        stage.setTitle("Stat");
        sbc.setTitle("Nb de rec par Date");
        String sql = "SELECT date, COUNT(*) as lotfi FROM reclamation WHERE date >= DATE(NOW()) - INTERVAL 6 DAY GROUP by DAYOFWEEK(date)";
        try {
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                series1.getData().add(new XYChart.Data<>(String.valueOf(resultSet.getDate(1)), resultSet.getInt(2)));
                System.out.println(resultSet.getDate(1));
                System.out.println(resultSet.getInt(2));
            }
            System.out.println(resultSet);
//            barChart.getData().add(data);

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        Scene scene = new Scene(sbc, 800, 600);
        sbc.getData().addAll(series1);
        stage.setScene(scene);
        stage.show();
    }

    /**************************** STAT PUB ***********************/
    private void LoadChart()  {

        XYChart.Series<String,Integer> data =new XYChart.Series<>();

        String sql = "select * FROM publication order by nb_like desc";


        try {
            barChart.setTitle("Nombre des jaimes par publication");



            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {


                data.getData().add(new XYChart.Data<>(resultSet.getString(4), resultSet.getInt(7)));
//                System.out.println(resultSet.getString(4));
//                System.out.println(resultSet.getInt(7));

            }
//            System.out.println(data);
            barChart.getData().add(data);


        } catch (SQLException ex) {
            Logger.getLogger(artisteController.class.getName()).log(Level.SEVERE,null,ex);
        }


    }

    private void LoadChart1()  {

        ObservableList<PieChart.Data>data = FXCollections.observableArrayList();
        String sql = "select * FROM publication order by nb_like desc";


        try {

            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {


                data.add(new PieChart.Data(resultSet.getString(4), resultSet.getInt(7)));
//                System.out.println(resultSet.getString(4));
//                System.out.println(resultSet.getInt(7));

            }
//            System.out.println(data);

        } catch (SQLException ex) {
            Logger.getLogger(artisteController.class.getName()).log(Level.SEVERE,null,ex);
        }



        pieChart.setTitle("Nombre des jaimes Par Publication");
        pieChart.setLegendSide(Side.LEFT);
        pieChart.setData(data);


    }

    private void LoadChart3()  {

        XYChart.Series data =new XYChart.Series();

        String sql = "select * FROM publication order by nb_like desc";


        try {
            barChart.setTitle("Nombre des jaimes par publication");



            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {


                data.getData().add(new XYChart.Data<>(resultSet.getString(4), resultSet.getInt(7)));
//                System.out.println(resultSet.getString(4));
//                System.out.println(resultSet.getInt(7));

            }
//            System.out.println(data);
            scatt.setTitle("Jaime publication");
            scatt.getData().add(data);
            scatt.setLegendSide(Side.LEFT);

        } catch (SQLException ex) {
            Logger.getLogger(artisteController.class.getName()).log(Level.SEVERE,null,ex);
        }


    }


}


