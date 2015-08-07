package soundefy.layout;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;


public class TabEditorControler {
	@FXML
	private Canvas canvas;
	
	private GraphicsContext context;
	
	@FXML
	private void initialize(){

		context = canvas.getGraphicsContext2D();
		drawTab();
	}
	
	private void drawTab(){
		context.beginPath();
		context.moveTo(10, 10);
		context.lineTo(canvas.getWidth() - 10, 10);
		System.out.println(canvas.getLayoutBounds().getWidth());
		context.stroke();
	}
	
	@FXML
	private void onMouseDrag(){
		drawTab();
	}

	@FXML
	private void onMousePressed(){
		drawTab();
	}
}
