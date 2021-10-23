import javafx.scene.control.TextArea;

public class Fields {
    TextArea codeField;
    TextArea terminalField;
    Fields(){
        codeField = new TextArea();
        codeField.setTranslateY(32);
        codeField.setTranslateX(200);//scene.getWidth()-200
        codeField.setPrefSize(550,370);
        terminalField = new TextArea();
        terminalField.setTranslateY(370);
        terminalField.setTranslateX(200);
        terminalField.setEditable(false);//scene.getWidth()-200
        terminalField.setPrefSize(550,130);
        terminalField.setText("PS "+System.getProperty("user.dir")+"> ");
        terminalField.setStyle("-fx-background-color:black;-fx-text-fill:#383838;");
    }
    
}
