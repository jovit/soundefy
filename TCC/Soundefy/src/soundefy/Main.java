package soundefy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import soundefy.layout.TabEditorControler;
import soundefy.model.TimeSignature;

public class Main extends Application{
	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Soundefy");
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("layout/TabEditor.fxml"));
		AnchorPane pane = (AnchorPane)loader.load();
		TabEditorControler controller = loader.getController();
		controller.setStandardTempo(126);
		controller.setStandardTimeSigniature(new TimeSignature(4, 4));
		this.primaryStage.setScene(new Scene(pane,500,500));
		this.primaryStage.show();
	}
	
	
	public static void main(String args[]){
		launch(args);
	}
}
