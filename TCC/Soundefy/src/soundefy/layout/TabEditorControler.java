package soundefy.layout;

import soundefy.util.TabRecognitionPlayer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Bar;
import model.Chord;
import model.Note;
import model.Tab;
import reconhecimento_de_notas.TabRecognitionListener;

public class TabEditorControler {
	public static final int MARGIN = 20;
	public static final int LINE_SPACING = 10;
	public static final double LINE_WIDTH = 1;
	public static final int TIME_SIGNIATURE_SIZE = 20;
	public static final int NOTES_SPACING = 20;
	public static final int LINE_X_START = 40;
	
	private int currentScroll = 0;
	private int scrollStep = 1;
	private int scrollPositionHeight = 30;
	private int pageTotalHeight;
	
	private Tab tab;

	@FXML
	private Canvas canvas;
	@FXML
	private AnchorPane parent;

	private GraphicsContext context;
	
	private boolean pressedOnScrollBar;
	private int dragStart;
	
	@FXML
	private void initialize() {
		try{
			tab = new Tab();
			tab.addBar(16,16,400);
			tab.addChord(new Note(1, 1), null, null, null, null, null, 1.0/4.0);
			tab.addChord(new Note(3, 12), null, null, null, null, null, 1.0/4.0);
			tab.addChord(new Note(3, 4), null, null, null, null, null, 1.0/4.0);
			tab.addChord(new Note(4, 5), null, null, null, null, null, 1.0/4.0);
			//TabRecognitionPlayer tabRecognition = new TabRecognitionPlayer(tab);
			//tabRecognition.play();
			TabRecognitionListener tabListener = new TabRecognitionListener(tab);
			tabListener.play();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		context = canvas.getGraphicsContext2D();
		
		canvas.widthProperty().addListener(observable -> drawTab());
		canvas.heightProperty().addListener(observable -> drawTab());
		
		canvas.widthProperty().bind(parent.widthProperty());
		canvas.heightProperty().bind(parent.heightProperty());
		
		canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getSceneX() >= (canvas.getWidth() - MARGIN + 2)){
					if(event.getSceneY() >= currentScroll && event.getSceneY() <= (currentScroll + scrollPositionHeight)){
						pressedOnScrollBar = true;
						dragStart = (int) event.getSceneY();
					}
				}
			}
		});
		
		canvas.setOnMouseDragged(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				if(pressedOnScrollBar){
					currentScroll += (event.getSceneY() - dragStart);
					if(currentScroll < 0){
						currentScroll = 0;
					}
					if((currentScroll*scrollStep) > pageTotalHeight- canvas.getHeight()){
						currentScroll = (int) Math.round((pageTotalHeight - canvas.getHeight())/scrollStep);
					}
					dragStart = (int)event.getSceneY();
					drawTab();
				}
			}
		});
		
		canvas.setOnMouseReleased(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				pressedOnScrollBar = false;
			}
			
		});
	}
	
	private void drawTimeSigniature(int y, int a, int b){
		context.setStroke(Color.BLACK);
		context.setFill(Color.BLACK);
		context.setFont(new Font("Times Roman", TIME_SIGNIATURE_SIZE));
		context.fillText(String.valueOf(a), MARGIN + 5, y+2*LINE_SPACING);
		context.fillText(String.valueOf(b), MARGIN + 5, y+4*LINE_SPACING);
	}
	
	private int drawLines(int y, int a, int b){
		int whereDrawTimeSigniature = y;
		context.setLineWidth(LINE_WIDTH);
		context.setStroke(Color.gray(0.6));
		context.strokeLine(MARGIN, y, MARGIN, y+ 5*LINE_SPACING);
		context.strokeLine(canvas.getWidth() - 1 - 2 * MARGIN, y, canvas.getWidth() - 1 - 2 * MARGIN, y + 5*LINE_SPACING);

		for(int i=0; i<6; i++){
			context.strokeLine(MARGIN, y, canvas.getWidth() - 2 * MARGIN, y);
			y += LINE_SPACING; 
		}
		
		drawTimeSigniature(whereDrawTimeSigniature, a, b);
		return y + MARGIN;
	}
	
	private void drawScrollBar(int maxScroll){
		if((currentScroll * scrollStep)  > maxScroll){
			currentScroll = maxScroll;
		}
		context.setFill(Color.LIGHTGRAY);
		context.fillRect(canvas.getWidth() - MARGIN, 0, MARGIN, canvas.getHeight());
		
		if(maxScroll != 0){
			scrollPositionHeight = 30;
			if(maxScroll > (canvas.getHeight() - scrollPositionHeight)){
				scrollStep =  (int) Math.round(maxScroll / (canvas.getHeight() - scrollPositionHeight));
			}else{
				scrollStep = 1;
				scrollPositionHeight = (int)canvas.getHeight() - maxScroll;
			}
			
			
			context.setFill(Color.GREY);
			
			context.fillRect(canvas.getWidth() - MARGIN + 2, currentScroll, MARGIN - 4, scrollPositionHeight);
		}else{
			currentScroll = 0;
			scrollStep = 1;
		}
	}
	
	private int drawBar(Bar bar, int whereY, int whereX){
		whereX += NOTES_SPACING;
		for(Chord c: bar.getNotes()){
			context.strokeLine(whereX, whereY, whereX , whereY+ 50);
			whereX += NOTES_SPACING;
		}
		
		return whereX;
	}
	
	private void drawTab() {
		int where = MARGIN  - (currentScroll * scrollStep);
		context.setFill(Color.WHITE);
		context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
				
		int lineWidth = (int)Math.round(canvas.getWidth()) - 2 * MARGIN - LINE_X_START;
		System.out.println(lineWidth);
		int currentLinePosition = lineWidth;
		for(Bar b : tab.getBars()){
			int barWidth = b.getNotes().size() * NOTES_SPACING;
			System.out.println("Bar width:" + (currentLinePosition + barWidth));
			if((currentLinePosition + barWidth) > lineWidth){
				where = drawLines(where, 4, 4);
				currentLinePosition = 0;
			}
		}
		
		where = drawLines(where, 4, 4);
		pageTotalHeight = (where + currentScroll * scrollStep);
		if((pageTotalHeight - canvas.getHeight()) > 0)
			drawScrollBar((int)(pageTotalHeight - canvas.getHeight()));
	}

}
