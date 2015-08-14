package model;

public class Note {
	private int string;
	private int fret;

	public Note(int string, int fret) {
		this.string = string;
		this.fret = fret;
	}

	public int getString() {
		return string;
	}

	public void setString(int string) {
		this.string = string;
	}

	public int getFret() {
		return fret;
	}

	public void setFret(int fret) {
		this.fret = fret;
	}

}
