package Controller;

import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entite.artiste;
import entite.evenement;
import entite.publication;
import entite.reclamation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
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



    private Connection conn=null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement1 = null;

    private ObservableList<reclamation> list3;
    private ObservableList <artiste> list4;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = ConnectionUtil.conDB();
        try {
          populateTablereclamation();
            populateTableArtiste();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void populateTablereclamation() throws SQLException {
        list3 = FXCollections.observableArrayList();
        String sql = "SELECT * FROM reclamation";
        try {
            preparedStatement = conn.prepareStatement(sql);
            resultSet=preparedStatement.executeQuery();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        while (resultSet.next()) {
            reclamation rec = new reclamation(resultSet.getInt("id_reclamation"), resultSet.getString("titre"), resultSet.getString("contenu"), resultSet.getTimestamp("date"), resultSet.getString("etat"),resultSet.getInt("id_artiste") ,resultSet.getInt("id_client")    );
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
            cell = new PdfPCell( new Phrase("ID", FontFactory.getFont("Comic Sans MS",12)));
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
    void showselectedrec(MouseEvent event) {
        reclamation a=tableReclamation.getSelectionModel().getSelectedItem();
        if(a!=null)
        {
            txtdesc_rec.setText(String.valueOf(a.getContenu()));
            txt_rep_rec.setText(String.valueOf(a.getEtat()));
            txtid_rec.setText(String.valueOf(a.getId_reclamation()));

        }

    }

    private void modifierReclamation() throws SQLException {
        String sql="UPDATE reclamation set  etat= ?  where id_reclamation= ?";
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
    /****************************** LISTE ARTISTES**************************/

    private void populateTableArtiste() throws SQLException {
        list4 = FXCollections.observableArrayList();
        String sql = "SELECT * FROM artiste";
        resultSet = conn.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            artiste rec = new artiste(resultSet.getInt("id_artiste"), resultSet.getString("login"),resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("adresse"), resultSet.getString("mail"),resultSet.getInt("tel"),resultSet.getString("pwd"),resultSet.getString("description"), resultSet.getInt("etat")   );
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
        String sql="UPDATE artiste set  etat= 1 where id_artiste=?";


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
        String sql="UPDATE artiste set  etat=0 where id_artiste=?";
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

        artiste a=tableArtiste.getSelectionModel().getSelectedItem();
        if(a!=null)
        {
            id_artiste.setText(String.valueOf(a.getId_artiste()));

        }
    }
}




