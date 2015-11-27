package soundefy.layout;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import soundefy.Main;
import soundefy.net.Server;
import soundefy.model.Tab;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class TabUploaderController {

	@FXML
	private TextField txtSongName;

	@FXML
	private TextField txtArtistName;

	@FXML
	private TextField txtSongYear;

	@FXML
	private TextField txtSongGenre;

	@FXML
	private Button btnOpenFile;

	@FXML
	private Button btnUpload;

	@FXML
	private Label lblFileName;

	private FileChooser fileChooser;

	private Stage primaryStage;

	private Tab tab;

	private byte[] file;

	private Main main;

	private Boolean fileSelected;

	@FXML
	private void initialize() {
		fileSelected = false;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	private void openSoundefyMain() {
		try {
			main.openSoundefy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void onOpenFileClick() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open File");
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Soundefy Files", "*.sdy"));
		File selectedFile = fileChooser.showOpenDialog(primaryStage);
		if (selectedFile != null) {
			try {
				this.tab = Tab.readFile(selectedFile.getAbsolutePath());
				file = tab.readFileToUpload();
				fileSelected = true;
				lblFileName.setText(selectedFile.getAbsolutePath());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	private void onUploadClick() {
		if (fileSelected) {
			Server server = null;
			String artistName = txtArtistName.getText();
			String songYear = txtSongYear.getText();
			String songName = txtSongName.getText();
			String songGenre = txtSongGenre.getText();
			if (artistName.equals("")) {
				JOptionPane.showMessageDialog(null,
						"Nome de artista invalido!", "Upload",
						JOptionPane.ERROR_MESSAGE);
			} else if (songYear.equals("")) {
				JOptionPane.showMessageDialog(null,
						"Ano de lançamento invalido!", "Upload",
						JOptionPane.ERROR_MESSAGE);
			} else if (songName.equals("")) {
				JOptionPane.showMessageDialog(null, "Nome da musica invalido!",
						"Upload", JOptionPane.ERROR_MESSAGE);
			} else if (songGenre.equals("")) {
				JOptionPane.showMessageDialog(null,
						"Genero da musica invalido!", "Upload",
						JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					server = new Server();
					if (server.upload(artistName, songYear, songName,
							songGenre, file)) {
						JOptionPane.showMessageDialog(null,
								"Upload feito com sucesso!", "Upload",
								JOptionPane.OK_OPTION);
						openSoundefyMain();
					} else {
						JOptionPane.showMessageDialog(null,
								"Ocorreu um erro ao fazer o upload!", "Upload",
								JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					server.closeConnection();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			JOptionPane.showMessageDialog(null,
					"Selectione um arquivo para fazer upload!", "Upload",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
