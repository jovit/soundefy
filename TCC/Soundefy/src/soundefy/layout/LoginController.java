package soundefy.layout;

import soundefy.util.Server;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class LoginController {

	@FXML
	private Button btnLogin;
	
	@FXML
	private Label lblInscrever;
	
	@FXML
	private Label lblMudarSenha;

	@FXML
	private void initialize() {
		btnLogin.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Server server = new Server();
			}
		});
		
		lblInscrever.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

			}
		});
	
		lblMudarSenha.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
			}
		});
	}
}
