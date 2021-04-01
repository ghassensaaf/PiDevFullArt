package Controller;

import com.itextpdf.text.Image;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import entite.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import util.ConnectionUtil;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;
import javafx.event.*;


public class ClientIController extends Component implements Initializable {
    @FXML private Button add;
    @FXML private TextField txtid_pub;
    @FXML private TextField txtid_rec;
    @FXML private Button consult_pub;
    @FXML private Button edit;
    @FXML
    private LineChart<?, ?> star;

    @FXML private Label clientlogin;

    @FXML
    private Button delete;

    @FXML
    private TextField txttitre;

    @FXML
    private ComboBox<String> txteve;

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
    @FXML
    private TableView<artiste> tab_con_art;

    @FXML
    private TableColumn<artiste, Integer> tab_con_art_id;

    @FXML
    private TableColumn<artiste, String> tab_con_art_nom;

    @FXML
    private TableColumn<artiste, String> tab_con_art_pre;

    @FXML
    private TableColumn<artiste, String> tab_con_art_adresse;

    @FXML
    private TableColumn<artiste, String> tab_con_art_mail;

    @FXML
    private TableColumn<artiste, Integer> tab_con_art_tel;

    @FXML
    private TableColumn<artiste, String> tab_con_art_desc;

    @FXML
    private Button Contacter_art;

    @FXML
    private TextField lotfi;

    @FXML
    private Button btn_avis;

    @FXML
    private Label lblerreur1;

    @FXML
    private Label erreur;


    @FXML
    private Label lblerreur2;

    @FXML
    private TextField txtid_art;
    private client client1;
    private Connection conn=null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement1 = null;
    private ObservableList <Annonce> list;
    private ObservableList <publication> list2;
    private ObservableList <reclamation> list3;
    private ObservableList <artiste> list4;
    private ObservableList <concert> list5;
    private Annonce a;
    private evenement event;

    @FXML
    private Circle photo_circle;

//****************************Initialisation******************************************
    @FXML
    void refresh1(MouseEvent event) throws SQLException {
        client1=getClient();
        lotfi.setText(String.valueOf(client1.getId_client()));
        populateTableAnnonce();
        populateTablereclamation();
        afficherconcert();
    }
    public ClientIController() throws SQLException {
    }

