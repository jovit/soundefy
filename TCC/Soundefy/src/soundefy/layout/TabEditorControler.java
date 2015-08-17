package soundefy.layout;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import listener.NextNoteListener;
import soundefy.model.Bar;
import soundefy.model.Chord;
import soundefy.model.Note;
import soundefy.model.Tab;
import soundefy.util.TabRecognitionPlayer;

public class TabEditorControler implements NextNoteListener{
	public static final int MARGIN = 20;
	public static final int LINE_SPACING = 10;
	public static final double LINE_WIDTH = 1;
	public static final int TIME_SIGNIATURE_SIZE = 20;
	public static final int NOTES_SIZE = 12;
	public static final int NOTES_SPACING = 30;
	public static final int LINE_X_START = 40;
	public static final double REST_SCALE = 0.5;

	private int currentScroll = 0;
	private int scrollStep = 1;
	private int scrollPositionHeight = 30;
	private int pageTotalHeight;

	private Image[] restImages;

	private Image[] noteImages;
	
	private int noteCount;
	
	private Tab tab;
	
	private int currentNote = 0;

	@FXML
	private Canvas canvas;
	@FXML
	private AnchorPane parent;

	private GraphicsContext context;

	private boolean pressedOnScrollBar;
	private int dragStart;

	
	private void loadImages(){
		restImages = new Image[8];
		restImages[0] = new Image("./resources/rests/wholerest.png");
		restImages[1] = new Image("./resources/rests/halfrest.png");
		restImages[2] = new Image("./resources/rests/quarterrest.png");
		restImages[3] = new Image("./resources/rests/eighthrest.png");
		restImages[4] = new Image("./resources/rests/sixteenthrest.png");
		restImages[5] = new Image("./resources/rests/thirtysecondrest.png");
		restImages[6] = new Image("./resources/rests/sixtyfourthrest.png");
		restImages[7] = new Image("./resources/rests/hundredtwentyeighthrest.png");
		
		noteImages = new Image[8];
		noteImages[0] = new Image("./resources/notes/wholenote.png");
		noteImages[1] = new Image("./resources/notes/halfnote.png");
		noteImages[2] = new Image("./resources/notes/quarternote.png");
		noteImages[3] = new Image("./resources/notes/eighthnote.png");
		noteImages[4] = new Image("./resources/notes/sixteenthnote.png");
		noteImages[5] = new Image("./resources/notes/thirtysecondnote.png");
		noteImages[6] = new Image("./resources/notes/sixtyfourthnote.png");
		noteImages[7] = new Image("./resources/notes/hundredtwentyeighthnote.png");		
	}
	
