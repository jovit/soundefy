package soundefy;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import soundefy.layout.TabEditorController;
import soundefy.model.TimeSignature;
import soundefy.net.SoundefyClient;

public class Main extends Application{
	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Soundefy");
		
		openLogin();
	}
	
	public void openTab() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("layout/TabEditor.fxml"));
		AnchorPane pane = (AnchorPane)loader.load();
		TabEditorController controller = loader.getController();
		controller.setStandardTempo(126);
		controller.setStandardTimeSignature(new TimeSignature(4, 4));
		controller.setPrimaryStage(this.primaryStage);
		this.primaryStage.setScene(new Scene(pane));
		this.primaryStage.show();
		
		new SoundefyClient();
	}
	
	public void openLogin() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("layout/Login.fxml"));
		AnchorPane pane = (AnchorPane) loader.load();
		//controller.setPrimaryStage(this.primaryStage);
		this.primaryStage.setScene(new Scene(pane));
		this.primaryStage.show();
	}
	
	public static void main(String args[]){
		launch(args);
	}
}
