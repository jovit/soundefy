package soundefy.layout;

import java.io.IOException;

import soundefy.Main;
import soundefy.net.Server;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class SignUpController {
	
	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtEmail;
	
	@FXML
	private TextField txtPassword; 
	
	@FXML
	private TextField txtConfirmPassword;
	
	@FXML
	private DatePicker dpBirthDate;
	
	@FXML
	private Button btnSignUp;
	
	private Main main;

	public void setMain(Main main) {
		this.main = main;
	}
	
	private void openLoginMain(){
		try {
			main.openLogin();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void configureOnLoginClick() {
		btnSignUp.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Server server = new Server();
				String email = txtEmail.getText();
				String pwd = txtPassword.getText();
				try {
					if (server.signIn(email, pwd)) {
						openLoginMain();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	@FXML
	private void initialize() {
	}
}
