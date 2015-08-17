package soundefy.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class Tab {
	private ArrayList<Bar> bars;

	public Tab() {
		bars = new ArrayList<>();
	}

	public void addBar(int numberOfBeats, int wholeNoteDuration, int tempo)
			throws Exception {
		bars.add(new Bar(numberOfBeats, wholeNoteDuration, tempo));
	}

	public void addChord(Note note1, Note note2, Note note3, Note note4,
			Note note5, Note note6, double duration) throws Exception {
		Note[] notes = new Note[6];
		notes[0] = note1;
		notes[1] = note2;
		notes[2] = note3;
		notes[3] = note4;
		notes[4] = note5;
		notes[5] = note6;

		bars.get(bars.size() - 1).addChord(new Chord(notes, duration));
	}

	public void removerBar() {
		bars.remove(bars.size() - 1);
	}

	public Bar getBar() {
		return bars.get(bars.size() - 1);
	}

	public ArrayList<Bar> getBars() {
		return bars;
	}

	public void saveFile(String arqName) throws Exception {
		if (arqName != null && arqName != "") {
			DataOutputStream out = new DataOutputStream(new FileOutputStream(
					arqName));
			for (Bar b : bars) {
				int tempo = b.getTempo();
				int numberOfBeats = b.getTimeSignature().getNumberOfBeats();
				int wholeNoteDuration = b.getTimeSignature()
						.getWholeNoteDuration();
				out.writeInt(tempo);
				out.writeInt(numberOfBeats);
				out.writeInt(wholeNoteDuration);
				for (Chord c : b.getNotes()) {
					double duration = c.getDuration();
					out.writeDouble(duration);
					for (Note n : c.getNotes()) {
						if (n != null) {
							int string = n.getString();
							int fret = n.getFret();
							out.writeInt(string);
							out.writeInt(fret);
						} else {
							out.writeInt(-1);
						}
					}
				}
			}
		} else {
			throw new Exception("Invalid file name!");
		}
	}

	public Tab readFile(String arqName) throws Exception {
		if (arqName != null && arqName != "") {
			Tab tab = new Tab();
			try {
				DataInputStream in = new DataInputStream(new FileInputStream(
						arqName));
				try {
					
					for (;;) {
						int tempo = in.readInt();
						int numberOfBeats = in.readInt();
						int wholeNoteDuration = in.readInt();
						tab.addBar(numberOfBeats, wholeNoteDuration, tempo);
						int barCompletion = 0;
						for (;;) {
							if (barCompletion == numberOfBeats) {
								break;
							}

							double duration = in.readDouble();
							Note notes[] = new Note[6];
							for (int i = 0; i < 5; i++) {
								int string = in.readInt();
								int fret = in.readInt();
								notes[i] = new Note(string, fret);
							}
							tab.getBar().addChord(new Chord(notes, duration));
							barCompletion += duration * wholeNoteDuration;
						}
					}
				} catch (Exception ex) {
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return tab;
		} else {
			throw new Exception("Invalid file name!");
		}
	}
}
