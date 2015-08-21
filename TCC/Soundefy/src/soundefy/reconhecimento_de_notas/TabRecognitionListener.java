package soundefy.reconhecimento_de_notas;

import javax.sound.sampled.LineUnavailableException;

import soundefy.listener.NextNoteListener;
import soundefy.model.Bar;
import soundefy.model.Chord;
import soundefy.model.Note;
import soundefy.model.Tab;
import soundefy.reconhecimento_de_notas.PitchDetector;

public class TabRecognitionListener {
	private NextNoteListener listener;
	private Tab tab;

	public void setNextNoteListener(NextNoteListener listener) {
		this.listener = listener;
	}

	public TabRecognitionListener(Tab tab) {
		this.tab = tab;
	}

	public void play() {
		PitchDetector p = new PitchDetector();
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
						pos += fret;
						Nota readNote = PitchDetector.notas[pos];
						try {
							Thread.sleep((long)(noteDuration));
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
						}
						Nota maisTocada = p.getNotaMaisTocada();
						if(maisTocada != null){
							if (readNote == maisTocada){
								System.out.println("Acertou a nota leke");
							} else {
								System.out.println("Errou a nota leke");
							}
							System.out.println("Nota esperada : " + readNote.getNome());
							System.out.println("Nota tocada : " + maisTocada.getNome());
						} else {
							System.out.println("Fa�a um barulho");
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
		listener.tabFinished();
	}

}
