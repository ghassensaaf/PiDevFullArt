package Controller;

import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entite.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.ConnectionUtil;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.*;

public class adminController extends Component implements Initializable {
    @FXML
    private TableView<reclamation> tableReclamation;

    @FXML
    private TableColumn<reclamation, Integer> colid_rec;

    @FXML
    private TableColumn<reclamation, String> coltitre_rec;

    @FXML
    private TableColumn<reclamation, String> colcontenu_rec;

    @FXML
    private TableColumn<reclamation, String> coletat_rec;

    @FXML
    private TableColumn<reclamation, Timestamp> coldate_rec;

    @FXML
    private TableColumn<reclamation, Integer> id_artiste_rec;

    @FXML
    private TableColumn<reclamation, Integer> id_client_rec;

    @FXML
    private Button btn_traiter;

    @FXML
    private Button save;

    @FXML
    private TextField rech_rec_admin;

    @FXML
    private Label adminLogin;

    @FXML
    private TextArea txtdesc_rec;

    @FXML
    private TableView<artiste> tableArtiste;

    @FXML
    private TextField txtid_rec;

    @FXML
    private TextArea txt_rep_rec;

    @FXML
    private TableColumn<artiste, Integer> tab_con_art_id;

    @FXML
    private TableColumn<artiste, String> tab_art_login;

    @FXML
    private TableColumn<artiste, String> tab_con_art_nom;

    @FXML
    private TableColumn<artiste, String> tab_con_art_pre;

    @FXML
    private TableColumn<artiste, String> tab_con_art_adresse;

    @FXML
    private TableColumn<artiste, String> tab_con_art_mail;

    @FXML
    private TableColumn<artiste, Integer> tab_art_tel;

    @FXML
    private TableColumn<artiste, String> tab_art_mdp;

    @FXML
    private TableColumn<artiste, String> tab_con_art_desc;

    @FXML
    private TableColumn<artiste, Integer> tab_art_etat;
    @FXML
    private Button btn_bloquer;

    @FXML
    private TextField id_artiste;
    @FXML
    private Button btn_debloquer;


    @FXML
    private TableColumn<client, Integer> col_id;

    @FXML
    private TableColumn<client, String> col_login;

    @FXML
    private TableColumn<client, String> col_mdp;

    @FXML
    private TableColumn<client, String> col_nom;

    @FXML
    private TableColumn<client, String> col_prenom;

    @FXML
    private TableColumn<client, String> col_mail;

    @FXML
    private TableColumn<client, Integer> col_tel;

    @FXML
    private TableColumn<client, String> col_photo;

    @FXML
    private TableColumn<client, String> col_adresse;

    @FXML
    private TableColumn<client, Integer> col_etat;

    @FXML
    private Button btn_bloquer_client;

    @FXML
    private Button btn_debloquer_client;

    @FXML
    private TextField idClient;

    @FXML
    private TableView<client> tab_client;


    @FXML
    private TableColumn<Annonce, Integer> col_id_ann;

    @FXML
    private TableColumn<Annonce, Integer> col_ann_id_client;

    @FXML
    private TableColumn<Annonce, String> col_ann_titre;

    @FXML
    private TableColumn<Annonce, String> col_ann_desc;

    @FXML
    private TableColumn<Annonce, Integer> col_ann_prix_min;

    @FXML
    private TableColumn<Annonce, Integer> col_ann_prix_max;

    @FXML
    private TableColumn<Annonce, Date> col_ann_date;

    @FXML
    private TableColumn<Annonce, String> col_ann_adresse;

    @FXML
    private TableColumn<Annonce, Integer> col_ann_etat;

    @FXML
    private TableColumn<Annonce, Timestamp> col_ann_d;

    @FXML
    private TableColumn<Annonce, Integer> col_ann_nbr_con;

    @FXML
    private TableColumn<Annonce, String> col_ann_type;

    @FXML
    private Button btn_supp_ann;

    @FXML
    private Button btn_stat_ann;


    private Connection conn = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement1 = null;

