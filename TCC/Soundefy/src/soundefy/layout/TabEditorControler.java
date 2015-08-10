package soundefy.layout;

import model.Tab;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;

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
		tab = new Tab();
		try {
			tab.addBar(4, 4, 100);
			Note chord[] = {new Note(), new Note()};
			tab.getBar().addChord(new Chord(chord));
		} catch (Exception ex) {
		}
		
		context = canvas.getGraphicsContext2D();

		canvas.widthProperty().bind(parent.widthProperty().subtract(MARGIN));
		canvas.heightProperty().bind(parent.heightProperty());

		canvas.widthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {
				drawTab();

			}
		});

		drawTab();
		parent.requestLayout();
	}

	private void drawTab() {
		context.beginPath();
		context.moveTo(MARGIN, MARGIN);
		context.lineTo(canvas.getWidth(), MARGIN);
		System.out.println(canvas.getLayoutBounds().getWidth());
		context.stroke();

	}

}
