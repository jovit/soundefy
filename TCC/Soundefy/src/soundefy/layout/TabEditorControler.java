package soundefy.layout;

import java.io.File;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import soundefy.listener.NextNoteListener;
import soundefy.model.Bar;
import soundefy.model.Chord;
import soundefy.model.Note;
import soundefy.model.Tab;
import soundefy.model.TimeSignature;
import soundefy.reconhecimento_de_notas.TabRecognitionListener;
import soundefy.util.TabRecognitionPlayer;

public class TabEditorControler implements NextNoteListener {
	public static final int MARGIN = 20;
	public static final int LINE_SPACING = 12;
	public static final double LINE_WIDTH = 1;
	public static final int TIME_SIGNIATURE_SIZE = 20;
	public static final int NOTES_SIZE = 12;
	public static final int NOTES_SPACING = 30;
	public static final int LINE_X_START = 40;
	public static final double REST_SCALE = 0.5;

	private double currentScroll = 0;
	private double scrollStep = 1;
	private int scrollPositionHeight = 30;
	private int pageTotalHeight;

	private Image[] restImages;

	private Image[] noteImages;

	private int noteCount;

	private Tab tab;

	private int currentNote = -1;

	private boolean playingTab = false;

	@FXML
	private Canvas canvas;
	@FXML
	private AnchorPane parent;

	private Stage primaryStage;

	private GraphicsContext context;

	private boolean pressedOnScrollBar = false;
	private boolean editable;
	private int dragStart;

	private boolean addingNewNote = false;

	private TimeSignature standardTimeSigniature;
	private int standardTempo;

	private int selectedString = 1;
	private Note[] currentChord;

	
	private void setEditable(boolean editable){
		this.editable = editable;
	}
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	private void loadImages() {
		setEditable(true);
		restImages = new Image[8];
		restImages[0] = new Image("/resources/rests/wholerest.png");
		restImages[1] = new Image("/resources/rests/halfrest.png");
		restImages[2] = new Image("/resources/rests/quarterrest.png");
		restImages[3] = new Image("/resources/rests/eighthrest.png");
		restImages[4] = new Image("/resources/rests/sixteenthrest.png");
		restImages[5] = new Image("/resources/rests/thirtysecondrest.png");
		restImages[6] = new Image("/resources/rests/sixtyfourthrest.png");
		restImages[7] = new Image(
				"/resources/rests/hundredtwentyeighthrest.png");

		noteImages = new Image[8];
		noteImages[0] = new Image("/resources/notes/wholenote.png");
		noteImages[1] = new Image("/resources/notes/halfnote.png");
		noteImages[2] = new Image("/resources/notes/quarternote.png");
		noteImages[3] = new Image("/resources/notes/eighthnote.png");
		noteImages[4] = new Image("/resources/notes/sixteenthnote.png");
		noteImages[5] = new Image("/resources/notes/thirtysecondnote.png");
		noteImages[6] = new Image("/resources/notes/sixtyfourthnote.png");
		noteImages[7] = new Image(
				"/resources/notes/hundredtwentyeighthnote.png");
	}

