import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
public class Buttons{
        Button save;
        Button compile;
        Button run;
        Button filedit;
        Button newfileButton;
        Button openfileButton;
        Button openfolderButton;
        Button button;
        Buttons(){
            save = new Button();
            save.setText("Save");
            save.setMinWidth(100);
            save.setLayoutX(450);
            save.setMinHeight(32);

            compile = new Button();
            compile.setText("Compile");
            compile.setLayoutX(550);
            compile.setMinWidth(100);
            compile.setMinHeight(32);

            run = new Button();
            run.setText("Run");
            run.setLayoutX(650);
            run.setMinWidth(100);
            run.setMinHeight(32);

            filedit = new Button();
            filedit.setText("File");
            filedit.setMinWidth(100);
            filedit.setMinHeight(32);
            filedit.setStyle("-fx-alignment:LEFT;-fx-border-color:#383838;-fx-border-width:2px;-fx-border-radius:0;;-fx-background-color:#383838;-fx-text-fill:black");
            filedit.setPadding(new Insets(0,0,0,80));

            newfileButton = new Button();
            newfileButton.setText("new File");
            newfileButton.setMinWidth(100);
            newfileButton.setMinHeight(32);
            newfileButton.setLayoutY(32);
            newfileButton.setVisible(false);
            

            openfileButton = new Button();
            openfileButton.setText("Open File");
            openfileButton.setMinWidth(100);
            openfileButton.setMinHeight(32);
            openfileButton.setLayoutY(64);
            openfileButton.setVisible(false);

            openfolderButton = new Button();
            openfolderButton.setText("Open Folder");
            openfolderButton.setMinWidth(100);
            openfolderButton.setMinHeight(32);
            openfolderButton.setLayoutY(96);
            openfolderButton.setVisible(false);
        
        };
        public List<Button> creat_buttons(String path,int j,int k){
            List<Button> list = new ArrayList<Button>();
            File folder = new File(path);
            File[] filelist = folder.listFiles();
            for (int i=0 ; i<filelist.length;i++){
                if(filelist[i].isFile()){
                    System.out.println(filelist[i].getName().toString());
                    button = new Button();
                    button.setText(filelist[i].getName());
                    button.setMinWidth(200);
                    button.setMinHeight(32);
                    button.setLayoutY(j*32);
                    button.setVisible(true);
                    button.setStyle("-fx-alignment:LEFT;-fx-background-color:transparent;-fx-background-radious:0;");
                    button.setPadding(new Insets(0,0,0,20*k));
                    list.add(button);
                    j++;
                }
                else if(filelist[i].isDirectory()){
                    button = new Button();
                    button.setText(filelist[i].getName());
                    button.setMinWidth(200);
                    button.setMinHeight(32);
                    button.setLayoutY(j*32);
                    button.setStyle("-fx-alignment:center-left;-fx-background-color:transparent;-fx-background-radious:0;");
                    button.setPadding(new Insets(0,0,0,20*k));
                    list.add(button);
                    j++;
                    List<Button> butonsn = new ArrayList<Button>();
                    butonsn=creat_buttons(path+'/'+filelist[i].getName().toString() , j,k+1);
                    for(int l=0;l<butonsn.size();l++){
                        list.add(butonsn.get(l));
                        j++;
                    }
                    
                }
            
        }
        return list;
    }
}