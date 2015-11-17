package soundefy.layout;

import java.io.IOException;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import javax.swing.JOptionPane;

import soundefy.Main;
import soundefy.net.Server;

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
		String name = txtName.getText();
		String email = txtEmail.getText();
		String password = txtPassword.getText();
		LocalDate localDate = dpBirthDate.getValue();
		String birthDate = localDate.toString().replace('-', '/');

		if (name == "") {
			showErrorDialog("Nome Inválido!");
		} else if (password == "" || password.length() < 6) {
			showErrorDialog("Senha inválida!");
		} else if (email == "") {
			showErrorDialog("E-Mail inválido!");
		} else if (birthDate == "") {
			showErrorDialog("Data de nascimento inválida!");
		} else {
			signUpAndShowMessage(name, password, email, birthDate);
		}

	}

	private void showErrorDialog(String message) {
		JOptionPane.showMessageDialog(null, message, "Cadastro",
				JOptionPane.ERROR_MESSAGE);
	}

	private void signUpAndShowMessage(String name, String password,
			String email, String birthDate) {
		Server server = null;
		try {
			server = new Server();
			if (server.signUp(name, password, email, birthDate)) {
				JOptionPane.showMessageDialog(null,
						"Usuário cadastrado com sucesso!", "Cadastro",
						JOptionPane.PLAIN_MESSAGE);
				openLoginMain();
			} else {
				JOptionPane.showMessageDialog(null, "Usuário já existente!",
						"Cadastro", JOptionPane.ERROR_MESSAGE);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			server.closeConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void initialize() {
	}
}
