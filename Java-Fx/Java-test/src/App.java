import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
//import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.shape.Line;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
public class App extends Application {
    public static void runcommand(String command_ , TextArea terminalField){
        try {
            terminalField.appendText(command_+'\n');
            Process process = Runtime.getRuntime().exec(command_);
            BufferedReader readeroutput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader readeroutputerror = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line = "",data_="";
            while ((line = readeroutput.readLine()) != null) 
            {
                data_=data_+line+'\n';
            }
            while ((line = readeroutputerror.readLine()) != null) 
            {
                data_=data_+line+'\n';
            }
            terminalField.appendText(data_+"PS "+System.getProperty("user.dir")+"> ");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root,750 , 500);
        root.setMaxSize(750, 500);
        primaryStage.setTitle("TEXT EDITOR");
        Button btn = new Button();
        Line line1 = new Line();
        line1.setStartX(375.0f); 
        line1.setStartY(32.0f);
        line1.setEndX(375.0f); 
        line1.setEndY(scene.getHeight()-32);
        Line line2 = new Line();
        line2.setStartX(0.0f); 
        line2.setStartY(32.0f);
        line2.setEndX(scene.getWidth()); 
        line2.setEndY(32.0f);
        TextArea codeField = new TextArea();
        codeField.setTranslateY(32);
        codeField.setPrefSize(375,436);
        codeField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent arg0) {
                btn.setText("Save");                
            }
        });
        TextArea terminalField = new TextArea();
        terminalField.setTranslateY(32);
        terminalField.setTranslateX(375);
        terminalField.setEditable(false);
        terminalField.setPrefSize(375,436);
        terminalField.setWrapText(true);
        terminalField.setText("PS "+System.getProperty("user.dir")+"> ");
        btn.setText("Save");
        btn.setMinWidth(250);
        btn.setMinHeight(32);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    FileWriter myWriter = new FileWriter("test.java");
                    myWriter.write(codeField.getText());
                    myWriter.close();
                    btn.setText("Saved");
                } 
                catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
        });
        Button btn1 = new Button();
        btn1.setText("Compile");
        btn1.setLayoutX(250);
        btn1.setMinWidth(250);
        btn1.setMinHeight(32);
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                runcommand("javac test.java",terminalField);
            }
        });
        Button btn2 = new Button();
        btn2.setText("Run");
        btn2.setLayoutX(500);
        btn2.setMinWidth(250);
        btn2.setMinHeight(32);
        btn2.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                runcommand("java -cp . test 'hello'",terminalField);
            }
        });
        root.getChildren().add(btn);
        root.getChildren().add(btn1);
        root.getChildren().add(btn2);
        root.getChildren().add(line1);
        root.getChildren().add(line2);
        root.getChildren().add(codeField);
        root.getChildren().add(terminalField);
        /*EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
            @Override 
            public void handle(MouseEvent e) { 
                //System.out.println((int)(Math.random()*100));
                System.out.println(e.getX());
                System.out.println(e.getY());
            } 
        };
         root.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);   */
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}