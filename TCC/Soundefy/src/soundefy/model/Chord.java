package soundefy.model;

public class Chord {
	private Note notes[];
	private double duration;
	
	public Chord(Note notes[], double duration)throws Exception{
		if(notes.length != 6){
			throw new Exception("Invalid number of notes");
		}
		this.notes = notes;
		this.duration = duration;
	}

	public double getDuration() {
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
