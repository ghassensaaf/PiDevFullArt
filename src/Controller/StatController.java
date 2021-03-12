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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import util.ConnectionUtil;

/**
 * FXML Controller class
 *
 * @author User P
 */
public class StatController implements Initializable {

    @FXML
    private PieChart piechart;
    @FXML
    private Button statistique;

    /**
     * Initializes the controller class.
     */
    
    
    
    
      private Connection conn;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement1 = null;
    @FXML
    private Button retour;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         conn = (Connection) ConnectionUtil.conDB();
    }    

    @FXML
    private void btn(ActionEvent event) throws SQLException {
        
        
        
         /*String sql = "SELECT COUNT(prix_service) AS ZOZO FROM service WHERE (prix_service>100)";*/
         String sql = "SELECT prix_service FROM service WHERE (prix_service>100)";
        try {
            preparedStatement = conn.prepareStatement(sql);
            /*preparedStatement.setString(1, txtnom.getText());*/

            resultSet=preparedStatement.executeQuery();
            
     
         
           // metaData.getColumnName(i) 
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
            
        int i=0;
        while (resultSet.next()) {
           i=i+1;
        }
           
      /*  while (resultSet.next()){
        
            
        }*/
      /*  int resu1= resultSet.getInt("ZOZO");*/
      
      
      String res2 = "SELECT prix_service FROM service WHERE (prix_service<=100)";
        try {
            preparedStatement = conn.prepareStatement(res2);
            /*preparedStatement.setString(1, txtnom.getText());*/

            resultSet=preparedStatement.executeQuery();
            
     
         
           // metaData.getColumnName(i) 
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
            
        int j=0;
        while (resultSet.next()) {
           j=j+1;
        }
        piechart.setTitle("statistiques des prix ");
        ObservableList<Data> list = FXCollections.observableArrayList(

                new PieChart.Data("prix supperieur à 100dt",i),
        new PieChart.Data("prix inferieur à 100dt",j));
        piechart.setData(list);
        
    

}

    @FXML
    private void rt(ActionEvent event) throws IOException  {
      
        
            Parent stat_page = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            
            Scene stat_page_scene=new Scene(stat_page);

          
            
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(stat_page_scene);
            app_stage.show();
                    
            
    }
    

}

       

