package soundefy.layout;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BarSettingsController {
	
	@FXML
	private TextField wholeNoteDuration;
	
	@FXML
	private TextField numberOfBeats;
	
	@FXML
	private TextField tempo;
	
	private boolean okClicked = false;
	
	private Stage dialogStage;
	
	public void setDialogStage(Stage dialogStage){
		this.dialogStage = dialogStage;
	}
	
	public boolean isOkClicked() {
		return okClicked;
	}
	
	public int getWholeNoteDuration() {
		return Integer.parseInt(wholeNoteDuration.getText());
	}
	
	public int getNumberOfBeats() {
		return Integer.parseInt(numberOfBeats.getText());
	}
	
	public int getTempo(){
		return Integer.parseInt(tempo.getText());
	}
	
	private ChangeListener<String> getMaxSizeListener(int maxSize, TextField field){
		return new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(newValue.length() > maxSize){
					field.setText(oldValue);
				}
			}
		};
	}
	
	private ChangeListener<String> getNumbersOnlyListener(TextField field){
		return new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(!newValue.equals("") && !isInteger(newValue)){
					field.setText(oldValue);
				}
			}
		};
	}
	
	public static boolean isInteger(String s) {
	    return isInteger(s,10);
	}

	public static boolean isInteger(String s, int radix) {
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(i == 0 && s.charAt(i) == '-') {
	            if(s.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(s.charAt(i),radix) < 0) return false;
	    }
	    return true;
	}
	
	private boolean validateTempo(){
		return false;
	}
	
	private boolean validateNumberOfBeats(){
		return false;
	}
	
	private boolean validateWholeNumberDuration(){
		return false;
	}
	
	private boolean validateFields(){
		boolean valid = true;
		
		if(!validateNumberOfBeats()){
			valid = false;
		}
		
		if(!validateTempo()){
			valid = false;
		}
		
		if(!validateWholeNumberDuration()){
			valid = false;
		}
		
		return valid;
	}
	
	@FXML
	private void initialize(){
		wholeNoteDuration.textProperty().addListener(getMaxSizeListener(2, wholeNoteDuration));
		wholeNoteDuration.textProperty().addListener(getNumbersOnlyListener(wholeNoteDuration));
		
		tempo.textProperty().addListener(getMaxSizeListener(3, tempo));
		tempo.textProperty().addListener(getNumbersOnlyListener(tempo));
		
		numberOfBeats.textProperty().addListener(getMaxSizeListener(2, numberOfBeats));
		numberOfBeats.textProperty().addListener(getNumbersOnlyListener(numberOfBeats));
	}
	
	@FXML
	private void cancelClick(){
		dialogStage.close();
	}
	
	@FXML
	private void confirmClick(){
		if(validateFields()){
			this.okClicked = true;
			dialogStage.close();
		}
	}
}
