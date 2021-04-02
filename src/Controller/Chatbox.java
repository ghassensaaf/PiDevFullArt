package Controller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.JFrame;
import entite.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import util.ConnectionUtil;

import javax.swing.*;
import java.net.URL;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
public class Chatbox implements Initializable {
    @FXML
    private ScrollPane box;

    @FXML
    private VBox Chatbox;


    @FXML
    private ListView<String> affiche;

    @FXML
    private Button send;

    @FXML
    private TextField msg;

    @FXML
    private Label clientlogin;

    @FXML
    private TextField txtid_art;

    @FXML
    private TextField id_client;



    private client artiste1;

    private Connection conn=null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    private ObservableList<message> list;
    private ObservableList<message> list1;
    private ObservableList<artiste> list4;

    ObservableList<message> chat =FXCollections.observableArrayList();




    public void initData(int i, String ll) {
        txtid_art.setText(String.valueOf(i));
        clientlogin.setText(ll);

    }

    public void initialize(URL location, ResourceBundle resources) {
        conn = ConnectionUtil.conDB();
        try {
            Loadmsg();
            populateTableArtiste();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


//

    }

    /**************************** GET Artiste  ***********************/
    private artiste getartisteid() throws SQLException {
        String sql ="SELECT * FROM artiste where login= ?";
        artiste a=null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, clientlogin.getText());
            resultSet=preparedStatement.executeQuery();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        if (resultSet.next())
        {
            a = new artiste(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),resultSet.getInt(7),resultSet.getString(8),resultSet.getString(9),resultSet.getString(10),resultSet.getString(11));
        }
        return a;
    }

    /**************************** GET CLIENT  ***********************/
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

    /**************************** MESSAGE ENVOYER ***********************/

    private final List<Label> message= new ArrayList<>();


    @FXML
    private ListView<message> affichage;




    private void Loadmsg() throws SQLException {
        list = FXCollections.observableArrayList();

        String sql = "SELECT * FROM message WHERE id_artiste_dest=? AND id_client_exp=? OR id_artiste_exp=? AND id_client_dest=? order by date ASC";
        {
            try {
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, txtid_art.getText());
                preparedStatement.setString(2, id_client.getText());
                preparedStatement.setString(3, txtid_art.getText());
                preparedStatement.setString(4, id_client.getText());

                resultSet = preparedStatement.executeQuery();



            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }

            while (resultSet.next()) {
                message rec = new message(resultSet.getInt("id_message"), resultSet.getString("contenu"), resultSet.getTimestamp("date"),resultSet.getInt("id_client_dest"),resultSet.getInt("id_client_exp"),resultSet.getInt("id_artiste_dest"),resultSet.getInt("id_artiste_exp"));
                list.add(rec);
            }
            affichage.setItems(list);
        }
    }

    private String getNomClient() throws SQLException {
        String sql ="SELECT * FROM client where id_client= ?";
        client ccc=null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, id_client.getText());
            resultSet=preparedStatement.executeQuery();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        if (resultSet.next())
        {
            ccc = new client(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),resultSet.getInt(7),resultSet.getString(8),resultSet.getString(9));
        }
        return ccc.getNom();
    }

    private void ajouterMessage() throws SQLException {
        String sql = "INSERT into message (contenu,id_client_exp,id_artiste_dest) " +
                "values (?,?,?) ";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, msg.getText());
            preparedStatement.setString( 2, id_client.getText());
            preparedStatement.setString(3, txtid_art.getText());
            preparedStatement.executeUpdate();



        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        affichage.getItems().clear();
        Loadmsg();
    }
    @FXML
    void Send(ActionEvent event) {
        if (event.getSource() == send) {
            if (msg.getText().length()==0) {
                msg.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
                new animatefx.animation.Shake(msg).play();
            }
            else {
                msg.setStyle(null);

                try {

                    ajouterMessage();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } finally {
                    msg.setText("");
                }
            }
        }
    }



    @FXML
    void refresh(MouseEvent event) throws SQLException {

        artiste1=getClient();
        id_client.setText(String.valueOf(artiste1.getId_client()));
        Loadmsg();
        affichage.setCellFactory(param -> {


            Circle cir2 = new Circle(25,25,12);
            cir2.setStroke(Color.SEAGREEN);
           Image img=new Image("/image/users/check.jpg");
            Image img2=new Image("/image/users/salma.jpg");


            ListCell<message> cell = new ListCell<message>(){
                Label lblUserLeft = new Label();
                Label lblTextLeft = new Label();
                HBox hBoxLeft = new HBox(lblUserLeft, lblTextLeft);

                Label lblUserRight = new Label();
                Label lblTextRight = new Label();
                HBox hBoxRight = new HBox(lblTextRight, lblUserRight);

                {
                    hBoxLeft.setAlignment(Pos.CENTER_LEFT);
                    hBoxLeft.setSpacing(5);
                    hBoxRight.setAlignment(Pos.CENTER_RIGHT);
                    hBoxRight.setSpacing(5);
                }
                @Override
                protected void updateItem(message item, boolean empty) {
                    super.updateItem(item, empty);

                    if(empty)
                    {
                        setText(null);
                        setGraphic(null);
                    }
                    else{
                        if(item.getId_artiste_dest()<999999999 && item.getId_artiste_dest()>0)
                        {
                            cir2.setFill(new ImagePattern(img));

                            cir2.setEffect(new DropShadow(+15d, 0d, +1d, Color.DARKCYAN));

                            lblUserRight.setGraphic(cir2);

                            lblTextRight.setText(item.getContenu());
                            lblTextRight.setStyle("-fx-background-color: rgb(241,194,50); -fx-background-radius: 8px;");
                         lblTextRight.setPadding(new Insets(6));
                         lblTextRight.setFont(new Font(14));


                            setGraphic(hBoxRight);

                        }
                        else{
                            cir2.setFill(new ImagePattern(img2));
                            cir2.setEffect(new DropShadow(+15d, 0d, +1d, Color.DARKBLUE));
                            lblUserLeft.setGraphic(cir2);

                            lblTextLeft.setText(item.getContenu());
                            lblTextLeft.setStyle("-fx-background-color: rgb(179,231,244); -fx-background-radius: 8px;");
                            lblTextLeft.setPadding(new Insets(6));
                            lblTextLeft.setFont(new Font(14));
                            setGraphic(hBoxLeft);
                        }
                    }
                }

            };

            return cell;
        });
    }

