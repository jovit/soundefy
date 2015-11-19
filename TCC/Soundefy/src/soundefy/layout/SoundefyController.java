package soundefy.layout;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import soundefy.Main;

public class SoundefyController {
	@FXML
	AnchorPane contentPane;
	@FXML
	Label browseTabsLabel;
	@FXML
	Label friendsLabel;
	@FXML
	Label uploadTabsLabel;
	@FXML
	Label playSongLabel;
	@FXML
	Label writeTabLabel;
	
	private AnchorPane tabEditor, tabBrowser;
	
	private Main main;
	
	public void setMain(Main main){
		this.main = main;
		loadScreens();
	}
	
	private void removeLabelStyles(){
		browseTabsLabel.setStyle("");
		friendsLabel.setStyle("");
		uploadTabsLabel.setStyle("");
		playSongLabel.setStyle("");
		writeTabLabel.setStyle("");
	}
	
	private void removeAllScreens(){
		for(int i=0; i<contentPane.getChildren().size(); i++)
			contentPane.getChildren().remove(i);
	}
	
	private void loadScreens(){
		try {
			tabEditor = main.openTab();
			tabBrowser = main.openTabBrowser();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void initialize(){
		
	}
	
	private void setContentPane(Pane pane){
		removeAllScreens();
		contentPane.getChildren().add(pane);
	}
	
	@FXML
	private void onBrowseTabsClick(){
		try{
			setContentPane(tabBrowser);
			removeLabelStyles();
			browseTabsLabel.setStyle("-fx-font-weight: bold;");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@FXML
	private void onFriendsClick(){
		removeLabelStyles();
		friendsLabel.setStyle("-fx-font-weight: bold;");
	}
	
	@FXML
	private void onUploadTabsClick(){
		removeLabelStyles();
		uploadTabsLabel.setStyle("-fx-font-weight: bold;");
	}
	@FXML
	private void onPlaySongsClick(){
		removeLabelStyles();
		playSongLabel.setStyle("-fx-font-weight: bold;");
	}
	
	@FXML
	private void onWriteTabClick(){
		try{
			setContentPane(tabEditor);
			removeLabelStyles();
			writeTabLabel.setStyle("-fx-font-weight: bold;");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
}