    private ObservableList<reclamation> list3;
    private ObservableList<artiste> list4;
    private ObservableList<client> list5;
    private ObservableList<Annonce> list;
    private ObservableList<commentaire> list2;
    private ObservableList<avis> list9;
    private ObservableList<publication> list10;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = ConnectionUtil.conDB();
        try {
            populateTablereclamation();
            populateTableArtiste();
            populateTableClient();
            populateTableAnnonce();
            populateTablecommentaire();
            populateTableavis();
            populateTablePublication();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void populateTablereclamation() throws SQLException {
        list3 = FXCollections.observableArrayList();
        String sql = "SELECT * FROM reclamation";
        try {
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        while (resultSet.next()) {
            reclamation rec = new reclamation(resultSet.getInt("id_reclamation"), resultSet.getString("titre"), resultSet.getString("contenu"), resultSet.getTimestamp("date"), resultSet.getString("etat"), resultSet.getInt("id_artiste"), resultSet.getInt("id_client"));
            list3.add(rec);
        }
        colid_rec.setCellValueFactory(new PropertyValueFactory<>("id_reclamation"));
        coltitre_rec.setCellValueFactory(new PropertyValueFactory<>("titre"));
        colcontenu_rec.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        coldate_rec.setCellValueFactory(new PropertyValueFactory<>("date"));
        coletat_rec.setCellValueFactory(new PropertyValueFactory<>("etat"));
        id_artiste_rec.setCellValueFactory(new PropertyValueFactory<>("id_artiste"));
        id_client_rec.setCellValueFactory(new PropertyValueFactory<>("id_client"));

        tableReclamation.setItems(list3);

    }

    public void save(javafx.event.ActionEvent event) {
        String path = "";
        JFileChooser j = new JFileChooser();
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int x = j.showSaveDialog(this);

        if (x == JFileChooser.APPROVE_OPTION) {
            path = j.getSelectedFile().getPath();
        }

        Document doc = new Document();
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(path + "Liste des Réclamation.pdf"));
            doc.open();
            Image img = Image.getInstance("C:\\Users\\salma\\Documents\\GitHub\\PiDevFullArt\\images\\couv.png");
            img.scaleAbsoluteWidth(600);
            img.scaleAbsoluteHeight(92);
            img.setAlignment(Image.ALIGN_CENTER);
            doc.add(img);

            doc.add(new Paragraph(" "));

            doc.add(new Paragraph(" "));


            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);

            PdfPCell cell;
            /***************************************************************/
            cell = new PdfPCell(new Phrase("ID", FontFactory.getFont("Comic Sans MS", 12)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.ORANGE);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Titre", FontFactory.getFont("Comic Sans MS", 12)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.ORANGE);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Contenu", FontFactory.getFont("Comic Sans MS", 12)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.ORANGE);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Etat", FontFactory.getFont("Comic Sans MS", 12)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.ORANGE);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Date", FontFactory.getFont("Comic Sans MS", 12)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.ORANGE);
            table.addCell(cell);


            String sql = "SELECT * FROM reclamation ";

            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                String id_reclamation = resultSet.getString("id_reclamation");
                cell = new PdfPCell(new Phrase(id_reclamation));
                table.addCell(cell);
                String titre = resultSet.getString("titre");
                cell = new PdfPCell(new Phrase(titre));
                table.addCell(cell);
                String contenu = resultSet.getString("contenu");
                cell = new PdfPCell(new Phrase(contenu));
                table.addCell(cell);
                String etat = resultSet.getString("etat");
                cell = new PdfPCell(new Phrase(etat));
                table.addCell(cell);
                String date = resultSet.getString("date");
                cell = new PdfPCell(new Phrase(date));
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
    void showselectedrec(MouseEvent event) {
        reclamation a = tableReclamation.getSelectionModel().getSelectedItem();
        if (a != null) {
            txtdesc_rec.setText(String.valueOf(a.getContenu()));
            txt_rep_rec.setText(String.valueOf(a.getEtat()));
            txtid_rec.setText(String.valueOf(a.getId_reclamation()));

        }

    }

