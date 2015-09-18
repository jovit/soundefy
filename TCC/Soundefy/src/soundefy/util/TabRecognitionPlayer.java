package soundefy.util;

import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;

import jm.music.data.CPhrase;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Rest;
import jm.music.data.Score;
import jm.util.Play;
import soundefy.listener.NextNoteListener;
import soundefy.model.Bar;
import soundefy.model.Chord;
import soundefy.model.Note;
import soundefy.model.Tab;
import soundefy.reconhecimento_de_notas.Nota;
import soundefy.reconhecimento_de_notas.PitchDetector;

public class TabRecognitionPlayer {
	private NextNoteListener listener;
	private Tab tab;

	public void setNextNoteListener(NextNoteListener listener) {
		this.listener = listener;
	}

	public void tocarNota(int pos, double noteDuration) {
		double noteFrequency = PitchDetector.notas[pos].getFreqOk();
		try {
			ToneMaker.tone(noteFrequency, (int) Math.round(noteDuration), 1.0);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public TabRecognitionPlayer(Tab tab) {
		this.tab = tab;
	}

	public void play() {
		Score s = new Score();
<<<<<<< HEAD
=======
		Part p = new Part();
		p.setInstrument(Part.DISTORTED_GUITAR);
		Phrase ph = new Phrase();
>>>>>>> 46846ed9602dae4297a0c945aad88224554b578f
		for (Bar b : tab.getBars()) {
			p = new Part();
			p.setTempo(b.getTempo());
			for (Chord c : b.getNotes()) {
<<<<<<< HEAD
				if (listener != null) {
					listener.nextNote();
				}
				double noteDuration = c.getDuration();
				jm.music.data.Note notes[] = new jm.music.data.Note[6];
				for (int i = 0; i < 6; i++) {
					notes[i] = new jm.music.data.Note();
					notes[i].setPitch(jm.music.data.Note.REST);
				}
				int i = 0;
				for (Note n : c.getNotes()) {
					if (n != null) {
						int string = n.getString();
						int fret = n.getFret();
						int pos = 0;
						if (string == 1) {
							pos = 51;
						}

						if (string == 2) {
							pos = 46;
						}

						if (string == 3) {
							pos = 42;
						}

						if (string == 4) {
							pos = 37;
						}

						if (string == 5) {
							pos = 32;
						}

						if (string == 6) {
							pos = 27;
						}
						final int notePos = pos + fret;
						notes[i] = new jm.music.data.Note(
								jm.music.data.Note.freqToMidiPitch(PitchDetector.notas[notePos]
										.getFreqOk()), noteDuration);
						i++;
					}
				}

				CPhrase p = new CPhrase();
				p.addNoteList(notes);
				Part pa = new Part();
				pa.addPhrase(p);
				pa.setTempo(tempo);
				pa.setNumerator(4);
				pa.setDenominator(4);
				pa.setInstrument(Part.DISTORTED_GUITAR);
				s.addPart(pa);
=======
				ph = new Phrase();
				ArrayList<Note> notes = new ArrayList<Note>();
				for (Note n : c.getNotes()) {
					if (n != null) {
						notes.add(n);
					}
				}

				if (notes.size() == 0) {
					Rest rest = new Rest();
					rest.setDuration(c.getDuration());
					ph.addRest(rest);
				} else {
					int[] chord = new int[notes.size()];
					for (int i = 0; i < notes.size(); i++) {
						Note current = notes.get(i);
						double freq = PitchDetector.notas[getNoteIndex(
								current.getString(), current.getFret())]
								.getFreqOk();
						chord[i] = jm.music.data.Note.freqToMidiPitch(freq);
					}
					int wholeNoteDuration = b.getTimeSignature()
							.getWholeNoteDuration();
					double noteDuration = c.getDuration();
					ph.addChord(chord, noteDuration);
				}
				p.addPhrase(ph);
>>>>>>> 46846ed9602dae4297a0c945aad88224554b578f
			}
			s.addPart(p);
		}
		Play.midi(s);
		/*
		 * for (Bar b : tab.getBars()) { int tempo = b.getTempo(); int
		 * wholeNoteDuration = b.getTimeSignature().getWholeNoteDuration(); for
		 * (Chord c : b.getNotes()) { if (listener != null) {
		 * listener.nextNote(); } double noteDuration = (60000 / tempo) *
		 * wholeNoteDuration c.getDuration(); for (Note n : c.getNotes()) { if
		 * (n != null) { int string = n.getString(); int fret = n.getFret(); int
		 * pos = 0; if (string == 1) { pos = 40; }
		 * 
		 * if (string == 2) { pos = 35; }
		 * 
		 * if (string == 3) { pos = 31; }
		 * 
		 * if (string == 4) { pos = 26; }
		 * 
		 * if (string == 5) { pos = 21; }
		 * 
		 * if (string == 6) { pos = 16; } final int notePos = pos + fret; new
		 * Thread(new Runnable() {
		 * 
		 * @Override public void run() { tocarNota(notePos, noteDuration); }
		 * 
		 * }).start(); } } try { Thread.sleep((int) Math.round(noteDuration)); }
		 * catch (InterruptedException e) { e.printStackTrace(); } } }
		 */
		listener.tabFinished();
		Play.midi(s);
	}

	private int getNoteIndex(int string, int fret) {
		int pos = 0;
		if (string == 1) {
			pos = 40;
		}

		if (string == 2) {
			pos = 35;
		}

		if (string == 3) {
			pos = 31;
		}

		if (string == 4) {
			pos = 26;
		}

		if (string == 5) {
			pos = 21;
		}

		if (string == 6) {
			pos = 16;
		}
		return pos + fret;
	}

}
