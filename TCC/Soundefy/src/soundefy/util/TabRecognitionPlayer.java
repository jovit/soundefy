package soundefy.util;

import javax.sound.sampled.LineUnavailableException;

import reconhecimento_de_notas.PitchDetector;
import model.Bar;
import model.Chord;
import model.Note;
import model.Tab;

public class TabRecognitionPlayer implements Runnable {

	private Tab tab;

	@Override
	public void run() {
		for (Bar b : tab.getBars()) {
			int tempo = b.getTempo();
			int wholeNoteDuration = b.getTimeSignature().getWholeNoteDuration();
			for (Chord c : b.getNotes()) {
				double noteDuration = tempo * wholeNoteDuration
						* c.getDuration();
				for (Note n : c.getNotes()) {
					if (n != null) {
						int string = n.getString();
						int fret = n.getFret();
						int pos = 0;
						switch (string) {
						case 6: {
							pos = 28;
							break;
						}

						case 5: {
							pos = 34;
							break;
						}

						case 4: {
							pos = 39;
							break;
						}

						case 3: {
							pos = 44;
							break;
						}

						case 2: {
							pos = 48;
							break;
						}

						case 1: {
							pos = 53;
							break;
						}
						}
						String noteName = PitchDetector.notas[pos].getNome();
						double noteFrequency = PitchDetector.notas[pos]
								.getFreqOk();
						try {
							ToneMaker.tone(noteFrequency,
									(int) Math.round(noteDuration), 1.0);
						} catch (LineUnavailableException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	public TabRecognitionPlayer(Tab tab) {
		this.tab = tab;
	}

	public void play() {
		new Thread(this).run();
	}

}
