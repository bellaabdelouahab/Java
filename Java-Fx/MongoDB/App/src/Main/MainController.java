package Main;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoIterable;

import org.bson.Document;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
public class MainController {
    @FXML public VBox Datalistes;
    public Connect Connection;
    public void AddDbs(VBox Datalistes){
        VBox collictionslist = new VBox();
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
                ((Button)event.getSource()).setStyle("-fx-alignment:center;-fx-background-color:#111111;-fx-background-radius:0;-fx-text-fill:#a8a8a8");
                if(vbox.getChildren().size()==0){
                    Connection.GetCollectionNames(((Button)event.getSource()).getText());
                    for (String name : Connection.collections) {
                        Button collactioButton = new Button();
                        collactioButton.setText(name);
                        collactioButton.setMaxHeight(32);
                        collactioButton.setPrefWidth(hbox.getPrefWidth());
                        collactioButton.setStyle("-fx-alignment:center;-fx-background-color:#353535;-fx-background-radius:0;-fx-text-fill:#a8a8a8");
                        collactioButton.setOnAction(event1->{
                            ShowCollectionsList(collictionslist, name);
                        });     
                        vbox.getChildren().add(collactioButton);
                    }	
                }   
                else{
                    vbox.getChildren().clear();
                    ((Button)event.getSource()).setStyle("-fx-alignment:center;-fx-background-color:#333333;-fx-background-radius:0;-fx-text-fill:#a8a8a8");
                }
                if (button_.getGraphic().getRotate()==-90){
                    button_.getGraphic().setRotate(0);
                    button_.setStyle("-fx-alignment:center;-fx-background-color:#111111;-fx-background-radius:0;-fx-text-fill:#a8a8a8");
                }
                else{
                    button_.getGraphic().setRotate(-90);
                    button_.setStyle("-fx-alignment:center;-fx-background-color:#333333;-fx-background-radius:0;-fx-text-fill:#a8a8a8");
                }
            });
            hbox.getChildren().add(button_);
            hbox.getChildren().add(button);
            Datalistes.getChildren().addAll(hbox,vbox);
            System.out.println(s);
        }
    }
    private void ShowCollectionsList(VBox collictionslist, String name) {
        Connection.getCollection(name);
        MongoCursor<Document> cursor = Connection.ReadThrowCollection();
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
            collictionslist.setPrefHeight(collictionslist.getPrefHeight());
            i++;
            if(i>5){
                break;
            }
        }
    }
}
