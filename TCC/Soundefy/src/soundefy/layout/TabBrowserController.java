package soundefy.layout;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import soundefy.net.Server;

public class TabBrowserController {
	@FXML
	private ListView<String> tabList;
	private ArrayList<String> tabIds;
	ObservableList<String> listOfTabs;
	@FXML
	private TextField searchText;
	@FXML
	private Button downloadButton;
	private Stage primaryStage;
	
	public void setPrimaryStage(Stage primaryStage){
		this.primaryStage = primaryStage;
	}
	
	@FXML
	private void initialize(){
		tabIds = new ArrayList<String>();
		
		Server s = new Server();
		String tabs = s.getTabs();
		try {
			s.closeConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listOfTabs = FXCollections.observableArrayList();
		
		StringTokenizer tabsTokenizer = new StringTokenizer(tabs,"/");
		while(tabsTokenizer.hasMoreTokens()){
			String id = tabsTokenizer.nextToken();
			String tab = tabsTokenizer.nextToken();
			
			tabIds.add(id);
			listOfTabs.add(tab);
		}
		
		if(listOfTabs.size() > 0){
			downloadButton.setDisable(false);
		}
		
		tabList.setItems(listOfTabs);
	}
	
	private File openFileChooserForSaving(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save File");
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Soundefy Files", "*.sdy"));
		return fileChooser.showSaveDialog(primaryStage);
	}
	
	@FXML
	private void onSearchClick(){
		
	}
	
	@FXML
	private void onDownloadClick(){
		int index = tabList.getSelectionModel().selectedIndexProperty().get();
		String tab = listOfTabs.get(index);
		
		int tabId = Integer.valueOf(tabIds.get(index));
		
		Server s = new Server();
		
		byte[] tabFile = s.download(tabId);
		
		try {
			s.closeConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		File selectedFile = openFileChooserForSaving();
		if(selectedFile != null){
			DataOutputStream writeFile;
			try {
				writeFile = new DataOutputStream(new FileOutputStream(selectedFile));	
				writeFile.write(tabFile);
				writeFile.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
}
