package soundefy.layout;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import soundefy.net.Server;
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
		Server s = new Server();
		String tabs = s.getTabs();
		try {
			s.closeConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<String> tabList = new ArrayList<>();
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
