package soundefy.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class Tab {
	private ArrayList<Bar> bars;

	public Tab() {
		bars = new ArrayList<>();
	}
	
	public int getNumberOfNotes(){
		int numberOfNotes = 0;
		if(bars != null){
			for(Bar b:bars){
				numberOfNotes += b.getNotes().size();
			}	
		}
		
		return numberOfNotes;
	}

	public void addBar(int numberOfBeats, int wholeNoteDuration, int tempo)
			throws Exception {
		bars.add(new Bar(numberOfBeats, wholeNoteDuration, tempo));
	}

	public void setChord(Note note1, Note note2, Note note3, Note note4,
			Note note5, Note note6) throws Exception {
		double duration = getBar().getNotes()
				.get(getBar().getNotes().size() - 1).getDuration();
		getBar().removeChord();
		addChord(note1, note2, note3, note4, note5, note6, duration);
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
		if (bars.size() > 0)
			bars.remove(bars.size() - 1);
	}

	public Bar getBar() {
		if (bars.size() > 0) {
			return bars.get(bars.size() - 1);
		} else {
			return null;
		}
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
							out.writeInt(-1);
						}
					}
				}
			}
			out.close();
		} else {
			throw new Exception("Invalid file name!");
		}
	}

	public static Tab readFile(String arqName) throws Exception {
		if (arqName != null && arqName != "") {
			Tab tab = new Tab();
			DataInputStream in = null;
			try {
				in = new DataInputStream(new FileInputStream(arqName));
				try {
					for (;;) {
						int tempo = in.readInt();
						int numberOfBeats = in.readInt();
						int wholeNoteDuration = in.readInt();
						tab.addBar(numberOfBeats, wholeNoteDuration, tempo);
						double barCompletion = 0;
						for (;;) {
							if (barCompletion == numberOfBeats) {
								break;
							}

							double duration = in.readDouble();
							Note notes[] = new Note[6];
							for (int i = 0; i < 6; i++) {
								int string = in.readInt();
								int fret = in.readInt();
								if (string > 0 && fret > -1) {
									notes[i] = new Note(string, fret);
								} else {
									notes[i] = null;
								}
							}
							tab.getBar().addChord(new Chord(notes, duration));
							barCompletion += duration * wholeNoteDuration;
						}
					}
				} catch (Exception ex) {
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				in.close();
			}

			return tab;
		} else {
			throw new Exception("Invalid file name!");
		}
	}
}
