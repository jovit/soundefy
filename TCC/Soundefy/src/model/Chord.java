package model;

public class Chord {
	private Note notes[];
	private float duration;
	
	public Chord(Note notes[], float duration)throws Exception{
		if(notes.length != 6){
			throw new Exception("Invalid number of notes");
		}
		this.notes = notes;
		this.duration = duration;
	}

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	public Note[] getNotes() {
		return notes;
	}

	public void setNotes(Note[] notes) {
		this.notes = notes;
	}
	
	
}
