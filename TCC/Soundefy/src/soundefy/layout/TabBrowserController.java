package soundefy.layout;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import soundefy.net.Server;

public class TabBrowserController {
	@FXML
	private ListView<String> tabList;
	@FXML
	private TextField searchText;
	
	@FXML
	private void initialize(){
		Server s = new Server();
		String tabs = s.getTabs();
		try {
			s.closeConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//ArrayList<String> tabListArray = new ArrayList<>();
		//tabListArray.add("penes");
		//tabList.setItems((ObservableList<String>) tabListArray);
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
