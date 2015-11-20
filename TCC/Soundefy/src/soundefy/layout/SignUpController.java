package soundefy.layout;

import java.io.IOException;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.swing.JOptionPane;

import soundefy.Main;
import soundefy.net.Server;
import soundefy.util.EmailValidator;

public class SignUpController {

	@FXML
	private TextField txtName;

	@FXML
	private TextField txtEmail;

	@FXML
	private PasswordField txtPassword;

	@FXML
	private PasswordField txtConfirmPassword;

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
		if (validateFields()) {
			String name = txtName.getText();
			String email = txtEmail.getText();
			String password = txtPassword.getText();
			LocalDate localDate = dpBirthDate.getValue();
			String birthDate = localDate.toString().replace('-', '/');
			
			signUpAndShowMessage(name, password, email, birthDate);
		}

	}
	
	private boolean validateFields(){
		String name = txtName.getText();
		String email = txtEmail.getText();
		String password = txtPassword.getText();
		String passwordConfirm = txtConfirmPassword.getText();
		LocalDate localDate = dpBirthDate.getValue();
		String birthDate = localDate.toString().replace('-', '/');
		boolean valid = true;
		if (name.equals("")) {
			showErrorDialog("Nome Invalido!");
			valid = false;
		} 
		if (password.equals("") || password.length() < 6) {
			showErrorDialog("Senha Invalida!");
			valid = false;
		}else if(!password.equals(passwordConfirm)){
			showErrorDialog("As senhas nao batem!");
			valid = false;
		}
		
		if (!(new EmailValidator().validate(email))) {
			showErrorDialog("E-Mail Invalido!");
			valid = false;
		} 
		if (birthDate.equals("")) {
			showErrorDialog("Data de nascimento invalida!");
			valid = false;
		}
		return valid;
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
						"Usu�rio cadastrado com sucesso!", "Cadastro",
						JOptionPane.PLAIN_MESSAGE);
				openLoginMain();
			} else {
				JOptionPane.showMessageDialog(null, "Usu�rio j� existente!",
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
