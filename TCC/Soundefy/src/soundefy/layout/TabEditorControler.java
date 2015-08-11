package soundefy.layout;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import model.Tab;

public class TabEditorControler {
	public static final int MARGIN = 20;
	private Tab tab;

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
		canvas.heightProperty().bind(parent.heightProperty());
		
		drawTab();
		
	}

	private void drawTab() {
		
		context.setFill(Color.BLACK);
		context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

	}

}
