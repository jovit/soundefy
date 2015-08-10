package model;

public class Note {
	private int string;
	private int fret;
	private String name;

	public Note(int string, int fret, String name) {
		this.string = string;
		this.fret = fret;
		this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
