package soundefy.reconhecimento_de_notas;

import soundefy.listener.NextNoteListener;
import soundefy.model.Bar;
import soundefy.model.Chord;
import soundefy.model.Note;
import soundefy.model.Tab;
import soundefy.util.Metronome;

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
		Metronome m = new Metronome(tab.getBar().getTempo());
		new Thread(p).start();
		new Thread(m).start();
		int previousTempo = 0;
		for (Bar b : tab.getBars()) {
			int tempo = b.getTempo();
			if(previousTempo != tempo){
				previousTempo  =tempo;
				m.stop();
				m = new Metronome(tempo);
				new Thread(m).start();
			}
			int wholeNoteDuration = b.getTimeSignature().getWholeNoteDuration();
			for (Chord c : b.getNotes()) {
				if (listener != null) {
					listener.nextNote();
				}
				double noteDuration = (60000 / tempo) * wholeNoteDuration
						* c.getDuration();
				try {
					Thread.sleep((int) Math.round(noteDuration));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
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
						pos += fret;
					
						Nota readNote = PitchDetector.notas[pos];
						Nota maisTocada = p.getNotaMaisTocada();
						if(maisTocada != null){
							if (readNote == maisTocada || PitchDetector.notas[pos+1] == maisTocada || PitchDetector.notas[pos-1] == maisTocada ){
								listener.hitNote();
							} else {
								listener.missNote();
							}
							System.out.println("Nota esperada : " + readNote.getNome());
							System.out.println("Nota tocada : " + maisTocada.getNome());
						} else {
							listener.missNote();
							System.out.println("sem som" );
							
						}				
					}
				}
				
			}
		}
		m.stop();
		p.stopListening();
		listener.tabFinished();
	}

}
