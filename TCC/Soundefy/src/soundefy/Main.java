package soundefy;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import soundefy.layout.LoginController;
import soundefy.layout.SignUpController;
import soundefy.layout.SoundefyController;
import soundefy.layout.TabEditorController;
import soundefy.model.TimeSignature;

public class Main extends Application{
	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		setupPrimaryStage(primaryStage);
		this.primaryStage.setScene(new Scene(openTab()));
		this.primaryStage.show();
		//openLogin();
	}
	
	private void setupPrimaryStage(Stage primaryStage){
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Soundefy");
	}

	public AnchorPane openTab() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("layout/TabEditor.fxml"));
		AnchorPane pane = (AnchorPane)loader.load();
		TabEditorController controller = loader.getController();
		controller.setStandardTempo(126);
		controller.setStandardTimeSignature(new TimeSignature(4, 4));
		controller.setPrimaryStage(this.primaryStage);
		return pane;
	}

	public void openLogin() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("layout/Login.fxml"));
		AnchorPane pane = (AnchorPane) loader.load();
		LoginController controller = loader.getController();
		controller.setMain(this);
		
		this.primaryStage.setScene(new Scene(pane));
		this.primaryStage.show();
	}
	
	public AnchorPane openTabBrowser() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("layout/TabBrowser.fxml"));
		AnchorPane pane = (AnchorPane) loader.load();
		return pane;
	}
	
	public void openSoundefy() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("layout/Soundefy.fxml"));
		SplitPane pane = (SplitPane) loader.load();
		SoundefyController controller = loader.getController();
		controller.setMain(this);
		this.primaryStage.setScene(new Scene(pane));
		this.primaryStage.show();
	}
	
	public void openSignUp() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("layout/SignUp.fxml"));
		AnchorPane pane = (AnchorPane) loader.load();
		SignUpController controller = loader.getController();
		controller.setMain(this);
		this.primaryStage.setScene(new Scene(pane));
		this.primaryStage.show();
	}

	public static void main(String args[]){
		launch(args);
	}
}
