package soundefy.util;

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
		for (Bar b : tab.getBars()) {
			int tempo = b.getTempo();
			int wholeNoteDuration = b.getTimeSignature().getWholeNoteDuration();
			for (Chord c : b.getNotes()) {
				if (listener != null) {
					listener.nextNote();
				}
				double noteDuration = (60000 / tempo) * wholeNoteDuration
						* c.getDuration();
				for (Note n : c.getNotes()) {
					if (n != null) {
						int string = n.getString();
						int fret = n.getFret();
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
						final int notePos = pos + fret;
						new Thread(new Runnable() {
							@Override
							public void run() {
								tocarNota(notePos, noteDuration);
							}

						}).start();						
					}
				}
				try {
					Thread.sleep((int) Math.round(noteDuration));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		listener.tabFinished();
	}

}
