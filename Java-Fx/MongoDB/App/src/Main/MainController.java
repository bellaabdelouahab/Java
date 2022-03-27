package Main;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

import org.bson.Document;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
public class MainController {
    @FXML private AnchorPane MainPane;
    @FXML public VBox Datalistes;
    @FXML private Pane DataBaseSection;
    @FXML private Pane ContentPane;
    private VBox DocumentVbox;
    public Connect Connection;
    public void AddDbs(VBox Datalistes){
        MongoIterable<String> dbs = Connection.GetDatabasesName();
        for (String s:dbs){
            VBox vbox = new VBox();
            HBox hbox = new HBox();
            vbox.setId("vbox");
            hbox.setPrefWidth(250);
            hbox.setMaxHeight(32);
            Button button_ = new Button();
            Image img = new Image("Arrow.png");
            ImageView view = new ImageView(img);
            view.setFitHeight(10);
            view.setFitWidth(10);
            view.setPreserveRatio(true);
            view.setRotate(-90);
            button_.setGraphic(view);
            button_.setMaxHeight(32);
            button_.setPrefWidth(32);
            button_.setStyle("-fx-alignment:center;-fx-background-color:#3d4f58;-fx-background-radius:0;-fx-text-fill:#a8a8a8");
            Button button = new Button();
            button.setText(s);
            button.setMaxHeight(32);
            button.setPrefWidth(218);
            button.setStyle("-fx-alignment:center;-fx-background-color:#3d4f58;-fx-background-radius:0;-fx-text-fill:#a8a8a8");
            button.setOnAction(event->{
                ShowCollectionsList( vbox, hbox, button_, event);
            });
            hbox.getChildren().add(button_);
            hbox.getChildren().add(button);
            Datalistes.getChildren().addAll(hbox,vbox);
            System.out.println(s);
        }
    }
    private void ShowCollectionsList( VBox vbox, HBox hbox, Button button_, ActionEvent event) {
        ((Button)event.getSource()).setStyle("-fx-alignment:center;-fx-background-color:#333333;-fx-background-radius:0;-fx-text-fill:#a8a8a8");
        if(vbox.getChildren().size()==0){
            Connection.GetCollectionNames(((Button)event.getSource()).getText());
            for (String name : Connection.collections) {
                Button collactioButton = new Button();
                collactioButton.setText(name);
                collactioButton.setMaxHeight(32);
                collactioButton.setPrefWidth(hbox.getPrefWidth());
                collactioButton.setPadding(new Insets(5,5,5,0));
                collactioButton.setStyle("-fx-alignment:center;-fx-background-color: #3d4f58;-fx-background-radius:0;-fx-text-fill:#a8a8a8");
                collactioButton.setOnAction(event1->{
                    ShowCollection(name);
                });   
                collactioButton.setOnMouseEntered(e->{
                    collactioButton.setStyle(collactioButton.getStyle()+";-fx-background-color:#4e5f69");
                });  
                collactioButton.setOnMouseExited(e->{
                    collactioButton.setStyle(collactioButton.getStyle()+";-fx-background-color:#3d4f58");
                });  
                vbox.getChildren().add(collactioButton);
            }	
        }   
        else{
            vbox.getChildren().clear();
            ((Button)event.getSource()).setStyle("-fx-alignment:center;-fx-background-color:#3d4f58;-fx-background-radius:0;-fx-text-fill:#a8a8a8");
        }
        if (button_.getGraphic().getRotate()==-90){
            button_.getGraphic().setRotate(0);
            button_.setStyle("-fx-alignment:center;-fx-background-color:#333333;-fx-background-radius:0;-fx-text-fill:#a8a8a8");
        }
        else{
            button_.getGraphic().setRotate(-90);
            button_.setStyle("-fx-alignment:center;-fx-background-color:#3d4f58;-fx-background-radius:0;-fx-text-fill:#a8a8a8");
        }
    }
    private void ShowCollection( String name) {
        if(DocumentVbox==null)
        SetCollectionENV();
        Connection.getCollection(name);
        MongoCursor<Document> cursor = Connection.ReadThrowCollection();
        DocumentVbox.getChildren().clear();
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
            djson.setPrefWidth(DocumentVbox.getPrefWidth());
            Line line = new Line();
            line.setEndX(DocumentVbox.getPrefWidth());
            line.setStyle("-fx-strock:#121212");
            DocumentVbox.getChildren().add(line);
            DocumentVbox.getChildren().add(djson);
            DocumentVbox.setPrefHeight(DocumentVbox.getPrefHeight());
            i++;
            if(i>5){
                break;
            }
        }
    }
    public void CreateDataBase(){
        Pane CreateDataBase = new Pane();
        CreateDataBase.setPrefSize(1024, 600);
        CreateDataBase.setStyle("-fx-background-color:#45454533");
        TextField DBname = new TextField();
        DBname.setPrefSize(250,30);
        DBname.setLayoutX(400);
        DBname.setLayoutY(250);
        DBname.setPromptText("Enter Data Base Name");
        DBname.setStyle("-fx-background-color:#15151555;-fx-text-fill:#eee");
        DBname.setOnKeyPressed(event ->{
            if(event.getCode()==KeyCode.ENTER){
                SaveDatabse(DBname,CreateDataBase);
            }
        });
        Button SaveDataBase = new Button();
        SaveDataBase.setStyle("-fx-background-color:#f57b42");
        SaveDataBase.setText("Save Data Base");
        SaveDataBase.setPrefSize(100,20);
        SaveDataBase.setLayoutX(550);
        SaveDataBase.setLayoutY(280);
        SaveDataBase.setOnAction(e->{
            SaveDatabse(DBname,CreateDataBase);
        });
        Button Cancel = new Button();
        Cancel.setStyle("-fx-background-color:#f57b42");
        Cancel.setText("Cancel");
        Cancel.setPrefSize(100,20);
        Cancel.setLayoutX(400);
        Cancel.setLayoutY(280);
        Cancel.setOnAction(e->{
            MainPane.getChildren().remove(CreateDataBase);
        });
        CreateDataBase.getChildren().addAll(DBname,SaveDataBase,Cancel);
        MainPane.getChildren().add(CreateDataBase);
    }
    private void SaveDatabse(TextField DBname, Pane CreateDataBase) {
        String DataBaseName = DBname.getText();
        MongoDatabase database = Connection.mongoClient.getDatabase(DataBaseName);
        database.createCollection(DataBaseName);
        DataBaseSection.getChildren().remove(DBname);
        Datalistes.getChildren().clear();
        MainPane.getChildren().remove(CreateDataBase);
        AddDbs(Datalistes);
    }
    public void SetCollectionENV(){
        Pane FindPane = new Pane();
        FindPane.setPrefSize(653,38);
        FindPane.setLayoutX(76);
        FindPane.setLayoutY(118);
        FindPane.setStyle("-fx-background-radius:50;-fx-border-radius:50;-fx-border-color:#434343");
        Button FindButton = new Button("Find");
        FindButton.setPrefSize(98,29);
        FindButton.setLayoutX(552);
        FindButton.setLayoutY(4);
        FindButton.setStyle("-fx-background-color:#121212;-fx-background-radius:50;-fx-background-insets: 0 0 -1 0, 0, 1, 2;");
        FindButton.setOnAction(e->{
            System.out.println("sdsd");
        });
        TextField findField = new TextField();
        findField.setPrefSize(500,30);
        findField.setLayoutX(76);
        findField.setLayoutY(4);
        findField.setStyle("-fx-background-radius:50;-fx-border-radius:50;-fx-border-color:#434343");
        Label filterLabel = new Label("   Filter");
        filterLabel.setPrefSize(90,30);
        filterLabel.setLayoutX(5);
        filterLabel.setLayoutY(4);
        filterLabel.setStyle("-fx-background-radius:50;-fx-border-radius:50;-fx-border-color:#434343");
        FindPane.getChildren().addAll(filterLabel,findField,FindButton);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(776,365);
        scrollPane.setMinSize(776,365);
        scrollPane.setLayoutX(0);
        scrollPane.setLayoutY(166);
        scrollPane.setStyle("-fx-background-color:#15151555");
        scrollPane.setHbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.NEVER);
        DocumentVbox = new VBox(scrollPane);
        DocumentVbox.setPrefWidth(776);
        DocumentVbox.setMinSize(776,365);
        scrollPane.setContent(DocumentVbox);
        ContentPane.getChildren().addAll(FindPane,scrollPane);
    }
}
