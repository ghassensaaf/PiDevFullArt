package Controller;

import entite.Annonce;
import entite.Candidature;
import entite.publication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import util.ConnectionUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class detailAnnonceController implements Initializable {

    @FXML
    private TableView<Candidature> tabcandidature;

    @FXML
    private TableColumn<Candidature, Integer> colid;

    @FXML
    private TableColumn<Candidature, Integer> colartiste;

    @FXML
    private TableColumn<Candidature, String> colcontenu;

    @FXML
    private TableColumn<Candidature, Integer> coletat;

    @FXML
    private TableColumn<Candidature, Integer> colprix;

    @FXML
    private TableColumn<Candidature, Timestamp> coldate;

    @FXML
    private TableColumn<Candidature, String> colaccepter;

    @FXML
    private TableColumn<Candidature, String> colrejeter;

    @FXML
    private Label clientlogin;
    @FXML
    private Label etatann;
    @FXML
    private Button btnretour;

    @FXML
    private TextField txtidann;

    @FXML
    private Label titreann;

    @FXML
    private TextArea txtcontenu;

    @FXML
    private Label prixmin;

    @FXML
    private Label prixmax;

    private Connection conn=null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement1 = null;
    PreparedStatement preparedStatement2 = null;
    private ObservableList<Candidature> list;
    Candidature can=null;
    Annonce aa=null;
    String loginn;

    public void initData(Annonce a, String ll) {
        aa=a;
        loginn=ll;
        txtidann.setText(String.valueOf(a.getId_annonce()));
        clientlogin.setText(ll);
        titreann.setText("Titre : "+a.getTitre());
        txtcontenu.setText(a.getDescription());
        prixmin.setText("Prix min : "+a.getPrix_min());
        prixmax.setText("Prix max : "+a.getPrix_max());

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = ConnectionUtil.conDB();
        try {
            populateTableCandidature();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
    private void populateTableCandidature() throws SQLException {
        list= FXCollections.observableArrayList();
        String sql ="SELECT * FROM candidature";
        resultSet=conn.createStatement().executeQuery(sql);
        while(resultSet.next())
        {
            Candidature candidature=new Candidature(resultSet.getInt("id_candidature"),resultSet.getInt("id_annonce"),resultSet.getString("contenu"),resultSet.getInt("prix"),resultSet.getInt("etat"),resultSet.getInt("id_artiste"),resultSet.getTimestamp("date"));
            list.add(candidature);
        }
        colid.setCellValueFactory(new PropertyValueFactory<>("id_candidature"));
        colartiste.setCellValueFactory(new PropertyValueFactory<>("id_artiste"));
        colcontenu.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        coletat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        colprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tabcandidature.setItems(list);
        javafx.util.Callback<TableColumn<Candidature,String>, TableCell<Candidature,String>> cellFactory=(param)->{
            final TableCell<Candidature,String> cell= new TableCell<Candidature,String>(){
                @Override
                public void updateItem(String item,boolean empty){
                    super.updateItem(item,empty);
                    if(empty){
                        setGraphic(null);
                        setText(null);
                    }
                    else {
                        Button btnconsulter=new Button("Accepter");
                        btnconsulter.setOnAction(event -> {
                            can=getTableView().getItems().get(getIndex());
                            aa.setEtat(false);
                            String sql="UPDATE candidature set  etat=1 where id_candidature=?";
                            String sql2="UPDATE candidature set etat=2 where id_candidature != ? ";
                            String sql1="UPDATE annonce set etat = 0 where id_annonce= ?";
                            try {
                                preparedStatement = conn.prepareStatement(sql);
                                preparedStatement1 = conn.prepareStatement(sql1);
                                preparedStatement2= conn.prepareStatement(sql2);
                                preparedStatement.setString(1, String.valueOf(can.getId_candidature()));
                                preparedStatement2.setString(1, String.valueOf(can.getId_candidature()));
                                preparedStatement1.setString(1, String.valueOf(can.getId_annonce()));
                                preparedStatement.executeUpdate();
                                preparedStatement1.executeUpdate();
                                preparedStatement2.executeUpdate();
                            } catch (SQLException ex) {
                                System.err.println(ex.getMessage());
                            }
                            try {
                                populateTableCandidature();
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }

                        });
                        if(aa.isEtat())
                        {
                            setGraphic(btnconsulter);
                            setText(null);
                        }
                        else
                        {
                            etatann.setText("Annonce Férmée");
                        }

                    }

                }
            };
            return cell;
        };

        colaccepter.setCellFactory(cellFactory);
        javafx.util.Callback<TableColumn<Candidature,String>, TableCell<Candidature,String>> cellFactory1=(param)->{
            final TableCell<Candidature,String> cell= new TableCell<Candidature,String>(){
                @Override
                public void updateItem(String item,boolean empty){
                    super.updateItem(item,empty);
                    if(empty){
                        setGraphic(null);
                        setText(null);
                    }
                    else {
                        Button btnconsulter=new Button("Rejeter");
                        btnconsulter.setOnAction(event -> {
                            can=getTableView().getItems().get(getIndex());
                            String sql="UPDATE candidature set  etat=2 where id_candidature=?";
                            try {
                                preparedStatement = conn.prepareStatement(sql);
                                preparedStatement.setString(1, String.valueOf(can.getId_candidature()));
                                preparedStatement.executeUpdate();
                            } catch (SQLException ex) {
                                System.err.println(ex.getMessage());
                            }
                            try {
                                populateTableCandidature();
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }

                        });
                        if(aa.isEtat())
                        {
                            setGraphic(btnconsulter);
                            setText(null);

                        }
                        else
                        {
                            etatann.setText("Annonce Férmée");
                        }
                    }

                }
            };
            return cell;
        };

        colrejeter.setCellFactory(cellFactory1);
    }

}
