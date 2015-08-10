package model;

import java.util.ArrayList;

public class Tab {
	private ArrayList<Bar> bars;

	public Tab() {
		bars = new ArrayList<>();
	}

	public void addBar(int numberOfBeats, int wholeNoteDuration, int tempo) throws Exception {
		bars.add(new Bar(numberOfBeats, wholeNoteDuration, tempo));
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
