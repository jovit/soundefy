package soundefy.layout;

import java.io.IOException;

import soundefy.net.Server;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import soundefy.Main;

public class LoginController {

	@FXML
	private Button btnLogin;

	@FXML
	private Label lblSignUp;

	@FXML
	private Label lblChangePassword;

	@FXML
	private TextField txtPassword;

	@FXML
	private TextField txtEmail;

	private Main main;

	public void setMain(Main main) {
		this.main = main;
	}

	@FXML
	private void initialize() {
		configureOnLoginClick();
		configureOnSubscribeClick();
		configureOnChangePasswordClick();
	}

	private void configureOnLoginClick() {
		btnLogin.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Server server = new Server();
				String email = txtEmail.getText();
				String pwd = txtPassword.getText();
				try {
					if (server.signIn(email, pwd)) {
						openSoundefyMain();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void openSoundefyMain() {
		try {
			main.openSoundefy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void configureOnSubscribeClick() {
		lblSignUp.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					main.openSignUp();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void configureOnChangePasswordClick() {
		lblChangePassword.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

			}
		});
	}
}
