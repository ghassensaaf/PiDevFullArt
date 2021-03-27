package Controller;

import com.sun.org.apache.bcel.internal.generic.INEG;
import entite.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import java.util.logging.Logger;
import java.util.logging.Level;

import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import util.ConnectionUtil;

import javax.management.Notification;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class detailPubController implements Initializable {
   private client client1;
    @FXML
    private TableView<jaime> tabJaimeArtiste;

    @FXML
    private PieChart pieChart;

    @FXML
    private TableView<jaime> tableJaimeClient;

    @FXML
    private TableView<commentaire> tab_commentaire;

    @FXML
    private TableColumn<commentaire, Integer> colid_com;

    @FXML
    private TableColumn<commentaire, String> colContenu_com;

    @FXML
    private TableColumn<commentaire, Integer> colnb_like_com;
    @FXML
    private TextArea txt_com;
    @FXML
    private Button btn_supprimer_com;

    @FXML
    private Button btn_modifier_com;

    @FXML
    private Button btn_ajouter_com;
    @FXML
    private TextField recherche_com;

    @FXML
    private TextField detailpub;
    @FXML
    private Label login;
    @FXML
    private TextField id_comm;
    @FXML
    private TableColumn<commentaire, String> collike_com;


    @FXML
    private TableColumn<commentaire, Integer> coljaimeArtiste;


    @FXML
    private TableColumn<commentaire, String> coljaimeClient;
    @FXML
    private Label idPub;
    @FXML
    private Button btn_stat;
    @FXML
    private TextField lotfi;
    private Connection conn;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement1 = null;
    private ObservableList<commentaire> list;
    private ObservableList<jaime> list1;


    public void initData(int i, String ll, String j) {
        detailpub.setText(String.valueOf(i));
        lotfi.setText(j);
        login.setText(ll);


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = ConnectionUtil.conDB();
        try {

            populateTablecommentaire();
            populateTablejaimeArtiste();
            populateTablejaimeClient();
            LoadChart1();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /*********************COMMENTAIRE*******************/
    private void populateTablecommentaire() throws SQLException {
        list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM commentaire WHERE id_pub=?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, detailpub.getText());

            resultSet=preparedStatement.executeQuery();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        while (resultSet.next()) {
            commentaire rec = new commentaire(resultSet.getInt("id_commentaire"), resultSet.getString("contenu"), resultSet.getInt("nb_like"));
            list.add(rec);
        }
        colid_com.setCellValueFactory(new PropertyValueFactory<>("id_commentaire"));
        colContenu_com.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        colnb_like_com.setCellValueFactory(new PropertyValueFactory<>("nb_like"));


        javafx.util.Callback<TableColumn<commentaire,String>, TableCell<commentaire,String>> cellFactory=(param)->{
            final TableCell<commentaire,String> cell= new TableCell<commentaire,String>(){
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
                            commentaire pub=getTableView().getItems().get(getIndex());
                            String sql="UPDATE commentaire set  nb_like = nb_like+1 where id_commentaire=?";
                            try {
                                preparedStatement = conn.prepareStatement(sql);
                                preparedStatement.setString(1, String.valueOf(pub.getId_commentaire()));
                                preparedStatement.executeUpdate();
                            } catch (SQLException ex) {
                                System.err.println(ex.getMessage());
                            }
                            try {
                                populateTablecommentaire();
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
        collike_com.setCellFactory(cellFactory);

        tab_commentaire.setItems(list);



    }

    /*************AJOUTER COMMENTAIRE**************/
    private void ajouterCommentaire() throws SQLException {
        String sql = "INSERT into commentaire (contenu,nb_like,id_pub) " +
                "values (?,?,?) ";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, txt_com.getText());
            preparedStatement.setString(2, "0");
            preparedStatement.setString(3, detailpub.getText());

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        populateTablecommentaire();
        populateTablejaimeArtiste();
    }

    @FXML
    void Action_com(ActionEvent event) {
        if (event.getSource() == btn_ajouter_com) {
            try {
                ajouterCommentaire();
                Alert aa = new Alert(Alert.AlertType.CONFIRMATION);
                aa.setContentText("Commentaire ajoutée");
                aa.show();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                txt_com.setText("");
                id_comm.setText("");
            }
        }
        else if (event.getSource() == btn_modifier_com) {
            try {
                editCommentaire();
                Alert aa = new Alert(Alert.AlertType.CONFIRMATION);
                aa.setContentText("Commentaire modifié");
                aa.show();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                txt_com.setText("");
                id_comm.setText("");

            }
        }
        else if (event.getSource() == btn_supprimer_com) {
            try {
                deleteCommentaire();
                Alert aa = new Alert(Alert.AlertType.CONFIRMATION);
                aa.setContentText("Commentaire supprimer");
                aa.show();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                txt_com.setText("");
                id_comm.setText("");
            }
        }
    }


    @FXML
    void showselected(MouseEvent event) {
        commentaire a=tab_commentaire.getSelectionModel().getSelectedItem();
        if(a!=null)
        {
            txt_com.setText(String.valueOf(a.getContenu()));
            id_comm.setText(String.valueOf(a.getId_commentaire()));



        }

    }
    private void editCommentaire() throws SQLException {
//        Annonce annonce = new Annonce(1,txttitre.getText(),txtdesc.getText(),Integer.parseInt(txtprixmin.getText()),Integer.parseInt(txtprixmax.getText()), Date.from(Instant.from(txtdate.getValue().atStartOfDay(ZoneId.systemDefault()))),txtadresse.getText(),true,0,2);
        String sql="UPDATE commentaire set  contenu= ?  where id_commentaire=?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, txt_com.getText());
            preparedStatement.setString(2, id_comm.getText());



            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        populateTablecommentaire();
        tab_commentaire.refresh();
    }

    private void deleteCommentaire() throws SQLException {
//        Annonce annonce = new Annonce(1,txttitre.getText(),txtdesc.getText(),Integer.parseInt(txtprixmin.getText()),Integer.parseInt(txtprixmax.getText()), Date.from(Instant.from(txtdate.getValue().atStartOfDay(ZoneId.systemDefault()))),txtadresse.getText(),true,0,2);
        String sql="delete from commentaire where id_commentaire = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, id_comm.getText());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        populateTablecommentaire();
    }

    private void populateTablejaimeArtiste() throws SQLException {
        list1 = FXCollections.observableArrayList();
        String sql = "SELECT * FROM jaime WHERE id_pub=? and id_artiste IS NOT NULL ";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, detailpub.getText());

            resultSet=preparedStatement.executeQuery();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        while (resultSet.next()) {
            jaime rec = new jaime(resultSet.getInt("id_like"), resultSet.getInt("id_artiste"),resultSet.getString("nom"), resultSet.getInt("id_client") , resultSet.getInt("id_pub"));
            list1.add(rec);

        }
        coljaimeArtiste.setCellValueFactory(new PropertyValueFactory<>("id_artiste"));
        tabJaimeArtiste.setItems(list1);

    }

    private client getClient() throws SQLException {
        String sql ="SELECT * FROM client ";
        client ccc=null;
        try {
            preparedStatement = conn.prepareStatement(sql);
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

    private void populateTablejaimeClient() throws SQLException {
        list1 = FXCollections.observableArrayList();
        String sql = "SELECT * FROM jaime WHERE id_pub=? and id_client and nom IS NOT NULL ";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, detailpub.getText());

            resultSet=preparedStatement.executeQuery();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        while (resultSet.next()) {
            jaime rec = new jaime(resultSet.getInt("id_like"), resultSet.getInt("id_artiste"),resultSet.getString("nom") ,resultSet.getInt("id_client") , resultSet.getInt("id_pub"));
            list1.add(rec);

        }
        coljaimeClient.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tableJaimeClient.setItems(list1);

    }

    @FXML
    void back_to_client_home(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/Client.fxml"));
            Parent p=loader.load();
            Scene scene=new Scene(p);

            ClientIController controller = loader.getController();
            controller.initData(login.getText());
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
    void refresh(MouseEvent event) throws SQLException {

        populateTablejaimeArtiste();
        populateTablecommentaire();
        populateTablejaimeClient();


    }


    @FXML
    void search2(KeyEvent event) {
        FilteredList filter = new FilteredList(list, e->true);
        recherche_com.textProperty().addListener((observable, oldValue, newValue )-> {


            filter.setPredicate((Predicate<? super commentaire>) (commentaire commentaire)->{
                if(newValue.isEmpty() || newValue==null) {
                    return true;
                }
                else if(commentaire.getContenu().contains(newValue)) {
                    return true;
                }

                return false;
            });
        });

        SortedList sort = new SortedList(filter);
        sort.comparatorProperty().bind(tab_commentaire.comparatorProperty());
        tab_commentaire.setItems(sort);


    }
    private void LoadChart1()  {

        ObservableList<PieChart.Data>data = FXCollections.observableArrayList();
        String sql = "select * FROM commentaire order by nb_like desc\n";


        try {

            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {


                data.add(new PieChart.Data(resultSet.getString(3), resultSet.getInt(4)));
//                System.out.println(resultSet.getString(4));
//                System.out.println(resultSet.getInt(7));

            }
//            System.out.println(data);

        } catch (SQLException ex) {
            Logger.getLogger(artisteController.class.getName()).log(Level.SEVERE,null,ex);
        }



        pieChart.setTitle("Nombre des jaimes Par commentaire");
        pieChart.setLegendSide(Side.RIGHT);

        pieChart.setData(data);


    }
}



