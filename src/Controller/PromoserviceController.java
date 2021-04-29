/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import entite.artiste;
import entite.promo;
import entite.service;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import util.ConnectionUtil;

/**
 * FXML Controller class
 *
 * @author User P
 */
public class PromoserviceController implements Initializable {

     @FXML
    private TableColumn<promo, Integer> colidpro;
    @FXML
    private TableColumn<promo, String> colnompro;
    @FXML
    private TableColumn<promo, Integer> colpro;
    @FXML
    private TableColumn<promo, Date> dated;
    @FXML
    private TableColumn<promo, Date> datef;
    @FXML
    private Button ajouterpro;
    @FXML
    private Button supppro;
    @FXML
    private Button modifpro;
    @FXML
    private TableView<promo> tabpromo;
    
    private artiste artiste1;
    private Connection conn;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement1 = null;
    private ObservableList<promo> list6;
    @FXML
    private ComboBox<String> txtidser;
    @FXML
    private DatePicker txtdated;
    @FXML
    private DatePicker txtdatef;
    @FXML
    private TextField txtvpro;
    @FXML
    private TextField txtidart;
    @FXML
    private TextField txtidpromo;
    @FXML
    private Button rservice;
    @FXML
    private TextField txtrechpromo;
    @FXML
    private TextField mourad;
 
    /**
     * Initializes the controller class.
     */
   
     
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    conn = (Connection) ConnectionUtil.conDB();
        try {
            
            ArrayList<service> listepromo = getidser();
            for (service s : listepromo) {
                txtidser.getItems().add( s.getId_service()+" - "+s.getNom_service());
               /* nomser.getItems().add(s.getId_service());*/
            }

            populateTablepromo();
         

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }   
    
    
     /******************************afficherpromo*****************************/
    
    
    
