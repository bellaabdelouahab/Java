package Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader load = new FXMLLoader(getClass().getResource("../MainWindow1.fxml"));
        Parent root = load.load();
        MainController controller = load.getController();
        controller.Connection= new Connect();
        controller.Connection.StartConnection();
        controller.AddDbs(controller.Datalistes);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
