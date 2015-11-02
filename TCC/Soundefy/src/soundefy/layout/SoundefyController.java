package soundefy.layout;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import soundefy.Main;

public class SoundefyController {
	@FXML
	AnchorPane contentPane;
	
	private Main main;
	
	public void setMain(Main main){
		this.main = main;
	}
	
	@FXML
	private void initialize(){
		
	}
	
	@FXML
	private void onBrowseTabsClick(){
		try{
			contentPane.getChildren().removeAll();
			contentPane.getChildren().add(main.openTabBrowser());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
