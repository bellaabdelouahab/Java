package Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoIterable;

import org.bson.Document;
import org.bson.types.ObjectId;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
public class MainWindowController implements Initializable {
    @FXML private Button Databases;
    @FXML private VBox Datalistes;
    @FXML private VBox collictionslist;
    @FXML private Pane pane_insert;
    @FXML private GridPane addinformations;
    @FXML private VBox listofinfos;
    @FXML private HBox menu_db;
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static List<TextArea> datalist = new ArrayList<TextArea>();
    private static MongoCollection<Document> collectionslisted;
    public void runcommand(VBox Datalistes,VBox collictionslist){
        
        try{
            //connect to mongo db server 
            mongoClient = new MongoClient("localhost",27017);
            //create a database name
            List<String> dbs =mongoClient.getDatabaseNames();
            for (String s:dbs){
                VBox vbox = new VBox();
                HBox hbox = new HBox();
                vbox.setId("vbox");
                hbox.setPrefWidth(Datalistes.getPrefWidth());
                hbox.setMaxHeight(32);
                Button button_ = new Button();
                button_.setText("→");
                button_.setMaxHeight(32);
                button_.setPrefWidth(32);
                button_.setStyle("-fx-alignment:center;-fx-background-color:#333333;-fx-background-radius:0;-fx-text-fill:#a8a8a8");
                Button button = new Button();
                button.setText(s);
                button.setMaxHeight(32);
                button.setPrefWidth(178);
                button.setStyle("-fx-alignment:center;-fx-background-color:#333333;-fx-background-radius:0;-fx-text-fill:#a8a8a8");
                button.setOnAction(event->{
                    ((Button)event.getSource()).setStyle("-fx-alignment:center;-fx-background-color:#111111;-fx-background-radius:0;-fx-text-fill:#a8a8a8");
                    if(vbox.getChildren().size()==0){
                        database = mongoClient.getDatabase(((Button)event.getSource()).getText());
                        MongoIterable<String> collections = database.listCollectionNames();
                        for (String name : collections) {
                            Button collactioButton = new Button();
                            collactioButton.setText(name);
                            collactioButton.setMaxHeight(32);
                            collactioButton.setPrefWidth(hbox.getPrefWidth());
                            collactioButton.setStyle("-fx-alignment:center;-fx-background-color:#353535;-fx-background-radius:0;-fx-text-fill:#a8a8a8");
                            collactioButton.setOnAction(event1->{
                                collectionslisted= database.getCollection(name);
                                this.menu_db.setDisable(false);
                                MongoCursor<Document> cursor = collectionslisted.find().iterator();
                                collictionslist.getChildren().clear();
                                int i=0;
                                while (cursor.hasNext()) {
                                    Label djson = new Label();
                                    String j_sondata = cursor.next().toJson();
                                    j_sondata = j_sondata.replace("{", "");
                                    j_sondata = j_sondata.replace("}", "");
                                    j_sondata = j_sondata.replace(",", "\n");
                                    j_sondata = j_sondata.replace("\"", "");
                                    j_sondata = j_sondata.replace("$oid:", "");
                                    //j_sondata = j_sondata.substring(1);
                                    
                                    djson.setText(j_sondata);
                                    djson.setStyle("-fx-background-color:#15151555");
                                    djson.setPrefWidth(collictionslist.getPrefWidth());
                                    Line line = new Line();
                                    line.setEndX(collictionslist.getPrefWidth());
                                    line.setStyle("-fx-strock:#121212");
                                    collictionslist.getChildren().add(line);
                                    collictionslist.getChildren().add(djson);
                                    i++;
                                    /*if(i>5){
                                        break;
                                    }*/
                                }
                            });     
                            vbox.getChildren().add(collactioButton);
                        }	
                    }   
                    else{
                        vbox.getChildren().clear();
                        ((Button)event.getSource()).setStyle("-fx-alignment:center;-fx-background-color:#333333;-fx-background-radius:0;-fx-text-fill:#a8a8a8");
                    }
                    if (button_.getText()=="→"){
                        button_.setText("↓");
                        button_.setStyle("-fx-alignment:center;-fx-background-color:#111111;-fx-background-radius:0;-fx-text-fill:#a8a8a8");
                    }
                    else{
                        button_.setText("→");
                        button_.setStyle("-fx-alignment:center;-fx-background-color:#333333;-fx-background-radius:0;-fx-text-fill:#a8a8a8");
                    }
                });
                hbox.getChildren().add(button_);
                hbox.getChildren().add(button);
                Datalistes.getChildren().addAll(hbox,vbox);
                System.out.println(s);
            }

        }catch(Exception ex){
            ex.printStackTrace(); 
            System.exit(0);           
        }
    }
    public void show_insert(){
        this.pane_insert.setVisible(true);
    }
    public void hide_insert(){
        this.pane_insert.setVisible(false);
    }
    public void add_column(){
        HBox ex= new HBox();
        ex.prefWidth(447);
        ex.prefHeight(36);
        TextArea d_1 = new TextArea();
        d_1.setPrefWidth(173);
        d_1.setPrefHeight(36);
        d_1.setStyle("-fx-background-inset:0;-fx-background-radius:0");
        TextArea d_2 = new TextArea();
        d_2.setPrefWidth(250);
        d_2.setPrefHeight(36);
        d_2.setStyle("-fx-background-inset:0;-fx-background-radius:0");
        Button d_3 = new Button();
        d_3.setPrefWidth(43);
        d_3.setPrefHeight(36);
        d_3.setText("+");
        d_3.setStyle("-fx-background-color:#aaaaaa;-fx-background-radius:0");
        d_3.setOnAction(event->{
            add_column();
        });
        d_3.setId("A");
        for(Node i: this.listofinfos.getChildren()){
            for(Node j: ((HBox)i).getChildren()){
                if (j.lookup("#A") != null){
                    ((Button)j.lookup("#A")).setVisible(false);
                }
            }
        }
        ex.getChildren().addAll(d_1,d_2,d_3);
        datalist.add(d_1);
        datalist.add(d_2);
        this.listofinfos.getChildren().add(ex);
    }
    public void insert(){
        Document labledata=new Document();
        labledata.append("_id", new ObjectId());
        System.out.println(datalist.size());
        for(int i=0;i<datalist.size();i+=2){
            labledata.append(datalist.get(i).getText(), datalist.get(i+1).getText());
        }
        collectionslisted.insertOne(labledata);
        hide_insert();
        
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        runcommand(this.Datalistes,this.collictionslist);
        add_column();
    }

}   