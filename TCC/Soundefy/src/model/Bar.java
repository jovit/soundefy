package model;

import java.util.ArrayList;

public class Bar {
	private ArrayList<Chord> notes;
	private TimeSignature timeSignature;
	private int tempo;
	private float barCompletion;

	public ArrayList<Chord> getNotes() {
		return notes;
	}

	public void setNotes(ArrayList<Chord> notes) {
		this.notes = notes;
	}

	public TimeSignature getTimeSignature() {
		return timeSignature;
	}

	public void setTimeSignature(TimeSignature timeSignature) {
		this.timeSignature = timeSignature;
	}

	public int getTempo() {
		return tempo;
	}

	public void setTempo(int tempo) {
		this.tempo = tempo;
	}

	public Bar(int numberOfBeats, int wholeNoteDuration, int tempo)
			throws Exception {
		if (((wholeNoteDuration & (wholeNoteDuration - 1)) == 0) // VERIFICA SE A MAIOR NOTA EH POTENCIA DE 2
				&& (wholeNoteDuration < 32)) {
			this.timeSignature = new TimeSignature(numberOfBeats,
					wholeNoteDuration);
			this.notes = new ArrayList<>();
			this.tempo = tempo;
		} else {
			throw new Exception("Wrong whole note duration");
		}
	}
	
	public boolean isFilled(){
		return barCompletion == this.timeSignature.getNumberOfBeats();
	}

	public void addChord(Chord chord) throws Exception {
		double completion = barCompletion + chord.getDuration()
				* this.timeSignature.getWholeNoteDuration();
		if (completion > this.timeSignature.getNumberOfBeats()) {
			throw new Exception("Bar duration exceeded");
		}
		barCompletion += completion - barCompletion;
		notes.add(chord);
	}

	public boolean barCompleted() {
		return barCompletion == this.timeSignature.getNumberOfBeats();
	}
	
	public int numberOfNotes(){
		return notes.size();
	}

	public void removeChord(){
		double duration = notes.get(notes.size() - 1).getDuration() * this.timeSignature.getWholeNoteDuration();
		barCompletion -= duration;
		notes.remove(notes.size() - 1);
	}
}
