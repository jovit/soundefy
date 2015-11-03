package soundefy.layout;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class TabBrowserController {
	@FXML
	private ListView<String> tabList;
	@FXML
	private TextField searchText;
	
	@FXML
	private void initialize(){
		
	}
	
	@FXML
	private void onSearchClick(){
		
	}
	
	@FXML
	private void onDownloadClick(){
		
	}
	
	private void loadTabs(){
		//TODO connect to server and get all tabs list
	}
	
	
}
