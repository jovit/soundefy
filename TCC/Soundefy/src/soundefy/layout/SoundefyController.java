package soundefy.layout;

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
	
	private Main main;
	
	public void setMain(Main main){
		this.main = main;
	}
	
	private void removeLabelStyles(){
		browseTabsLabel.setStyle("");
		friendsLabel.setStyle("");
		uploadTabsLabel.setStyle("");
		playSongLabel.setStyle("");
		writeTabLabel.setStyle("");
	}
	
	@FXML
	private void initialize(){
		
	}
	
	private void setContentPane(Pane pane){
		contentPane.getChildren().removeAll();
		contentPane.getChildren().add(pane);
	}
	
	@FXML
	private void onBrowseTabsClick(){
		try{
			setContentPane(main.openTabBrowser());
			removeLabelStyles();
			browseTabsLabel.setStyle("-fx-font-weight: bold;");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@FXML
	private void onFriendsClick(){
		setContentPane(null);
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
			setContentPane(main.openTab());
			removeLabelStyles();
			writeTabLabel.setStyle("-fx-font-weight: bold;");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
}
