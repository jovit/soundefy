package soundefy.layout;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TabEditorControler {
	public static final int MARGIN = 20;
	public static final int LINE_SPACING = 10;
	public static final double LINE_WIDTH = 1;

	@FXML
	private Canvas canvas;
	@FXML
	private AnchorPane parent;

	private GraphicsContext context;

	@FXML
	private void initialize() {
		/*tab = new Tab();
		try {
			tab.addBar(4, 4, 100);
			Note chord[] = {new Note(), new Note()};
			tab.getBar().addChord(new Chord(chord));
		} catch (Exception ex) {
		}*/
		
		context = canvas.getGraphicsContext2D();
		
		canvas.widthProperty().addListener(observable -> drawTab());
		canvas.heightProperty().addListener(observable -> drawTab());
		
		canvas.widthProperty().bind(parent.widthProperty().subtract(MARGIN));
		canvas.heightProperty().bind(parent.heightProperty().subtract(MARGIN));
		
		
	}
	
	private void drawTimeSigniature(int a, int b, int y){
		context.setStroke(Color.BLACK);
		context.setFill(Color.BLACK);
		context.setFont(new Font("Arial", 20));
		context.fillText(String.valueOf(a), MARGIN + 5, y+20);
		context.fillText(String.valueOf(b), MARGIN + 5, y+40);
	}
	
	private int drawLines(int y){
		context.setLineWidth(LINE_WIDTH);
		context.setStroke(Color.gray(0.6));
		context.strokeLine(MARGIN, y, MARGIN, y+ 5*LINE_SPACING);
		context.strokeLine(canvas.getWidth() - 1, y, canvas.getWidth() - 1, y + 5*LINE_SPACING);

		for(int i=0; i<6; i++){
			context.strokeLine(MARGIN, y, canvas.getWidth(), y);
			y += LINE_SPACING; 
		}
		
		return y + MARGIN;
	}

	private void drawTab() {
		int where = MARGIN;
		context.setFill(Color.WHITE);
		context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		where = drawLines(where);
		drawTimeSigniature(4,4,MARGIN);
		
		drawLines(where);
		drawTimeSigniature(4,4,where);

	}

}
