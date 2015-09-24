package soundefy.util;

import javax.sound.midi.MidiUnavailableException;
import javax.sound.sampled.LineUnavailableException;

import soundefy.listener.NextNoteListener;
import soundefy.model.Bar;
import soundefy.model.Chord;
import soundefy.model.Note;
import soundefy.model.Tab;
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

		/*
		 * Score s = new Score(); Part p = new Part();
		 * p.setInstrument(Part.DISTORTED_GUITAR); Phrase ph = new Phrase(); for
		 * (Bar b : tab.getBars()) { p = new Part();
		 * p.setNumerator(b.getTimeSignature().getNumberOfBeats());
		 * p.setDenominator(b.getTimeSignature().getNumberOfBeats());
		 * p.setTempo(b.getTempo()); for (Chord c : b.getNotes()) { ph = new
		 * Phrase(); ArrayList<Note> notes = new ArrayList<Note>(); for (Note n
		 * : c.getNotes()) { if (n != null) { notes.add(n); } }
		 * 
		 * double noteDuration = c.getDuration(); if (notes.size() == 0) { Rest
		 * rest = new Rest(); rest.setDuration(c.getDuration());
		 * ph.addRest(rest); } else { int[] chord = new int[notes.size()]; for
		 * (int i = 0; i < notes.size(); i++) { Note current = notes.get(i);
		 * double freq = PitchDetector.notas[getNoteIndex( current.getString(),
		 * current.getFret())] .getFreqOk(); chord[i] =
		 * jm.music.data.Note.freqToMidiPitch(freq); } ph.addChord(chord,
		 * noteDuration);
		 * 
		 * Note current = notes.get(0); jm.music.data.Note note = new
		 * jm.music.data.Note(); double freq = PitchDetector.notas[getNoteIndex(
		 * current.getString(), current.getFret())] .getFreqOk();
		 * note.setPitch(jm.music.data.Note.freqToMidiPitch(freq));
		 * note.setDuration(noteDuration);
		 * ph.setInstrument(Phrase.DISTORTED_GUITAR); ph.add(note); }
		 * p.addPhrase(ph); } s.addPart(p); } Play.midi(s);
		 * 
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
		MidiHelper midi = null;
		try {
			midi = new MidiHelper();
		} catch (MidiUnavailableException e1) {
			e1.printStackTrace();
		}

		for (Bar b : tab.getBars()) {
			int tempo = b.getTempo();
			int wholeNoteDuration = b.getTimeSignature().getWholeNoteDuration();
			for (Chord c : b.getNotes()) {
				int noteDuration = (int) ((60000 / tempo) * wholeNoteDuration * c
						.getDuration());
				int freq = 0;
				for (Note n : c.getNotes()) {
					if (n != null) {
						freq = jm.music.data.Note
								.freqToMidiPitch(PitchDetector.notas[getNoteIndex(
										n.getString(), n.getFret())]
										.getFreqOk());
						try {
							listener.nextNote();
							midi.play(freq, 100, 1000);
						} catch (MidiUnavailableException
								| InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				try {
					Thread.sleep(noteDuration);
					midi.pause(freq, 100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		listener.tabFinished();
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
