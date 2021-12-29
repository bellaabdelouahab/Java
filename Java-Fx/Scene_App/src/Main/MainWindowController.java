package Main;
import javafx.fxml.FXML;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainWindowController implements Initializable {
    @FXML private TextArea codeField;
    @FXML private TextArea terminalField;
    @FXML private Button savebutton;
    @FXML private Button mainfilebutton;
    @FXML private VBox filemenu;
    @FXML private VBox fileslist;
    @FXML private HBox edit_area;
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
    public void savecode(){
        try {
            FileWriter myWriter = new FileWriter("test.java");
            myWriter.write(this.codeField.getText());
            myWriter.close();
            this.savebutton.setText("Saved");
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void compile(){
        runcommand("javac test.java",this.terminalField);
    }
    public void runcode(){
        runcommand("java -cp . test 'hello'",this.terminalField);
    }
    public void visibfilemenu(){
        if(this.filemenu.visibleProperty().getValue()){
        this.filemenu.setVisible(false);
        }
        else {
            this.filemenu.setVisible(true);   
        }
    }
    public List<Button> creat_buttons(String path,int j,int k,Double maxwidth,Double maxheight){
        List<Button> list = new ArrayList<Button>();
        File folder = new File(path);
        if(folder.isDirectory()){
            Button button = new Button();
            button.setText(folder.getName());
            button.setMaxWidth(maxwidth);
            button.setMaxHeight(32);
            button.setStyle("-fx-alignment:center-left;-fx-background-color:#333333;-fx-text-fill:#a8a8a8");
            button.setPadding(new Insets(5,0,5,20*k));
            list.add(button);
            k++;
            j++;
            List<Button> butonsn = new ArrayList<Button>();
            butonsn=creat_buttons(path+'/'+folder.getName().toString() , j,k+1,maxwidth,maxheight);
            for(int l=0;l<butonsn.size();l++){
                list.add(butonsn.get(l));
                j++;
            }
            
        }
        File[] filelist = folder.listFiles();
        if(filelist!=null)
        for (int i=0 ; i<filelist.length;i++){
            if(filelist[i].isFile()){
                System.out.println(filelist[i].getName().toString());
                Button button = new Button();
                button.setText(filelist[i].getName());
                button.setMaxWidth(maxwidth);
                button.setMaxHeight(32);
                button.setStyle("-fx-alignment:center-left;-fx-background-color:#333333;-fx-text-fill:a8a8a8");
                button.setPadding(new Insets(5,0,5,20*k));
                list.add(button);
                j++;
            }
            else if(filelist[i].isDirectory()){
                Button button = new Button();
                button.setText(filelist[i].getName());
                button.setMaxWidth(maxwidth);
                button.setMaxHeight(32);
                button.setStyle("-fx-alignment:center-left;-fx-background-color:#333333;-fx-text-fill:#a8a8a8");
                button.setPadding(new Insets(5,0,5,20*k));
                list.add(button);
                j++;
                List<Button> butonsn = new ArrayList<Button>();
                butonsn=creat_buttons(path+'/'+filelist[i].getName().toString() , j,k+1,maxwidth,maxheight);
                for(int l=0;l<butonsn.size();l++){
                    list.add(butonsn.get(l));
                    j++;
                }
                
            }
    }
    return list;
}
    public void openfolder(){
        JFileChooser file = new JFileChooser();
        file.setFileSelectionMode((JFileChooser.DIRECTORIES_ONLY));
        file.showSaveDialog(null);
        System.setProperty("user.dir", file.getCurrentDirectory().toString());
        System.out.println("====>"+this.fileslist.getPrefWidth());
        final List<Button> listoffiles=creat_buttons(file.getSelectedFile().toString(), 1,0,this.fileslist.getPrefWidth(),this.fileslist.getPrefHeight());
        this.fileslist.getChildren().removeAll(this.fileslist.getChildren());
        for(int i=0;i<listoffiles.size();i++){
            listoffiles.get(i).setOnAction(event->{
                this.add_to_edit(((Button)event.getSource()).getText());
            });
            this.fileslist.getChildren().add(listoffiles.get(i));
            this.filemenu.setVisible(false);
        }
        
    }
    public void add_to_edit(String filename){
        handlefile(filename);
        Button ebutton = new Button();
        ebutton.setText(filename);
        ebutton.setStyle("-fx-alignment:center;-fx-background-color:#252525;-fx-background-radius:0em;-fx-text-fill:#a8a8a8");
        ebutton.setPrefWidth(filename.length()*10);
        ebutton.setPrefHeight(28);
        ebutton.setPadding(new Insets(0,0,0,0));
        ebutton.setOnAction(event->{
            Button e = (Button) event.getSource();
            handlefile(e.getText().toString());
        });
        HBox cont = new HBox();
        cont.setPrefWidth(filename.length()*10+28);
        cont.setPrefHeight(28);
        cont.setStyle("-fx-background-color:#2525ee");
        Button exit_button = new Button();
        exit_button.setText("X");
        exit_button.setPrefHeight(28);
        exit_button.setPrefWidth(28);
        exit_button.setPadding(new Insets(0,0,0,0));
        exit_button.setStyle("-fx-alignment:center;-fx-background-radius:0em;-fx-background-color:#252525;-fx-text-fill:#a8a8a8");
        exit_button.setOnAction(event->{
            Button e = (Button) event.getSource();
            this.edit_area.getChildren().remove(e.getParent());
            this.codeField.setText("");
        });
        cont.getChildren().add(ebutton);
        cont.getChildren().add(exit_button);
        this.edit_area.getChildren().add(cont);
    }
    public void handlefile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result= fileChooser.showOpenDialog(null);
        if (result==JFileChooser.APPROVE_OPTION){
            File selectedfile = fileChooser.getSelectedFile();
            String data="";
            try {
                Scanner scan = new Scanner(selectedfile);
                while(scan.hasNextLine()){
                data=data+"\n"+scan.nextLine();
                } 
                scan.close();
                add_to_edit(selectedfile.getName());
                this.codeField.setText(data);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public void handlefile(String filename) {
        System.out.println(System.getProperty("user.dir"));
        File selectedfile = new File(System.getProperty("user.dir")+"/"+filename);
        String data="";
        System.out.println(selectedfile);
        try {
            Scanner scan = new Scanner(selectedfile);
            while(scan.hasNextLine()){
            data=data+"\n"+scan.nextLine();
            }
            scan.close();
            this.codeField.setText(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        terminalField.setText("PS "+System.getProperty("user.dir")+" > ");
        terminalField.setEditable(false);
        filemenu.setVisible(false);
    }
    
}