    public String initData(String login)
    {
        clientlogin.setText(login);

        return login;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = ConnectionUtil.conDB();
        javafx.scene.image.Image img = new javafx.scene.image.Image("/image/away.png");
        photo_circle.setFill(new ImagePattern(img));



        try {

            ArrayList <evenement> listeve=gettypeeve();
            for (evenement  e: listeve)
            {
                txteve.getItems().add(e.getNom());
            }
            populateTablePublication();
            populateTableArtiste();
            afficherconcert();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    //****************************Action Buttons******************************************
@FXML
void btnaction(ActionEvent event) {
    if(event.getSource()==add)
    {
        if (txttitre.getText().length()==0 ) {
            txttitre.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
            new animatefx.animation.Shake(txttitre).play();
            erreur.setText("Champ Obligatoire");
            erreur.setStyle("-fx-text-fill: red");
            new animatefx.animation.FadeInDown(erreur).play();
        }
        else {
            txttitre.setStyle(null);
            erreur.setText("");
        }
        if (txteve.getSelectionModel().isEmpty())
        {
            txteve.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
            new animatefx.animation.Shake(txteve).play();
            erreur.setText("Champ Obligatoire");
            erreur.setStyle("-fx-text-fill: red");
            new animatefx.animation.FadeInDown(erreur).play();
        }

        else {
            txteve.setStyle(null);
            erreur.setText("");
        }


        if (txtprixmin.getText().length()==0)
        {
            txtprixmin.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
            new animatefx.animation.Shake(txtprixmin).play();
            erreur.setText("Champ Obligatoire");
            erreur.setStyle("-fx-text-fill: red");
            new animatefx.animation.FadeInDown(erreur).play();
        }

        else {
            txtprixmin.setStyle(null);
            erreur.setText("");
        }


        if (txtprixmax.getText().length()==0)
        {
            txtprixmax.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
            new animatefx.animation.Shake(txtprixmax).play();
            erreur.setText("Champ Obligatoire");
            erreur.setStyle("-fx-text-fill: red");
            new animatefx.animation.FadeInDown(erreur).play();
        }

        else {
            txtprixmax.setStyle(null);
            erreur.setText("");
        }

        if (txtadresse.getText().length()==0)
        {
            txtadresse.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
            new animatefx.animation.Shake(txtadresse).play();
            erreur.setText("Champ Obligatoire");
            erreur.setStyle("-fx-text-fill: red");
            new animatefx.animation.FadeInDown(erreur).play();
        }

        else {
            txtadresse.setStyle(null);
            erreur.setText("");
        }

        if (txtdesc.getText().length()==0)
        {
            txtdesc.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
            new animatefx.animation.Shake(txtdesc).play();
            erreur.setText("Champ Obligatoire");
            erreur.setStyle("-fx-text-fill: red");
            new animatefx.animation.FadeInDown(erreur).play();
        }

        else {
            txtdesc.setStyle(null);
            erreur.setText("");
        
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
            txteve.getSelectionModel().select(-1);
        }
    }}
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
            txteve.getSelectionModel().select(-1);
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
            txteve.getSelectionModel().select(-1);
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
//****************************Show selected table row******************************************
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
        txteve.getSelectionModel().select(a.getId_type_eve()-1);
    }

}
//****************************CRUD Annonce******************************************
    private void populateTableAnnonce() throws SQLException {
        list= FXCollections.observableArrayList();
        String sql ="SELECT * FROM annonce where id_client = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,(lotfi.getText()));
            resultSet=preparedStatement.executeQuery();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
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

    private void addAnnonce() throws SQLException {
//        Annonce annonce = new Annonce(1,txttitre.getText(),txtdesc.getText(),Integer.parseInt(txtprixmin.getText()),Integer.parseInt(txtprixmax.getText()), Date.from(Instant.from(txtdate.getValue().atStartOfDay(ZoneId.systemDefault()))),txtadresse.getText(),true,0,2);
        String sql = "INSERT into annonce (id_client, titre, description, prix_min, prix_max, date, adresse, etat, nb_candidature, id_type_eve) " +
//                "values (1,'salem','sbe5ir',10,25,'2020-12-12','azefazef',true,0,4) ";
                "values (?,?,?,?,?,?,?,?,?,?) ";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, lotfi.getText());
            preparedStatement.setString(2, txttitre.getText());
            preparedStatement.setString(3, txtdesc.getText());
            preparedStatement.setString(4, txtprixmin.getText());
            preparedStatement.setString(5, txtprixmax.getText());
            preparedStatement.setString(6, txtdate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            preparedStatement.setString(7, txtadresse.getText());
            preparedStatement.setString(8, "1");
            preparedStatement.setString(9, "0");
            preparedStatement.setString(10, String.valueOf((txteve.getSelectionModel().getSelectedIndex())+1));
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        populateTableAnnonce();
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
            preparedStatement.setString(2, String.valueOf((txteve.getSelectionModel().getSelectedIndex())+1));
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
//****************************Affichage Table Pub******************************************




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
                        publication pub=getTableView().getItems().get(getIndex());
                        btnconsulter.setOnAction(event -> {
                            btnconsulter.setStyle("-fx-background-color: red; ");
                            new animatefx.animation.Shake(btnconsulter).play();
                            javafx.scene.image.Image img = new javafx.scene.image.Image("/image/love.png");
                            Notifications notificationBuilder = Notifications.create()
                                    .title("Notification")
                                    .text("Vous venez d'aimé la publication"+" "+ pub.getTitre())
                                    .graphic(new ImageView(img))
                                    .hideAfter(Duration.seconds(5))
                                    .position(Pos.BOTTOM_RIGHT);


                            notificationBuilder.show();
                            try {
                                Media hit = new Media(getClass().getClassLoader().getResource("image/notification.wav").toString());
                                MediaPlayer mediaPlayer = new MediaPlayer(hit);
                                mediaPlayer.play();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            String sql="UPDATE publication set  nb_like = nb_like+1 where id_pub=?";
                            String sql1="INSERT into jaime (id_artiste, id_client, id_pub, id_commentaire) values (null,?,?,null)";
                            try {
                                preparedStatement = conn.prepareStatement(sql);
                                preparedStatement1 = conn.prepareStatement(sql1);
                                preparedStatement.setString(1, String.valueOf(pub.getId_pub()));
                                preparedStatement1.setString(1, lotfi.getText());
                                preparedStatement1.setString(2, String.valueOf(pub.getId_pub()));


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
    String sql = "SELECT * FROM reclamation where id_client=?";
    try {
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1,lotfi.getText() );
        resultSet=preparedStatement.executeQuery();

    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
    while (resultSet.next()) {
        reclamation rec = new reclamation(resultSet.getInt("id_reclamation"), resultSet.getString("titre"), resultSet.getString("contenu"), resultSet.getTimestamp("date"), resultSet.getString("etat")    );
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
    String sql = "INSERT into reclamation (titre, contenu,etat,id_client) " +
//                "values (1,'salem','sbe5ir',10,25,'2020-12-12','azefazef',true,0,4) ";
            "values (?,?,?,?) ";
    try {
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, txttitre_rec.getText());
        preparedStatement.setString(2, txtdesc_rec.getText());
        preparedStatement.setString(3, "0");
        preparedStatement.setString(4, lotfi.getText());
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
            if ( txttitre_rec.getText().length()==0 ) {
                txttitre_rec.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                new animatefx.animation.Shake(txttitre_rec).play();
                lblerreur1.setText("Veuillez saisir un Titre");
                lblerreur1.setStyle("-fx-text-fill: red");
                new animatefx.animation.FadeInDown(lblerreur1).play();
            }
            else {
                txttitre_rec.setStyle(null);
                lblerreur1.setText("");
            }
             if (txtdesc_rec.getText().length()==0)
            {
                 txtdesc_rec.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                new animatefx.animation.Shake(txtdesc_rec).play();
                lblerreur2.setText("Veuillez saisir un Contenu");
                lblerreur2.setStyle("-fx-text-fill: red");
                new animatefx.animation.FadeInDown(lblerreur2).play();
            }

else {
                 txtdesc_rec.setStyle(null);
                 lblerreur2.setText("");


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

            }}
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
            controller.initData(Integer.parseInt(txtid_pub.getText()),clientlogin.getText(),lotfi.getText());

            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
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



    ArrayList<evenement> gettypeeve() throws SQLException {
        ArrayList<evenement>  lista = new ArrayList<>();
        String sql =" select * from type_evenement ";
        resultSet=conn.createStatement().executeQuery(sql);
        while(resultSet.next())
        {
            evenement e=new evenement(resultSet.getInt(1),resultSet.getString(2));
            lista.add(e);
        }
        return lista;
    }
    /**************************** MESSAGE ***********************/
    private void populateTableArtiste() throws SQLException {
        list4 = FXCollections.observableArrayList();
        String sql = "SELECT * FROM artiste";
        resultSet = conn.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            artiste rec = new artiste(resultSet.getInt("id_artiste"), resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("adresse"), resultSet.getString("mail"),resultSet.getInt("tel"),resultSet.getString("description")    );
            list4.add(rec);
        }
        tab_con_art_id.setCellValueFactory(new PropertyValueFactory<>("id_artiste"));
        tab_con_art_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tab_con_art_pre.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        tab_con_art_adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        tab_con_art_mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        tab_con_art_tel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        tab_con_art_desc.setCellValueFactory(new PropertyValueFactory<>("description"));
        tab_con_art.setItems(list4);

    }

    @FXML
    void showselectedartiste(MouseEvent event) {
        artiste a=tab_con_art.getSelectionModel().getSelectedItem();
        if(a!=null)
        {
            txtid_art.setText(String.valueOf(a.getId_artiste()));
        }

    }

    @FXML
    void btn_contacter(ActionEvent event) {
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/ChatView.fxml"));

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

    @FXML
    void btn_avis(ActionEvent event) {
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/avis.fxml"));

            Parent p=loader.load();

            Scene scene=new Scene(p);
            avisController controller = loader.getController();
            controller.initData(Integer.parseInt(txtid_art.getText()),clientlogin.getText());

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
    @FXML
    private TextField txtsearchann;
    @FXML
    void searchann(KeyEvent event) {
        FilteredList filter = new FilteredList(list, e->true);
        txtsearchann.textProperty().addListener((observable, oldValue, newValue )-> {


            filter.setPredicate((Predicate<? super Annonce>) (Annonce annonce)->{
                if(newValue.isEmpty() || newValue==null) {
                    return true;
                }
                else if(annonce.getTitre().contains(newValue)) {
                    return true;
                }
                else if(String.valueOf(annonce.getPrix_max()).contains(newValue)) {
                    return true;
                }
                else if(String.valueOf(annonce.getPrix_min()).contains(newValue)) {
                    return true;
                }
                return false;
            });
        });

        SortedList sort = new SortedList(filter);
        sort.comparatorProperty().bind(tableAnnonce.comparatorProperty());
        tableAnnonce.setItems(sort);

    }


    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    final StackedBarChart<String, Number> sbc =
            new StackedBarChart<String, Number>(xAxis, yAxis);
    final XYChart.Series<String, Number> series1 =
            new XYChart.Series<String, Number>();
    Stage stage=new Stage();

    @FXML
    void btn_stat(ActionEvent event) {
        stage.setTitle("Statistique");
        sbc.setTitle("nombre de j'aime par publication");
        String sql = "select * FROM publication order by nb_like desc";
        try {
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                series1.getData().add(new XYChart.Data<>(resultSet.getString(4), resultSet.getInt(7)));
            }
            System.out.println(resultSet);


        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        Scene scene = new Scene(sbc, 800, 600);
        sbc.getData().addAll(series1);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private Button btnstatann;
    @FXML
    void statann(ActionEvent event) {
        stage.setTitle("Statistique");
        sbc.setTitle("nombre d'annonce par jour dernier semaine");
        String sql = "SELECT date_annonce, COUNT(*) as lotfi FROM annonce WHERE date_annonce >= DATE(NOW()) - INTERVAL 6 DAY GROUP by DAYOFWEEK(date_annonce)";
        try {
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                series1.getData().add(new XYChart.Data<>(String.valueOf(resultSet.getDate(1)), resultSet.getInt(2)));
            }
            System.out.println(resultSet);


        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        Scene scene = new Scene(sbc, 800, 600);
        sbc.getData().addAll(series1);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private Button btn_pdf;


    @FXML
    void Action_pdf(ActionEvent event) {

        String path="";
        JFileChooser j = new JFileChooser();
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int x=j.showSaveDialog(this);

        if (x==JFileChooser.APPROVE_OPTION)
        {
            path=j.getSelectedFile().getPath();
        }

        Document doc = new Document();
        try {
            PdfWriter.getInstance(doc,new FileOutputStream(path+"Liste des Réclamation.pdf"));
            doc.open();
            Image img = Image.getInstance("C:\\Users\\salma\\Documents\\GitHub\\PiDevFullArt\\images\\couv.png");
            img.scaleAbsoluteWidth(600);
            img.scaleAbsoluteHeight(92);
            img.setAlignment(Image.ALIGN_CENTER);
            doc.add(img);

            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("Liste des reclamations"));
            doc.add(new Paragraph(" "));


            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);

            PdfPCell cell;
             /***************************************************************/
            cell = new PdfPCell( new Phrase("ID",FontFactory.getFont("Comic Sans MS",12)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.ORANGE);
            table.addCell(cell);

            cell = new PdfPCell( new Phrase("Titre",FontFactory.getFont("Comic Sans MS",12)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.ORANGE);
            table.addCell(cell);

            cell = new PdfPCell( new Phrase("Contenu",FontFactory.getFont("Comic Sans MS",12)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.ORANGE);
            table.addCell(cell);

            cell = new PdfPCell( new Phrase("Etat",FontFactory.getFont("Comic Sans MS",12)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.ORANGE);
            table.addCell(cell);

            cell = new PdfPCell( new Phrase("Date",FontFactory.getFont("Comic Sans MS",12)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.ORANGE);
            table.addCell(cell);


            String sql = "SELECT * FROM reclamation ";

                preparedStatement = conn.prepareStatement(sql);
                resultSet=preparedStatement.executeQuery();


            while (resultSet.next()) {
                String id_reclamation = resultSet.getString("id_reclamation");
                cell=new PdfPCell(new Phrase(id_reclamation));
                table.addCell(cell);
                String titre=resultSet.getString("titre");
                cell=new PdfPCell(new Phrase(titre));
                table.addCell(cell);
                String contenu=resultSet.getString("contenu");
                cell=new PdfPCell(new Phrase(contenu));
                table.addCell(cell);
                String etat=resultSet.getString("etat");
                cell=new PdfPCell(new Phrase(etat));
                table.addCell(cell);
                String date=resultSet.getString("date");
                cell=new PdfPCell(new Phrase(date));
                table.addCell(cell);



            }

            doc.add(table);


            doc.close();


        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    @FXML
    private TableView<concert> tab_concert;

    @FXML
    private TableColumn<concert, Integer> col_id_concert;

    @FXML
    private TableColumn<concert, Integer> col_id_artiste;

    @FXML
    private TableColumn<concert, String> col_lieu;

    @FXML
    private TableColumn<concert, Timestamp> col_date_concert;


    private void afficherconcert() throws SQLException {
        list5 = FXCollections.observableArrayList();
        String sql = "SELECT * FROM concert ";
        try {
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        while (resultSet.next()) {
            concert crt = new concert(resultSet.getInt("id_concert"), resultSet.getInt("id_artiste"), resultSet.getString("lieu"), resultSet.getDate("date"));
            list5.add(crt);
        }
        col_id_concert.setCellValueFactory(new PropertyValueFactory<>("id_concert"));
        col_id_artiste.setCellValueFactory(new PropertyValueFactory<>("id_artiste"));
        col_lieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        col_date_concert.setCellValueFactory(new PropertyValueFactory<>("date"));
        tab_concert.setItems(list5);

    }
    @FXML
    private TextField txtid_con;
    @FXML
    void showselectedconcert(MouseEvent event) {
        concert c=tab_concert.getSelectionModel().getSelectedItem();
        if(c!=null)
        {
            txtid_con.setText(String.valueOf(c.getId_concert()));

        }
    }
    @FXML
    void participer(ActionEvent event) throws StripeException {
        Random rand = new Random();
        int rand_int1 = rand.nextInt(899999999) + 100000000;
        String code=String.valueOf(rand_int1);
        String sql = "INSERT into ticket (id_ticket, id_client,id_concert) values (?,?,?) ";
        Stripe.apiKey ="sk_test_51E1IDxDf4dmFOPGYPesehMS319W3sD4UXvYouBXxreVdm2rc02Srw1fZJ5Cp76OG1frAIAR5w1s1ZCbYSLoWZrfb00N92IJxwU"; // add your api key

        Customer a= Customer.retrieve("cus_JDydQ2OhL8YO6V");//htha li client lezem thot l id  ***







        Map<String, Object> params = new HashMap<String,Object>();
        params.put("amount", "20000");// hna thot li prix
        params.put("currency", "usd");//hna il devise eur wala usd
        params.put("description", "achat ticket concert id:"+txtid_con.getText());//hna il description
        params.put("customer", a.getId());

        Charge.create(params);
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, code);
            preparedStatement.setString(2, lotfi.getText());
            preparedStatement.setString(3, txtid_con.getText());
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        Alert aa=new Alert(Alert.AlertType.CONFIRMATION);
        aa.setContentText("Succés du paiment");
        aa.show();

    }

}
