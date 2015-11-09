package soundefy.layout;

import java.io.IOException;
import java.time.LocalDate;

import javax.swing.JOptionPane;

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

	private void openLoginMain() {
		try {
			main.openLogin();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void configureOnSignUpClick() {
		btnSignUp.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Server server = new Server();
				String name = txtName.getText();
				String email = txtEmail.getText();
				String pwd = txtPassword.getText();
				LocalDate localDate = dpBirthDate.getValue();
				String birthDate = localDate.toString().replace('-', '/');
				
				if (name == "") {
					JOptionPane.showConfirmDialog(null, "Cadastro",
							"Nome Inválido!", JOptionPane.ERROR_MESSAGE);
				} else if (pwd == "" || pwd.length() < 6) {
					JOptionPane.showConfirmDialog(null, "Cadastro",
							"Senha inválida!", JOptionPane.ERROR_MESSAGE);
				} else if (email == "") {
					JOptionPane.showConfirmDialog(null, "Cadastro",
							"E-Mail inválido!", JOptionPane.ERROR_MESSAGE);
				} else if (birthDate == "") {
					JOptionPane.showConfirmDialog(null, "Cadastro",
							"Data de nascimento inválida!",
							JOptionPane.ERROR_MESSAGE);
				}
				
				try {
					if (server.signUp(name, pwd, email, birthDate)) {
						JOptionPane.showConfirmDialog(null, "Cadastro",
								"Usuário cadastrado com sucesso!",
								JOptionPane.OK_OPTION);
						openLoginMain();
					} else {
						JOptionPane.showConfirmDialog(null, "Cadastro",
								"Usuário já existente!",
								JOptionPane.ERROR_MESSAGE);
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