/********************************************************************************************************/
    @FXML
    private TableView<artiste> tb_art;

    @FXML
    private TableColumn<artiste, Integer> id_art;

    @FXML
    private TableColumn<artiste, String> Nom_art;


    /**************************** LISTE DES ARTISTES ***********************/
    private void populateTableArtiste() throws SQLException {
        list4 = FXCollections.observableArrayList();
        String sql = "SELECT * FROM artiste";
        resultSet = conn.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            artiste rec = new artiste(resultSet.getInt("id_artiste"), resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("adresse"), resultSet.getString("mail"),resultSet.getInt("tel"),resultSet.getString("description")    );
            list4.add(rec);
        }
        id_art.setCellValueFactory(new PropertyValueFactory<>("id_artiste"));
        Nom_art.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tb_art.setItems(list4);

    }


    @FXML
    void showselectedartiste(MouseEvent event) {
        artiste a=tb_art.getSelectionModel().getSelectedItem();
        if(a!=null)
        {
            txtid_art.setText(String.valueOf(a.getId_artiste()));
        }

    }
    @FXML
    void refresh2(MouseEvent event) {
        try {
            artiste1=getClient();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        id_client.setText(String.valueOf(artiste1.getId_client()));
        try {
            Loadmsg();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        affichage.setCellFactory(param -> {


            Circle cir2 = new Circle(25,25,12);
            cir2.setStroke(Color.SEAGREEN);
            Image img=new Image("/image/users/check.jpg");
            Image img2=new Image("/image/users/salma.jpg");


            ListCell<message> cell = new ListCell<message>(){
                Label lblUserLeft = new Label();
                Label lblTextLeft = new Label();
                HBox hBoxLeft = new HBox(lblUserLeft, lblTextLeft);

                Label lblUserRight = new Label();
                Label lblTextRight = new Label();
                HBox hBoxRight = new HBox(lblTextRight, lblUserRight);

                {
                    hBoxLeft.setAlignment(Pos.CENTER_LEFT);
                    hBoxLeft.setSpacing(5);
                    hBoxRight.setAlignment(Pos.CENTER_RIGHT);
                    hBoxRight.setSpacing(5);
                }
                @Override
                protected void updateItem(message item, boolean empty) {
                    super.updateItem(item, empty);

                    if(empty)
                    {
                        setText(null);
                        setGraphic(null);
                    }
                    else{
                        if(item.getId_artiste_dest()<999999999 && item.getId_artiste_dest()>0)
                        {
                            cir2.setFill(new ImagePattern(img));

                            cir2.setEffect(new DropShadow(+15d, 0d, +1d, Color.DARKCYAN));

                            lblUserRight.setGraphic(cir2);

                            lblTextRight.setText(item.getContenu());
                            lblTextRight.setStyle("-fx-background-color: rgb(241,194,50); -fx-background-radius: 8px;");
                            lblTextRight.setPadding(new Insets(6));
                            lblTextRight.setFont(new Font(14));


                            setGraphic(hBoxRight);

                        }
                        else{
                            cir2.setFill(new ImagePattern(img2));
                            cir2.setEffect(new DropShadow(+15d, 0d, +1d, Color.DARKBLUE));
                            lblUserLeft.setGraphic(cir2);

                            lblTextLeft.setText(item.getContenu());
                            lblTextLeft.setStyle("-fx-background-color: rgb(179,231,244); -fx-background-radius: 8px;");
                            lblTextLeft.setPadding(new Insets(6));
                            lblTextLeft.setFont(new Font(14));
                            setGraphic(hBoxLeft);
                        }
                    }
                }

            };

            return cell;
        });

    }

    @FXML
    private Button btnretour;
    @FXML
    void back_to_client_home(ActionEvent event) {

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
    private TextField rech;

    @FXML
    void search1(KeyEvent event) {
        FilteredList filter = new FilteredList(list4, e->true);
        rech.textProperty().addListener((observable, oldValue, newValue )-> {


            filter.setPredicate((Predicate<? super artiste>) (artiste artiste)->{
                if(newValue.isEmpty() || newValue==null) {
                    return true;

                }
                else if(artiste.getNom().contains(newValue)) {
                    return true;
                }
                return false;

            });
        });

        SortedList sort = new SortedList(filter);
        sort.comparatorProperty().bind(tb_art.comparatorProperty());
        tb_art.setItems(sort);

    }


    }

