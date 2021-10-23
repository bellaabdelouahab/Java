import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import java.awt.FileDialog;
import java.awt.Frame;
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
        AnchorPane root = new AnchorPane();
        primaryStage.setTitle("TEXT EDITOR");
        Buttons buttons= new Buttons();
        Fields mainfiFields = new Fields();
        mainfiFields.codeField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent arg0) {
                buttons.save.setText("Save");                
            }
        });
        buttons.save.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    FileWriter myWriter = new FileWriter("test.java");
                    myWriter.write(mainfiFields.codeField.getText());
                    myWriter.close();
                    buttons.save.setText("Saved");
                } 
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        buttons.compile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                runcommand("javac test.java",mainfiFields.terminalField);
            }
        });;
        buttons.run.setOnAction(new EventHandler<ActionEvent>() {
    
            @Override
            public void handle(ActionEvent event) {
                runcommand("java -cp . test 'hello'",mainfiFields.terminalField);
            }
        });
        buttons.filedit.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(buttons.newfileButton.visibleProperty().getValue()==false){
                    buttons.newfileButton.setVisible(true);
                    buttons.openfileButton.setVisible(true);
                    buttons.openfolderButton.setVisible(true);
                }
                else{
                    buttons.newfileButton.setVisible(false);
                    buttons.openfileButton.setVisible(false);
                    buttons.openfolderButton.setVisible(false);
                }
            }
        });
        buttons.openfileButton.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileDialog miniwindow = new FileDialog((Frame)null, "Select File to open");
                miniwindow.setMode(FileDialog.LOAD);
                miniwindow.setVisible(true);
                String file=miniwindow.getFile();
                System.out.println(file);
            }
        });
        buttons.openfolderButton.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                JFileChooser file = new JFileChooser();
                file.setFileSelectionMode((JFileChooser.DIRECTORIES_ONLY));
                file.showSaveDialog(null);
                System.out.println(file.getCurrentDirectory());
                List<Button> listoffiles = new ArrayList<Button>();
                listoffiles=buttons.creat_buttons(file.getCurrentDirectory().toString(), 1);
                for(int i=0;i<listoffiles.size();i++){
                    root.getChildren().add(listoffiles.get(i));
                }
            }
        });
        Line line1 = new Line();
        line1.setStartX(200.0f); 
        line1.setStartY(0.0f);
        line1.setEndX(200.0f); 
        line1.setEndY(32);
        root.getChildren().add(buttons.save);
        root.getChildren().add(buttons.compile);
        root.getChildren().add(buttons.run);
        root.getChildren().add(buttons.filedit);
        root.getChildren().add(buttons.newfileButton);
        root.getChildren().add(buttons.openfileButton);
        root.getChildren().add(buttons.openfolderButton);
        root.getChildren().add(mainfiFields.codeField);
        root.getChildren().add(mainfiFields.terminalField);
        root.getChildren().add(line1);
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
            @Override 
            public void handle(MouseEvent e) {
                System.out.println(e.getX());
                System.out.println(e.getY());
            } 
        };
        root.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        Scene scene = new Scene(root,750 , 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}