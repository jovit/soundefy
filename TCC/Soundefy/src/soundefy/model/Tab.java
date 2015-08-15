package soundefy.model;

import java.util.ArrayList;

public class Tab {
	private ArrayList<Bar> bars;

	public Tab() {
		bars = new ArrayList<>();
	}

	public void addBar(int numberOfBeats, int wholeNoteDuration, int tempo) throws Exception {
		bars.add(new Bar(numberOfBeats, wholeNoteDuration, tempo));
	}
	
	public void addChord(Note note1, Note note2, Note note3, Note note4, Note note5, Note note6, double duration) throws Exception{
		Note[] notes = new Note[6];
		notes[0] = note1;
		notes[1] = note2;
		notes[2] = note3;
		notes[3] = note4;
		notes[4] = note5;
		notes[5] = note6;
		
		bars.get(bars.size()-1).addChord(new Chord(notes, duration));;
		
		
	}

	public void removerBar() {
		bars.remove(bars.size() - 1);
	}
	
	public Bar getBar(){
		return bars.get(bars.size() - 1);
	}
	
	public ArrayList<Bar> getBars(){
		return bars;
	}
}