    private void modifierReclamation() throws SQLException {
        String sql = "UPDATE reclamation set  etat= ?  where id_reclamation= ?";
        try {
            preparedStatement1 = conn.prepareStatement(sql);
            preparedStatement1.setString(1, txt_rep_rec.getText());

            preparedStatement1.setString(2, txtid_rec.getText());
            preparedStatement1.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        populateTablereclamation();
        tableReclamation.refresh();
    }

    @FXML
    void refresh(MouseEvent event) throws SQLException {

        populateTablereclamation();
        populateTableArtiste();
        populateTableClient();
        populateTableAnnonce();
        populateTablecommentaire();
        populateTableavis();

    }

    public void traiter(javafx.event.ActionEvent event) throws SQLException {
        modifierReclamation();
    }


    /**************************** STAT REC  ***********************/
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    final StackedAreaChart<String, Number> sbc =
            new StackedAreaChart<>(xAxis, yAxis);
    final XYChart.Series<String, Number> series1 =
            new XYChart.Series<String, Number>();
    Stage stage = new Stage();

    @FXML
    void Btn_stat(ActionEvent event) {
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

    /****************************** LISTE ARTISTES**************************/

    private void populateTableArtiste() throws SQLException {
        list4 = FXCollections.observableArrayList();
        String sql = "SELECT * FROM artiste";
        resultSet = conn.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            artiste rec = new artiste(resultSet.getInt("id_artiste"), resultSet.getString("login"), resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("adresse"), resultSet.getString("mail"), resultSet.getInt("tel"), resultSet.getString("pwd"), resultSet.getString("description"), resultSet.getInt("etat"));
            list4.add(rec);
        }
        tab_con_art_id.setCellValueFactory(new PropertyValueFactory<>("id_artiste"));
        tab_art_login.setCellValueFactory(new PropertyValueFactory<>("login"));
        tab_con_art_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tab_con_art_pre.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        tab_con_art_adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        tab_con_art_mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        tab_art_tel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        tab_art_mdp.setCellValueFactory(new PropertyValueFactory<>("tel"));
        tab_con_art_desc.setCellValueFactory(new PropertyValueFactory<>("description"));
        tab_art_etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        tableArtiste.setItems(list4);

    }


    @FXML
    void Action_bloquer(ActionEvent event) throws SQLException {
        String sql = "UPDATE artiste set  etat= 1 where id_artiste=?";


        try {
            preparedStatement1 = conn.prepareStatement(sql);
            preparedStatement1.setString(1, id_artiste.getText());
            preparedStatement1.executeUpdate();
            Alert aa = new Alert(Alert.AlertType.CONFIRMATION);
            aa.setContentText("Artiste Bloqué");
            aa.show();


        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        try {
            populateTableArtiste();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tableArtiste.refresh();


    }

    @FXML
    void Action_debloquer(ActionEvent event) throws SQLException {
        String sql = "UPDATE artiste set  etat=0 where id_artiste=?";
        try {
            preparedStatement1 = conn.prepareStatement(sql);
            preparedStatement1.setString(1, id_artiste.getText());
            preparedStatement1.executeUpdate();
            Alert aa = new Alert(Alert.AlertType.CONFIRMATION);
            aa.setContentText("Artiste Débloqué");
            aa.show();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        populateTableArtiste();
        tableArtiste.refresh();
    }


    @FXML
    void showselected2(MouseEvent event) {

        artiste a = tableArtiste.getSelectionModel().getSelectedItem();
        if (a != null) {
            id_artiste.setText(String.valueOf(a.getId_artiste()));

        }
    }


    private void populateTableClient() throws SQLException {
        list5 = FXCollections.observableArrayList();
        String sql = "SELECT * FROM client";
        resultSet = conn.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            client rec = new client(resultSet.getInt("id_client"), resultSet.getString("login"), resultSet.getString("pwd"), resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("mail"), resultSet.getInt("tel"), resultSet.getString("photo"), resultSet.getString("adresse"), resultSet.getInt("etat"));
            list5.add(rec);
        }
        col_id.setCellValueFactory(new PropertyValueFactory<>("id_client"));
        col_login.setCellValueFactory(new PropertyValueFactory<>("login"));
        col_mdp.setCellValueFactory(new PropertyValueFactory<>("pwd"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        col_tel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        col_photo.setCellValueFactory(new PropertyValueFactory<>("photo"));
        col_adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        col_etat.setCellValueFactory(new PropertyValueFactory<>("etat"));

        tab_client.setItems(list5);

    }

    @FXML
    void Action_debloquer_client(ActionEvent event) {
        String sql = "UPDATE client set  etat= 0 where id_client=?";


        try {
            preparedStatement1 = conn.prepareStatement(sql);
            preparedStatement1.setString(1, idClient.getText());
            preparedStatement1.executeUpdate();
            Alert aa = new Alert(Alert.AlertType.CONFIRMATION);
            aa.setContentText("Client débloqué");
            aa.show();


        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        try {
            populateTableClient();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tab_client.refresh();


    }

    @FXML
    void Action_bloquer_client(ActionEvent event) {
        String sql = "UPDATE client set  etat= 1 where id_client=?";


        try {
            preparedStatement1 = conn.prepareStatement(sql);
            preparedStatement1.setString(1, idClient.getText());
            preparedStatement1.executeUpdate();
            Alert aa = new Alert(Alert.AlertType.CONFIRMATION);
            aa.setContentText("Client bloqué");
            aa.show();


        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        try {
            populateTableClient();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tab_client.refresh();


    }

    @FXML
    void showSelected(MouseEvent event) {

        client c = tab_client.getSelectionModel().getSelectedItem();
        if (c != null) {
            idClient.setText(String.valueOf(c.getId_client()));

        }

    }

    @FXML
    private TableView<Annonce> tableAnnonce;

    private void populateTableAnnonce() throws SQLException {
        list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM annonce ";
        try {
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        while (resultSet.next()) {
            Annonce annonce = new Annonce(resultSet.getInt("id_annonce"), resultSet.getInt("id_client"), resultSet.getString("titre"), resultSet.getString("description"), resultSet.getInt("prix_min"), resultSet.getInt("prix_max"), resultSet.getDate("date"), resultSet.getString("adresse"), resultSet.getBoolean("etat"), resultSet.getTimestamp("date_annonce"), resultSet.getInt("nb_candidature"), resultSet.getInt("id_type_eve"));
            list.add(annonce);
        }
        col_id_ann.setCellValueFactory(new PropertyValueFactory<>("id_annonce"));
        col_ann_id_client.setCellValueFactory(new PropertyValueFactory<>("id_client"));
        col_ann_titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        col_ann_desc.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_ann_prix_min.setCellValueFactory(new PropertyValueFactory<>("prix_min"));
        col_ann_prix_max.setCellValueFactory(new PropertyValueFactory<>("prix_max"));
        col_ann_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_ann_adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        col_ann_etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        col_ann_d.setCellValueFactory(new PropertyValueFactory<>("date_annonce"));
        col_ann_nbr_con.setCellValueFactory(new PropertyValueFactory<>("nb_candidature"));
        col_ann_type.setCellValueFactory(new PropertyValueFactory<>("id_type_eve"));
        tableAnnonce.setItems(list);
    }


    @FXML
    void stat_annonce(ActionEvent event) {
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
    private TextField idAnn;

    @FXML
    void supp_ann(ActionEvent event) throws SQLException {
        String sql = "delete from annonce where id_annonce = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, idAnn.getText());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        populateTableAnnonce();

    }

    @FXML
    void ShowselectedAnn(MouseEvent event) {
        Annonce c = tableAnnonce.getSelectionModel().getSelectedItem();
        if (c != null) {
            idAnn.setText(String.valueOf(c.getId_annonce()));

        }

    }

    @FXML
    private TableView<commentaire> tab_commentaire;

    @FXML
    private TableColumn<commentaire, Integer> colid;

    @FXML
    private TableColumn<commentaire, String> colContenu_com;

    @FXML
    private TableColumn<commentaire, Integer> colid_pub_com;

    @FXML
    private TableColumn<commentaire, Integer> colnb_like_com;

    @FXML
    private Button btn_del_com;

    @FXML
    private Button btn_del_avis;

    private void populateTablecommentaire() throws SQLException {
        list2 = FXCollections.observableArrayList();
        String sql = "SELECT * FROM commentaire ";
        try {
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        while (resultSet.next()) {
            commentaire rec = new commentaire(resultSet.getInt("id_commentaire"), resultSet.getInt("id_pub"), resultSet.getString("contenu"), resultSet.getInt("nb_like"));
            list2.add(rec);
        }
        colid.setCellValueFactory(new PropertyValueFactory<>("id_commentaire"));
        colContenu_com.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        colid_pub_com.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        colnb_like_com.setCellValueFactory(new PropertyValueFactory<>("nb_like"));
        tab_commentaire.setItems(list2);
    }

    @FXML
    private TextField lbl_com;

    @FXML
    void show_comm(MouseEvent event) {
        commentaire c = tab_commentaire.getSelectionModel().getSelectedItem();
        if (c != null) {
            lbl_com.setText(String.valueOf(c.getId_commentaire()));

        }
    }

    private void deleteCommentaire() throws SQLException {
        String sql = "delete from commentaire where id_commentaire = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, lbl_com.getText());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        populateTablecommentaire();
    }

    @FXML
    void supp_comm(ActionEvent event) {
        try {
            deleteCommentaire();
            Alert aa = new Alert(Alert.AlertType.CONFIRMATION);
            aa.setContentText("Commentaire supprimer");
            aa.show();
        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }
    }

    @FXML
    private TableView<avis> tab_avis;

    @FXML
    private TableColumn<avis, Integer> colid_avis;

    @FXML
    private TableColumn<avis, Integer> col_idartista_avis;

    @FXML
    private TableColumn<avis, Integer> colnote_avis;

    @FXML
    private TableColumn<avis, String> colContenu_avis;

    private void populateTableavis() throws SQLException {
        list9 = FXCollections.observableArrayList();
        String sql = "SELECT * FROM avis";
        try {
            preparedStatement = conn.prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();


        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        while (resultSet.next()) {
            avis av = new avis(resultSet.getInt("id_avis"), resultSet.getInt("id_artiste"), resultSet.getInt("note"), resultSet.getString("contenu"));
            list9.add(av);
        }
        colid_avis.setCellValueFactory(new PropertyValueFactory<>("id_avis"));
        col_idartista_avis.setCellValueFactory(new PropertyValueFactory<>("id_artiste"));
        colnote_avis.setCellValueFactory(new PropertyValueFactory<>("note"));
        colContenu_avis.setCellValueFactory(new PropertyValueFactory<>("contenu"));

        tab_avis.setItems(list9);

    }

    @FXML
    void del_avis(ActionEvent event) throws SQLException {
        String sql = "delete from avis WHERE id_avis=?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(lblavis.getText()));

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        populateTableavis();
    }

    @FXML
    private TextField lblavis;

    @FXML
    void showavis(MouseEvent event) {
        avis c = tab_avis.getSelectionModel().getSelectedItem();
        if (c != null) {
            lblavis.setText(String.valueOf(c.getId_avis()));

        }
    }

    @FXML
    private TextField lblpub;

    @FXML
    private TableView<publication> tab_pub;

    @FXML
    private TableColumn<publication, Integer> colid_pubb;

    @FXML
    private TableColumn<publication, Integer> col_artiste_pub;

    @FXML
    private TableColumn<publication, Integer> col_type_pub;

    @FXML
    private TableColumn<publication, String> col_titre_pub;

    @FXML
    private TableColumn<publication, String> col_contenu_pub;

    @FXML
    private TableColumn<publication, Timestamp> col_date_pub;

    @FXML
    private TableColumn<publication, Integer> col_nblike_pub;

    private void populateTablePublication() throws SQLException {
        list10 = FXCollections.observableArrayList();
        String sql = "SELECT * FROM publication";
        resultSet = conn.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            publication pub = new publication(resultSet.getInt("id_pub"), resultSet.getInt("id_artiste"), resultSet.getInt("id_type"), resultSet.getString("titre"), resultSet.getString("contenu"), resultSet.getTimestamp("date_pub"), resultSet.getInt("nb_like"));
            list10.add(pub);
        }
        colid_pubb.setCellValueFactory(new PropertyValueFactory<>("id_pub"));
        col_artiste_pub.setCellValueFactory(new PropertyValueFactory<>("id_artiste"));
        col_type_pub.setCellValueFactory(new PropertyValueFactory<>("id_type"));
        col_titre_pub.setCellValueFactory(new PropertyValueFactory<>("titre"));
        col_contenu_pub.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        col_date_pub.setCellValueFactory(new PropertyValueFactory<>("date_pub"));
        col_nblike_pub.setCellValueFactory(new PropertyValueFactory<>("nb_like"));
        tab_pub.setItems(list10);

    }


    @FXML
    void btn_stat_pub(ActionEvent event) {
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
    void back_home(ActionEvent event) {
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/Client.fxml"));
            Parent p=loader.load();
            Scene scene=new Scene(p);

            ClientIController controller = loader.getController();

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




