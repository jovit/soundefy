package soundefy.layout;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import soundefy.Main;

public class LoginController {

	@FXML
	private Button btnLogin;
	
	@FXML
	private Label lblInscrever;
	
	@FXML
	private Label lblMudarSenha;
	
	private Main main;

	public void setMain(Main main){
		this.main = main;
	}
	
	@FXML
	private void initialize() {
		configureOnLoginClick();
		configureOnSubscribeClick();
		configureOnChangePasswordClick();
	}
	
	private void configureOnLoginClick(){
		btnLogin.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				//Server server = new Server();
				openSoundefyMain();
			}
		});
	}
	
	private void openSoundefyMain(){
		try{
			main.openSoundefy();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void configureOnSubscribeClick(){
		lblInscrever.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

			}
		});
	}
	
	private void configureOnChangePasswordClick(){
		lblMudarSenha.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
			}
		});
	}
}