	@FXML
	private void initialize() {
		try{
			loadImages();
			
			tab = new Tab();
			tab.addBar(4, 4, 126);
			tab.addChord(null, null, new Note(3, 14), null, null, null, 1.0/8.0);
			tab.addChord(null, null, new Note(3, 14), null, null, null, 1.0/8.0);
			tab.addChord(new Note(1, 14), null, null, null, null, null, 1.0/8.0);
			tab.addChord(null, null, new Note(3, 14), null, null, null, 1.0/8.0);
			tab.addChord(null, null, null, new Note(4, 12), null, null, 1.0/8.0);
			tab.addChord(null, null, new Note(3, 14), null, null, null, 1.0/8.0);
			tab.addChord(new Note(1, 14), null, null, null, null, null, 1.0/8.0);
			tab.addChord(null, null, new Note(3, 14), null, null, null, 1.0/8.0);
			tab.addBar(4, 4, 126);
			tab.addChord(null, null, null, new Note(4, 12), null, null, 1.0/8.0);
			tab.addChord(null, new Note(2, 15), null, null, null, null, 1.0/8.0);
			tab.addChord(null, null, new Note(3, 14), null, null, null, 1.0/8.0);
			tab.addChord(null, null, new Note(3, 12), null, null, null, 1.0/8.0);
			tab.addChord(null, null, null, null, null, null, 1.0/8.0);
			tab.addChord(null, null, new Note(3, 14), null, null, null, 1.0/8.0);
			tab.addChord(new Note(1, 14), null, null, null, null, null, 1.0/8.0);
			tab.addChord(null, null, new Note(3, 14), null, null, null, 1.0/8.0);
			tab.addBar(4, 4, 126);
			tab.addChord(null, null, null, new Note(4, 14), null, null,
					1.0 / 8.0);
			tab.addChord(null, new Note(2, 15), null, null, null, null,
					1.0 / 8.0);
			tab.addChord(null, null, new Note(3, 14), null, null, null,
					1.0 / 8.0);
			tab.addChord(null, null, new Note(3, 12), null, null, null,
					1.0 / 8.0);
			tab.addChord(new Note(1, 15), null, null, null, null, null,
					1.0 / 8.0);
			tab.addChord(null, null, new Note(3, 14), null, null, null,
					1.0 / 8.0);
			tab.addChord(new Note(1, 14), null, null, null, null, null,
					1.0 / 8.0);
			tab.addChord(null, null, new Note(3, 14), null, null, null,
					1.0 / 8.0);
			tab.addBar(4, 4, 126);
			tab.addChord(null, null, null, new Note(4, 14), null, null,
					1.0 / 8.0);
			tab.addChord(null, new Note(2, 15), null, null, null, null,
					1.0 / 8.0);
			tab.addChord(null, null, new Note(3, 14), null, null, null,
					1.0 / 8.0);
			tab.addChord(null, null, new Note(3, 12), null, null, null,
					1.0 / 8.0);
			tab.addChord(new Note(1, 15), null, null, null, null, null,
					1.0 / 8.0);
			tab.addChord(null, null, new Note(3, 14), null, null, null,
					1.0 / 8.0);
			tab.addChord(new Note(1, 14), null, null, null, null, null,
					1.0 / 8.0);
			tab.addChord(null, null, new Note(3, 14), null, null, null,
					1.0 / 8.0);
			tab.addBar(4, 4, 126);
			tab.addChord(null, null, new Note(3, 12), null, null, null,
					1.0 / 8.0);
			tab.addChord(null, new Note(2, 15), null, null, null, null,
					1.0 / 8.0);
			tab.addChord(null, null, new Note(3, 14), null, null, null,
					1.0 / 8.0);
			tab.addChord(null, null, new Note(3, 12), null, null, null,
					1.0 / 8.0);
			tab.addChord(new Note(1, 15), null, null, null, null, null,
					1.0 / 8.0);
			tab.addChord(null, null, new Note(3, 14), null, null, null,
					1.0 / 8.0);
			tab.addChord(new Note(1, 14), null, null, null, null, null,
					1.0 / 8.0);
			tab.addChord(null, null, new Note(3, 14), null, null, null,
					1.0 / 8.0);
			tab.addBar(4, 4, 126);
			tab.addChord(null, null, new Note(3, 12), null, null, null,
					1.0 / 8.0);
			tab.addChord(null, new Note(2, 15), null, null, null, null,
					1.0 / 8.0);
			tab.addChord(null, null, new Note(3, 14), null, null, null,
					1.0 / 8.0);
			tab.addChord(null, null, new Note(3, 12), null, null, null,
					1.0 / 8.0);
			tab.addChord(new Note(1, 15), null, null, null, null, null,
					1.0 / 8.0);
			tab.addChord(null, null, new Note(3, 14), null, null, null,
					1.0 / 8.0);
			tab.addChord(new Note(1, 14), null, null, null, null, null,
					1.0 / 8.0);
			tab.addChord(null, null, new Note(3, 14), null, null, null,
					1.0 / 8.0);
			tab.addBar(4, 4, 126);
			tab.addChord(null, null, null, new Note(4, 12), null, null,
					1.0 / 8.0);
			tab.addChord(null, new Note(2, 15), null, null, null, null,
					1.0 / 8.0);
			tab.addChord(null, null, new Note(3, 14), null, null, null,
					1.0 / 8.0);
			tab.addChord(null, null, new Note(3, 12), null, null, null,
					1.0 / 8.0);
			tab.addChord(new Note(1, 15), null, null, null, null, null,
					1.0 / 8.0);
			tab.addChord(null, null, new Note(3, 14), null, null, null,
					1.0 / 8.0);
			tab.addChord(new Note(1, 14), null, null, null, null, null,
					1.0 / 8.0);
			tab.addChord(null, null, new Note(3, 14), null, null, null,
					1.0 / 8.0);
			tab.addBar(4, 4, 126);
			tab.addChord(null, null, null, new Note(4, 12), null, null, 1.0/8.0);
			tab.addChord(null, new Note(2, 15), null, null, null, null, 1.0/8.0);
			tab.addChord(null, null, new Note(3, 14), null, null, null, 1.0/8.0);
			tab.addChord(null, null, new Note(3, 12), null, null, null, 1.0/8.0);
			tab.addChord(new Note(1, 15), null, null, null, null, null, 1.0/8.0);
			tab.addChord(null, null, new Note(3, 14), null, null, null, 1.0/8.0);
			tab.addChord(new Note(1, 14), null, null, null, null, null, 1.0/8.0);
			tab.addChord(null, null, new Note(3, 14), null, null, null, 1.0/8.0);
			
			//TabRecognitionListener tabListener = new TabRecognitionListener(tab);
			//tabListener.play();
		}catch(Exception e){
			e.printStackTrace();
		}

		context = canvas.getGraphicsContext2D();

		
		canvas.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {
				currentScroll = 0;
				drawTab();
			}
		});
		canvas.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {
				currentScroll = 0;
				drawTab();
			}
		});		
		canvas.widthProperty().bind(parent.widthProperty());
		canvas.heightProperty().bind(parent.heightProperty());

		canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getSceneX() >= (canvas.getWidth() - MARGIN + 2)) {
					if (event.getSceneY() >= currentScroll
							&& event.getSceneY() <= (currentScroll + scrollPositionHeight)) {
						pressedOnScrollBar = true;
						dragStart = (int) event.getSceneY();
					}
				}
			}
		});

		canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (pressedOnScrollBar) {
					currentScroll += (event.getSceneY() - dragStart);
					if (currentScroll < 0) {
						currentScroll = 0;
					}
					if ((currentScroll * scrollStep) > pageTotalHeight
							- canvas.getHeight()) {
						currentScroll = (int) Math
								.round((pageTotalHeight - canvas.getHeight())
										/ scrollStep);
					}
					dragStart = (int) event.getSceneY();
					drawTab();
				}
			}
		});

		canvas.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				pressedOnScrollBar = false;
			}

		});
		
		
	}
	
	@FXML
	private void onCanvasClick(){
		TabRecognitionPlayer tabRecognition = new TabRecognitionPlayer(tab);
		//tabRecognition.setNextNoteListener(this);
		
		Task<Void> task = new Task<Void>() {
		    @Override public Void call() {
		        tabRecognition.play();
				return null;
		    }
		};
		new Thread(task).start();
	}

	private void drawTimeSigniature(int y, int a, int b) {
		context.setStroke(Color.BLACK);
		context.setFill(Color.BLACK);
		context.setFont(new Font("Times Roman", TIME_SIGNIATURE_SIZE));
		context.fillText(String.valueOf(a), MARGIN + 5, y + 2 * LINE_SPACING);
		context.fillText(String.valueOf(b), MARGIN + 5, y + 4 * LINE_SPACING);
	}

	private int drawLines(int y, int a, int b) {
		int whereDrawTimeSigniature = y;
		context.setLineWidth(LINE_WIDTH);
		context.setStroke(Color.gray(0.6));
		context.strokeLine(MARGIN, y, MARGIN, y + 5 * LINE_SPACING);
		context.strokeLine(canvas.getWidth() - 1 - 2 * MARGIN, y,
				canvas.getWidth() - 1 - 2 * MARGIN, y + 5 * LINE_SPACING);

		for (int i = 0; i < 6; i++) {
			context.strokeLine(MARGIN, y, canvas.getWidth() - 2 * MARGIN, y);
			y += LINE_SPACING;
		}

		drawTimeSigniature(whereDrawTimeSigniature, a, b);
		return y + MARGIN * 2;
	}

	private void drawScrollBar(int maxScroll) {
		if ((currentScroll * scrollStep) > maxScroll) {
			currentScroll = maxScroll;
		}
		context.setFill(Color.LIGHTGRAY);
		context.fillRect(canvas.getWidth() - MARGIN, 0, MARGIN,
				canvas.getHeight());

		if (maxScroll != 0) {
			scrollPositionHeight = 30;
			if (maxScroll > (canvas.getHeight() - scrollPositionHeight)) {
				scrollStep = (int) Math.round(maxScroll
						/ (canvas.getHeight() - scrollPositionHeight));
			} else {
				scrollStep = 1;
				scrollPositionHeight = (int) canvas.getHeight() - maxScroll;
			}

			context.setFill(Color.GREY);

			context.fillRect(canvas.getWidth() - MARGIN + 2, currentScroll,
					MARGIN - 4, scrollPositionHeight);
		} else {
			currentScroll = 0;
			scrollStep = 1;
		}
	}

	private int drawBar(Bar bar, int whereY, int whereX) {
		context.setFill(Color.BLACK);
		context.setFont(new Font("Arial", NOTES_SIZE));

		whereX += NOTES_SPACING;

		
		for(Chord c: bar.getNotes()){
			if(noteCount == currentNote){
				context.setStroke(Color.LIGHTGRAY);
				context.strokeLine(whereX, whereY, whereX, whereY+5*LINE_SPACING);
			}
			noteCount ++ ;
			Note[] notes = c.getNotes();
			boolean isRest = true;
			for (int i = 0; i < 6; i++) {
				if (notes[i] != null) {
					isRest = false;
					int whereNote = whereY
							+ (notes[i].getString() * LINE_SPACING)
							- (NOTES_SIZE / 2) - 1;
					if (notes[i].getFret() < 10) {
						context.setFill(Color.WHITE);
						context.fillRect(whereX - NOTES_SIZE / 4, whereNote
								- NOTES_SIZE, NOTES_SIZE, NOTES_SIZE);
						context.setFill(Color.BLACK);
						context.fillText(String.valueOf(notes[i].getFret()),
								whereX, whereNote);
					} else {
						context.setFill(Color.WHITE);
						context.fillRect(whereX - NOTES_SIZE / 2, whereNote
								- NOTES_SIZE, NOTES_SIZE, NOTES_SIZE);
						context.setFill(Color.BLACK);
						context.fillText(String.valueOf(notes[i].getFret()),
								whereX - (NOTES_SIZE / 2), whereNote);
					}
				}

			}

			if(isRest){
				Image restImage = null;
				if(c.getDuration() == 1.0){
					restImage = restImages[0];
				}else if(c.getDuration() == 1.0/2.0){
					restImage = restImages[1];
				}else if(c.getDuration() == 1.0/4.0){
					restImage = restImages[2];
				}else if(c.getDuration() == 1.0/8.0){
					restImage = restImages[3];
				}else if(c.getDuration() == 1.0/16.0){
					restImage = restImages[4];
				}else if(c.getDuration() == 1.0/32.0){
					restImage = restImages[5];
				}else if(c.getDuration() == 1.0/64.0){
					restImage = restImages[6];
				}else if(c.getDuration() == 1.0/128.0){
					restImage = restImages[7];
				}
				
				int rescaledHeight = (int)Math.round(restImage.getHeight() * REST_SCALE);
				int rescaledWidth = (int)Math.round(restImage.getWidth() * REST_SCALE);
				
				int whereYRest = ((whereY + (int)Math.round(2.5*LINE_SPACING)) - ((rescaledHeight/2) + whereY)) + whereY ;
				
				context.drawImage(restImage, whereX -  rescaledWidth/2, whereYRest, rescaledWidth, rescaledHeight);
			}
			whereX += NOTES_SPACING;
		}
		context.setStroke(Color.gray(0.6));
		context.strokeLine(whereX, whereY, whereX, whereY + 5*LINE_SPACING);
		
		return whereX;
	}

	private void drawTab() {
		int where = MARGIN - (currentScroll * scrollStep);
		int whereBar = where;

		context.setFill(Color.WHITE);
		context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		int lineWidth = (int) Math.round(canvas.getWidth()) - 2 * MARGIN
				- LINE_X_START;
		int currentLinePosition = lineWidth;

		
		noteCount = 0;
		for(Bar b : tab.getBars()){
			int barWidth = b.getNotes().size() * NOTES_SPACING;
			if ((currentLinePosition + barWidth) > lineWidth) {
				whereBar = where;
				where = drawLines(where, 4, 4);
				currentLinePosition = LINE_X_START;
			}
			currentLinePosition = drawBar(b, whereBar, currentLinePosition);

		}

		pageTotalHeight = (where + currentScroll * scrollStep);
		if ((pageTotalHeight - canvas.getHeight()) > 0)
			drawScrollBar((int) (pageTotalHeight - canvas.getHeight()));
	}


	@Override
	public void nextNote() {
		currentNote++;
		drawTab();
	}

}
