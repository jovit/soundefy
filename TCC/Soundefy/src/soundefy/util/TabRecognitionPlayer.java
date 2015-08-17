package soundefy.util;

import javax.sound.sampled.LineUnavailableException;

import listener.NextNoteListener;
import soundefy.model.Bar;
import soundefy.model.Chord;
import soundefy.model.Note;
import soundefy.model.Tab;
import soundefy.reconhecimento_de_notas.PitchDetector;

public class TabRecognitionPlayer implements Runnable {
	private NextNoteListener listener;
	private Tab tab;

	public void setNextNoteListener(NextNoteListener listener) {
		this.listener = listener;
	}

	@Override
	public void run() {
		for (Bar b : tab.getBars()) {
			int tempo = b.getTempo();
			int wholeNoteDuration = b.getTimeSignature().getWholeNoteDuration();
			for (Chord c : b.getNotes()) {
				double noteDuration = (60000 / tempo) * wholeNoteDuration
						* c.getDuration();
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
						new Thread(new Runnable() {
							@Override
							public void run() {
								tocarNota(notePos, noteDuration);
							}

						}).start();
						
						if (listener != null) {
							listener.nextNote();
						}
					}
				}
				try {
					Thread.sleep((int) Math.round(noteDuration));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
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
		new Thread(this).start();
	}

}