	@FXML
	private void initialize() {
		try {			
			loadImages();

			this.tab = new Tab();
		} catch (Exception e) {
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
						currentScroll = (pageTotalHeight - canvas.getHeight())
								/ scrollStep;
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

		canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.P) {
					if (!playingTab && !addingNewNote) {
						playingTab = true;
						currentNote = -1;
						TabRecognitionPlayer tabRecognition = new TabRecognitionPlayer(
								tab);
						tabRecognition
								.setNextNoteListener(TabEditorControler.this);

						TabRecognitionListener tabRecognitionListener = new TabRecognitionListener(tab);
						tabRecognitionListener.setNextNoteListener(TabEditorControler.this);
						
						Task<Void> task = new Task<Void>() {
							@Override
							public Void call() {
								tabRecognition.play();
								return null;
							}
						};
						Thread t = new Thread(task);
						t.setDaemon(true);
						t.start();
					}
				}else if(!playingTab){
					if(!addingNewNote){
						if(event.isControlDown() && event.getCode() == KeyCode.O){
							FileChooser fileChooser = new FileChooser();
							fileChooser.setTitle("Open File");
							fileChooser.getExtensionFilters().addAll(
									new FileChooser.ExtensionFilter("Soundefy Files", "*.sdy"));
							File selectedFile = fileChooser.showOpenDialog(primaryStage);
							if (selectedFile != null) {
								try {
									TabEditorControler.this.tab = Tab.readFile(selectedFile.getAbsolutePath());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							drawTab();
						}
					}
					if(!addingNewNote && editable){
						if(event.getCode() == KeyCode.DIGIT1){ // new wholenote

							startAddingNewNote(1);
						} else if (event.getCode() == KeyCode.DIGIT2) { // new
																		// halfnote
							startAddingNewNote(1.0 / 2.0);
						} else if (event.getCode() == KeyCode.DIGIT3) { // new
																		// quarternote
							startAddingNewNote(1.0 / 4.0);
						} else if (event.getCode() == KeyCode.DIGIT4) { // new
																		// eighthnote
							startAddingNewNote(1.0 / 8.0);
						} else if (event.getCode() == KeyCode.DIGIT5) { // new
																		// sixteenthnote
							startAddingNewNote(1.0 / 16.0);
						} else if (event.getCode() == KeyCode.DIGIT6) { // new
																		// thirtysecondnote
							startAddingNewNote(1.0 / 32.0);
						} else if (event.getCode() == KeyCode.DIGIT7) { // new
																		// sixtyfourthnote
							startAddingNewNote(1.0 / 64.0);
						} else if (event.getCode() == KeyCode.DIGIT8) { // new
																		// hundredtwentyeightnote
							startAddingNewNote(1.0 / 128.0);
						} else if (event.getCode() == KeyCode.BACK_SPACE) {
							removeLastNote();
						}
					}else if(addingNewNote){
						if(event.getCode() == KeyCode.ENTER){

							addingNewNote = false;
							drawTab();
						} else if (event.getCode() == KeyCode.DOWN) {
							selectedString++;
							if (selectedString > 6) {
								selectedString = 6;
							}
							drawTab();
						} else if (event.getCode() == KeyCode.UP) {
							selectedString--;
							if (selectedString < 1) {
								selectedString = 1;
							}
							drawTab();
						} else if (event.getCode() == KeyCode.DIGIT0) {
							addToNote(0);
						} else if (event.getCode() == KeyCode.DIGIT1) {
							addToNote(1);
						} else if (event.getCode() == KeyCode.DIGIT2) {
							addToNote(2);
						} else if (event.getCode() == KeyCode.DIGIT3) {
							addToNote(3);
						} else if (event.getCode() == KeyCode.DIGIT4) {
							addToNote(4);
						} else if (event.getCode() == KeyCode.DIGIT5) {
							addToNote(5);
						} else if (event.getCode() == KeyCode.DIGIT6) {
							addToNote(6);
						} else if (event.getCode() == KeyCode.DIGIT7) {
							addToNote(7);
						} else if (event.getCode() == KeyCode.DIGIT8) {
							addToNote(8);
						} else if (event.getCode() == KeyCode.DIGIT9) {
							addToNote(9);
						} else if (event.getCode() == KeyCode.DELETE) {
							currentChord[selectedString - 1] = null;
							try {
								tab.setChord(currentChord[0], currentChord[1],
										currentChord[2], currentChord[3],
										currentChord[4], currentChord[5]);
							} catch (Exception e) {
								e.printStackTrace();
							}
							drawTab();
						}
					}
				}

			}
		});
		canvas.setFocusTraversable(true);

	}

	private void addToNote(int fret) {
		if (currentChord[selectedString - 1] == null) {
			currentChord[selectedString - 1] = new Note(selectedString, fret);
		} else {
			int newFret = ((currentChord[selectedString - 1].getFret() * 10) + fret) % 100;

			currentChord[selectedString - 1].setFret(newFret);
		}
		try {
			tab.setChord(currentChord[0], currentChord[1], currentChord[2],
					currentChord[3], currentChord[4], currentChord[5]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		drawTab();
	}

	public void setStandardTimeSigniature(TimeSignature timeSignature) {
		this.standardTimeSigniature = timeSignature;
	}

	public void setStandardTempo(int tempo) {
		this.standardTempo = tempo;
	}

	private void removeLastNote() {
		if (tab.getBar() != null) {
			tab.getBar().removeChord();
			if (tab.getBar().isEmpty()) {
				tab.removerBar();
			}
			drawTab();
		}
	}

	private void startAddingNewNote(double duration) {
		addingNewNote = true;
		selectedString = 1;
		try {
			if ((tab.getBar() == null) || (tab.getBar().isFilled())) {
				tab.addBar(standardTimeSigniature.getNumberOfBeats(),
						standardTimeSigniature.getWholeNoteDuration(),
						standardTempo);
			}
			tab.addChord(null, null, null, null, null, null, duration);
			currentChord = new Note[6];

		} catch (Exception e) {
			e.printStackTrace();
			addingNewNote = false;
		}

		drawTab();
		if ((pageTotalHeight - canvas.getHeight()) > 0)
			currentScroll = (pageTotalHeight - canvas.getHeight()) / scrollStep;
		drawTab();
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
		context.setFill(Color.LIGHTGRAY);
		context.fillRect(canvas.getWidth() - MARGIN, 0, MARGIN,
				canvas.getHeight());

		if (maxScroll != 0) {
			scrollPositionHeight = 30;
			if (maxScroll > (canvas.getHeight() - scrollPositionHeight)) {
				scrollStep = maxScroll
						/ (canvas.getHeight() - scrollPositionHeight);
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

		for (Chord c : bar.getNotes()) {
			if (c == (tab.getBar().getNotes().get(tab.getBar().getNotes()
					.size() - 1))
					&& addingNewNote) {
				/* When editing a new note */
				Note[] notes = c.getNotes();
				for (int i = 0; i < 6; i++) {

					if (notes[i] != null) {
						int whereNote = whereY
								+ (notes[i].getString() * LINE_SPACING)
								- (NOTES_SIZE / 2) - 1;
						if (notes[i].getFret() < 10) {
							context.setFill(Color.WHITE);
							if (notes[i].getString() == selectedString) {
								context.setFill(Color.LIGHTGRAY);
							}
							context.fillRect(whereX - NOTES_SIZE / 4, whereNote
									- NOTES_SIZE, NOTES_SIZE, NOTES_SIZE);
							context.setFill(Color.BLACK);
							context.fillText(
									String.valueOf(notes[i].getFret()), whereX,
									whereNote);
						} else {
							context.setFill(Color.WHITE);
							if (notes[i].getString() == selectedString) {
								context.setFill(Color.LIGHTGRAY);
							}
							context.fillRect(whereX - NOTES_SIZE / 2, whereNote
									- NOTES_SIZE, NOTES_SIZE, NOTES_SIZE);
							context.setFill(Color.BLACK);
							context.fillText(
									String.valueOf(notes[i].getFret()), whereX
											- (NOTES_SIZE / 2), whereNote);
						}
					} else if (notes[selectedString - 1] == null) {
						int whereNote = whereY
								+ (selectedString * LINE_SPACING)
								- (NOTES_SIZE / 2) - 1;
						context.setFill(Color.LIGHTGRAY);
						context.fillRect(whereX - NOTES_SIZE / 4, whereNote
								- NOTES_SIZE, NOTES_SIZE, NOTES_SIZE);
					}
				}
				/* End of editing a new note */
			} else {
				if (noteCount == currentNote) {
					context.setStroke(Color.BLACK);
					context.setLineWidth(2);
					if (whereY > (canvas.getHeight() - (LINE_SPACING * 5))) {
						currentScroll += (whereY - MARGIN) / scrollStep;
						if ((currentScroll * scrollStep) > pageTotalHeight
								- canvas.getHeight()) {
							currentScroll = (pageTotalHeight - canvas
									.getHeight()) / scrollStep;
						}
					}
					context.strokeLine(whereX, whereY, whereX, whereY + 5
							* LINE_SPACING);
				}
				noteCount++;
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
							context.fillText(
									String.valueOf(notes[i].getFret()), whereX,
									whereNote);
						} else {
							context.setFill(Color.WHITE);
							context.fillRect(whereX - NOTES_SIZE / 2, whereNote
									- NOTES_SIZE, NOTES_SIZE, NOTES_SIZE);
							context.setFill(Color.BLACK);
							context.fillText(
									String.valueOf(notes[i].getFret()), whereX
											- (NOTES_SIZE / 2), whereNote);
						}
					}

				}

				if (isRest) {
					Image restImage = null;
					if (c.getDuration() == 1.0) {
						restImage = restImages[0];
					} else if (c.getDuration() == 1.0 / 2.0) {
						restImage = restImages[1];
					} else if (c.getDuration() == 1.0 / 4.0) {
						restImage = restImages[2];
					} else if (c.getDuration() == 1.0 / 8.0) {
						restImage = restImages[3];
					} else if (c.getDuration() == 1.0 / 16.0) {
						restImage = restImages[4];
					} else if (c.getDuration() == 1.0 / 32.0) {
						restImage = restImages[5];
					} else if (c.getDuration() == 1.0 / 64.0) {
						restImage = restImages[6];
					} else if (c.getDuration() == 1.0 / 128.0) {
						restImage = restImages[7];
					}

					int rescaledHeight = (int) Math.round(restImage.getHeight()
							* REST_SCALE);
					int rescaledWidth = (int) Math.round(restImage.getWidth()
							* REST_SCALE);

					int whereYRest = ((whereY + (int) Math
							.round(2.5 * LINE_SPACING)) - ((rescaledHeight / 2) + whereY))
							+ whereY;

					context.drawImage(restImage, whereX - rescaledWidth / 2,
							whereYRest, rescaledWidth, rescaledHeight);
				}

			}
			whereX += NOTES_SPACING;
		}
		context.setStroke(Color.gray(0.6));
		context.strokeLine(whereX, whereY, whereX, whereY + 5 * LINE_SPACING);
		context.setLineWidth(LINE_WIDTH);

		return whereX;
	}

	private void drawTab() {

		int where = (int) (MARGIN - Math.round(currentScroll * scrollStep));
		int whereBar = where;

		context.setFill(Color.WHITE);
		context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		int lineWidth = (int) Math.round(canvas.getWidth()) - 2 * MARGIN
				- LINE_X_START;
		int currentLinePosition = lineWidth;

		noteCount = 0;

		int previousWholeNoteDuration = 0;
		int previousNumberOfBeats = 0;
		for (Bar b : tab.getBars()) {
			int barWidth = b.getNotes().size() * NOTES_SPACING;
			if (((currentLinePosition + barWidth) > lineWidth)
					|| ((previousWholeNoteDuration != b.getTimeSignature()
							.getWholeNoteDuration()) || (previousNumberOfBeats != b
							.getTimeSignature().getNumberOfBeats()))) {
				whereBar = where;
				where = drawLines(where, b.getTimeSignature()
						.getNumberOfBeats(), b.getTimeSignature()
						.getWholeNoteDuration());
				currentLinePosition = LINE_X_START;
				if ((previousWholeNoteDuration != b.getTimeSignature()
						.getWholeNoteDuration())
						|| (previousNumberOfBeats != b.getTimeSignature()
								.getNumberOfBeats())) {
					previousWholeNoteDuration = b.getTimeSignature()
							.getWholeNoteDuration();
					previousNumberOfBeats = b.getTimeSignature()
							.getNumberOfBeats();
				}
			}
			currentLinePosition = drawBar(b, whereBar, currentLinePosition);

		}

		pageTotalHeight = (int) (where + Math.round(currentScroll * scrollStep));
		if ((pageTotalHeight - canvas.getHeight()) > 0)
			drawScrollBar((int) (pageTotalHeight - canvas.getHeight()));
	}

	@Override
	public void nextNote() {
		currentNote++;
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				drawTab();
			}
		});

	}

	@Override
	public void tabFinished() {
		playingTab = false;
		currentNote = -1;
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				drawTab();
			}
		});
	}

}