    private void populateTablepromo() throws SQLException {
        list6 = FXCollections.observableArrayList();
        String sql = "SELECT service.nom_service, promo.idpromo, promo.id_artiste, promo.dated, promo.datef, promo.vpromo  FROM service ,promo\n" +
"WHERE service.id_service = promo.id_service  AND promo.id_artiste=? ";
        try {
            preparedStatement = conn.prepareStatement(sql);
       
            
            
                preparedStatement.setString(1, txtidart.getText());
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        while (resultSet.next()) {
            promo pro = new promo( resultSet.getInt("idpromo"), resultSet.getInt("id_artiste"), resultSet.getDate("dated"), resultSet.getDate("datef"), resultSet.getInt("vpromo"),resultSet.getString("nom_service"));
            list6.add(pro);
        }
        colidpro.setCellValueFactory(new PropertyValueFactory<>("idpromo"));
        colnompro.setCellValueFactory(new PropertyValueFactory<>("nom_service"));
        /*colidart.setCellValueFactory(new PropertyValueFactory<>("id_artiste"));*/
        dated.setCellValueFactory(new PropertyValueFactory<>("dated"));
        datef.setCellValueFactory(new PropertyValueFactory<>("datef"));
        colpro.setCellValueFactory(new PropertyValueFactory<>("vpromo"));
     
        tabpromo.setItems(list6);


    }
    
    
     /******************************ajouterpromo*****************************/
    
    
    
    private void addPromo() throws SQLException {
        String sql = "INSERT into promo ( id_artiste, id_service, dated, datef,vpromo) " +
                "values (?,?,?,?,?) ";
        try {

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, txtidart.getText());
            
            String str = txtidser.getValue().toString();
            String[] s= str.split(" - ");
            
            
            preparedStatement.setString(2, s[0]);
            
             preparedStatement.setString(3, txtdated.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
             preparedStatement.setString(4, txtdatef.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
             preparedStatement.setString(5, txtvpro.getText());
            
           

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        populateTablepromo();
    }
   

                             /******************************modifierpromotion*****************************/




 private void modifierpromo() throws SQLException {
        String sql = "UPDATE promo set    dated = ? , datef = ? , vpromo = ?  where idpromo= ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            
             
             
            preparedStatement.setString(1, txtdated.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            preparedStatement.setString(2, txtdatef.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            preparedStatement.setString(3, txtvpro.getText());
            preparedStatement.setString(4, txtidpromo.getText());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        populateTablepromo();
    }

 
       /******************************supppromotion*****************************/
   



        private void supprimerpromo() throws SQLException {
        String sql = "delete from promo where idpromo = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, txtidpromo.getText());
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
         populateTablepromo();
    }

        
        
        
        
         /******************************recuperer*****************************/

 @FXML
    private void clickp(MouseEvent event) {
        
        
        
         promo p = tabpromo.getSelectionModel().getSelectedItem();
        if (p != null) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            txtidpromo.setText(String.valueOf(p.getIdpromo()));
            
            txtidart.setText(String.valueOf(p.getId_artiste()));;
            txtdated.setValue(LocalDate.parse(format.format(p.getDated())));
            txtdatef.setValue(LocalDate.parse(format.format(p.getDatef())));
            txtvpro.setText(String.valueOf(p.getVpromo()));
        }
    }

  
      
       /******************************recupnom**********************************/
    
    public String r;
        
        public void rnom () throws SQLException {
            
             String S = "SELECT nom FROM artiste WHERE id_artiste =";
                  
              String Sql=S+ txtidart.getText();
                   try {
                      preparedStatement = conn.prepareStatement(Sql);
                     
         
                      resultSet = preparedStatement.executeQuery();
           
                           } catch (SQLException throwables) {
                             throwables.printStackTrace();
                                                          }
                   while (resultSet.next()) {
                          r=(resultSet.getString("nom")); }
                   
                   r= r + " a ajouté une promotion";
                   
}
        
       
        
        
        
        
        
        
        
        
        
        
        /******************************recupmailclient**********************************/
        
        
        public void recupmailclient() throws SQLException, MessagingException {
            List<String> mailc = new  ArrayList<String>();
             
        
        String S = " SELECT mail from client ";
        
        
       
                      preparedStatement = conn.prepareStatement(S);
                     
         
                      resultSet = preparedStatement.executeQuery();
           
                           
      
        while (resultSet.next()) {
            mailc.add(resultSet.getString("mail")); }
       /*String m=resultSet.getString("mail");*/
       for(int i=0; i<mailc.size(); i++){
           
           send ((mailc.get(i)));}
        
        }
      
       
        
        
        
        
        public boolean recupnom() throws SQLException, MessagingException {
            
            String str = txtidser.getValue().toString();
            String[] s= str.split(" - ");
         
         int t=1;
        String S = " SELECT service.nom_service from service where service.id_service = promo.id_service ";  
        
        S=S+" AND nom_service LIKE '"+ s[1]+"'";
       
                      preparedStatement = conn.prepareStatement(S);
                      
                      
                     
         
                      resultSet = preparedStatement.executeQuery();
           
                           
      
        while (resultSet.next()) {
           String nn=resultSet.getString("nom_service"); 
        
         t=0;
        
        }
       /*String m=resultSet.getString("mail");*/
       
               
              
           
           if (t==0){
               return true;
           }
         else { 
        return false;
        
        }}
            
        
       
           
     /******************************refresh*****************************/
         
          
          
          
          @FXML
    void refresh(MouseEvent event) throws SQLException {

    }
            
            
        
     
    
    
    
  
    
    
    
     /******************************bouton*****************************/
        
     

    @FXML
    private void btnpromo(ActionEvent event) throws MessagingException, SQLException {
        
        String k=txtvpro.getText(); 
          int i=Integer.parseInt(k);
          
           LocalDate d1 = txtdated.getValue();
        LocalDate d2 = txtdatef.getValue();
    
        if (event.getSource() == ajouterpro) 
        {
          
        
             if (txtidart.getText().length()==0   )  {
            
            txtidart.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
        }
            
            
       else if ( d2.isBefore(d1) ) {
             txtdated.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
             txtdatef.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
         
         
         
         
         } 
          
         else if ((txtvpro.getText().length()==0 ) ||  ( i>=100) )  {
            
            txtvpro.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
        }
          else {
        
            try {
                addPromo();
                
                rnom();
                recupmailclient();
                
              
               
            
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                txtidser.getAccessibleText();
                txtidpromo.setText("");
                txtidart.setText("");
                txtvpro.setText("");
               txtdated.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                txtdatef.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }}
        } 
        else if (event.getSource() == supppro) {
            try {
               supprimerpromo();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                 txtidser.getAccessibleText();
                txtidpromo.setText("");
                txtidart.setText("");
                txtvpro.setText("");
                txtdated.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                txtdatef.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
        } else if (event.getSource() == modifpro) {
            
              if ( d2.isBefore(d1) ) {
               txtdated.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
               txtdatef.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
         
          } 
          
         else if ((txtvpro.getText().length()==0 ) ||  ( i>=100) )  {
            
            txtvpro.setStyle("-fx-border-color: red; -fx-border-width: 3px;");
        }
            
            
         else {  try {
                modifierpromo();
                Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                al.setContentText("promo bien modifié");
                al.show();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                 txtidser.getAccessibleText();
                txtidpromo.setText("");
                txtidart.setText("");
                txtvpro.setText("");
                txtdated.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                txtdatef.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
        } 
        }}

       

    
    
    
    
    
    
    
    /******************************identifiant service*****************************/
    
       ArrayList<service> getidser() throws SQLException {
        ArrayList<service> liste = new ArrayList<>();
        String f = " select * from service" ;
        
     
        resultSet = conn.createStatement().executeQuery(f);
        while (resultSet.next()) {
            service s = new service(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5));
            liste.add(s);
        }
        return liste;
    }

       
       
       
       
     
       
       
       
       /*********************************mailing*********************************/
       
       
   
       
       
       public  void send(String recepient ) throws MessagingException{
    //Propriétés
    Properties p = new Properties();
    p.put("mail.smtp.auth", "true");
    p.put("mail.smtp.starttls.enable", "true");
   /* p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");*/
    p.put("mail.smtp.host", "smtp.gmail.com");
    p.put("mail.smtp.port", "587");
    //Session
   
    
    String myAccountEmail = "fullart.entreprise@gmail.com";
    String password = "Fullart1999";
    
    Session session;
        session = Session.getInstance(p, new  javax.mail.Authenticator() {
        
        
            
           
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }

            

        });
      Message message = prepareMessage(session,myAccountEmail,recepient,r);
      Transport.send(message);
    }
     
    
       
       
       
       
    
      private static Message prepareMessage(Session session, String myAccountEmail,String recepient,String r){
           try {
         
         Message message = new MimeMessage(session);
         message.setFrom( new InternetAddress(myAccountEmail));
         message.setRecipient(Message.RecipientType.TO,new InternetAddress(recepient));
         message.setSubject("new email \n");
         
         message.setText (r);
         return message;
           }catch (MessagingException e) {
      e.printStackTrace();
    }
           return null;
     }


      
      
      
      
      /********************************* retour *********************************/
      
      
    @FXML
    private void retpro(ActionEvent event) throws IOException {
        
        
            Parent stat_page = FXMLLoader.load(getClass().getResource("artiste.fxml"));
            
            Scene stat_page_scene=new Scene(stat_page);

          
            
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(stat_page_scene);
            app_stage.show();
                    
                    
            
    
    }

    @FXML
    private void rechpro(KeyEvent event) {
        
        
        
        FilteredList filter = new FilteredList(list6, e->true);
        txtrechpromo.textProperty().addListener((observable, oldValue, newValue )-> {


        filter.setPredicate((Predicate<? super promo>) (promo promo)->{
            if(newValue.isEmpty() || newValue==null) {
                return true;
            }
            else if(promo.getNom_service().contains(newValue)) {
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
        sort.comparatorProperty().bind(tabpromo.comparatorProperty());
        tabpromo.setItems(sort);
    }
    

      
      
      
      
    }