package model;

public class TimeSignature {
	private int numberOfBeats;
	private int wholeNoteDuration;

	public int getNumberOfBeats() {
		return numberOfBeats;
	}

	public void setNumberOfBeats(int numberOfBeats) {
		this.numberOfBeats = numberOfBeats;
	}

	public int getWholeNoteDuration() {
		return wholeNoteDuration;
	}

	public void setWholeNoteDuration(int wholeNoteDuration) {
		this.wholeNoteDuration = wholeNoteDuration;
	}

	public TimeSignature(int numberOfBeats, int wholeNoteDuration) {
		this.numberOfBeats = numberOfBeats;
		this.wholeNoteDuration = wholeNoteDuration;
	}
}
