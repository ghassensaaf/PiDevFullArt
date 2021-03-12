/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testfarah;

import entite.service;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.ConnectionUtil;

/**
 *
 * @author User P
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private TextField txtnom;
    @FXML
    private Label labnom;
    @FXML
    private TextField txtprix;
    @FXML
    private Label labprix;
    @FXML
    private TextField txtart;
    @FXML
    private TextField txtdetail;
    @FXML
    private Label labidart;
    @FXML
    private Label labdetail;
    
    
    
     
    @FXML
    private TableView<service> tabservice;
    @FXML
    private TableColumn<service, Integer> colid;
    @FXML
    private TableColumn<service, String> colnom;
    @FXML
    private TableColumn<service, Integer> colprix;
    @FXML
    private TableColumn<service, String> coldetail;
    @FXML
    private TableColumn<service, Integer> colidart;
 
    
      private Connection conn;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement1 = null;
    private ObservableList<service> list;
    @FXML
    private Button btnajouterser;
    @FXML
    private Button btnmodifser;
    @FXML
    private Button btnsuppser;
    @FXML
    private TextField idser;
    @FXML
    private TextField txtrech;
    @FXML
    private Button statis;
    
    
    
   
    @Override
    public void initialize(URL location, ResourceBundle resources) {
   conn = (Connection) ConnectionUtil.conDB();
        try {

            populateTableservice();
         

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    
        
       

    
    
    private void populateTableservice() throws SQLException {
        list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM service ";
        try {
            preparedStatement = conn.prepareStatement(sql);
            /*preparedStatement.setString(1, txtnom.getText());*/

            resultSet=preparedStatement.executeQuery();
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        while (resultSet.next()) {
            service rec = new service(resultSet.getInt("id_service"),resultSet.getInt("id_artiste"),resultSet.getString("nom_service"),resultSet.getInt("prix_service"), resultSet.getString("detail"));
            list.add(rec);
        }
        colid.setCellValueFactory(new PropertyValueFactory<>("id_service"));
        colidart.setCellValueFactory(new PropertyValueFactory<>("id_artiste"));
        colnom.setCellValueFactory(new PropertyValueFactory<>("nom_service"));
        colprix.setCellValueFactory(new PropertyValueFactory<>("prix_service"));
        coldetail.setCellValueFactory(new PropertyValueFactory<>("detail"));
        /*tabservice.setItems();*/
        tabservice.setItems(list);
        
}
    
    
    private void ajouterservice() throws SQLException {
        String sql = "INSERT into service (nom_service,prix_service,id_artiste,detail) " +
                "values (?,?,?,?) ";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, txtnom.getText());
            preparedStatement.setString(2, txtprix.getText());
            preparedStatement.setString(3, txtart.getText());
            preparedStatement.setString(4, txtdetail.getText());

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        populateTableservice();
    
    }
    

    

    @FXML
    private void Action_ser(ActionEvent event) {
    
      if (event.getSource() == btnajouterser ){
            try {
                
                ajouterservice();
                Alert aa = new Alert(Alert.AlertType.CONFIRMATION);
                aa.setContentText("service ajouté");
                aa.show();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                txtnom.setText("");
                txtart.setText("");
                txtprix.setText("");
                txtdetail.setText("");

            }
        }
        else if (event.getSource() == btnmodifser) {
            try {
                editservice();
                Alert aa = new Alert(Alert.AlertType.CONFIRMATION);
                aa.setContentText("service modifié");
                aa.show();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                txtnom.setText("");
                txtart.setText("");
                txtprix.setText("");
                txtdetail.setText("");
                idser.setText("");

            }
        }
        else if (event.getSource() == btnsuppser) {
            try {
                deleteservice();
                Alert aa = new Alert(Alert.AlertType.CONFIRMATION);
                aa.setContentText("service supprimer");
                aa.show();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                txtnom.setText("");
                txtart.setText("");
                txtprix.setText("");
                txtdetail.setText("");
            }
        }
    }


 
 private void editservice() throws SQLException {
//        Annonce annonce = new Annonce(1,txttitre.getText(),txtdesc.getText(),Integer.parseInt(txtprixmin.getText()),Integer.parseInt(txtprixmax.getText()), Date.from(Instant.from(txtdate.getValue().atStartOfDay(ZoneId.systemDefault()))),txtadresse.getText(),true,0,2);
       String sql="UPDATE service set  nom_service= ? , prix_service= ? , id_artiste = ?, detail = ?   where id_service= ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            

          
            preparedStatement.setString(1, txtnom.getText());
            preparedStatement.setString(2, txtprix.getText());
            preparedStatement.setString(3, txtart.getText());
            preparedStatement.setString(4, txtdetail.getText());
            preparedStatement.setString(5, idser.getText());
            /*tabservice.getSelectionModel().getSelectedItem().getId_service();*/
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        populateTableservice();
        tabservice.refresh();
    }

    private void deleteservice() throws SQLException {
//        Annonce annonce = new Annonce(1,txttitre.getText(),txtdesc.getText(),Integer.parseInt(txtprixmin.getText()),Integer.parseInt(txtprixmax.getText()), Date.from(Instant.from(txtdate.getValue().atStartOfDay(ZoneId.systemDefault()))),txtadresse.getText(),true,0,2);
        String sql="delete from service where id_service = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, idser.getText());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        populateTableservice();
    }

    @FXML
    private void showselected(MouseEvent event) {
    
        service s=tabservice.getSelectionModel().getSelectedItem();
        if(s !=null)
        {
                txtnom.setText(String.valueOf(s.getNom_service()));
                txtprix.setText(String.valueOf(s.getPrix_service()));
                txtart.setText(String.valueOf(s.getId_artiste()));
                txtdetail.setText(String.valueOf(s.getDetail()));
            idser.setText(String.valueOf(s.getId_service()));
    }


    

    
   }

    @FXML
    private void rech2(KeyEvent event) {
    FilteredList filter = new FilteredList(list, e->true);
        txtrech.textProperty().addListener((observable, oldValue, newValue )-> {


        filter.setPredicate((Predicate<? super service>) (service service)->{
            if(newValue.isEmpty() || newValue==null) {
                return true;
            }
            else if(service.getNom_service().contains(newValue)) {
                return true;
            }
            /* else if(publication.getContenu().contains(newValue)) {
                return true;
            }
            else if(publication.getDate_pub().equals(newValue)) {
                return true;
            }*/

            return false;
        });
        });

        SortedList sort = new SortedList(filter);
        sort.comparatorProperty().bind(tabservice.comparatorProperty());
        tabservice.setItems(sort);
    }

    @FXML
    private void stat(ActionEvent event)  throws IOException {
        
            Parent stat_page = FXMLLoader.load(getClass().getResource("stat.fxml"));
            
            Scene stat_page_scene=new Scene(stat_page);

          
            
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(stat_page_scene);
            app_stage.show();
                    
            
    }
    
    
    
    
    
    


}

