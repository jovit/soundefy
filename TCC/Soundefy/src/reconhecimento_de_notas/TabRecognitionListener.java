package reconhecimento_de_notas;

import javax.sound.sampled.LineUnavailableException;

import model.Bar;
import model.Chord;
import model.Note;
import model.Tab;
import soundefy.util.ToneMaker;

public class TabRecognitionListener implements Runnable {
	private boolean listening ;
	private final Tab tab;
	@Override
	public void run(){
		PitchDetector p = new PitchDetector();
		new Thread(p).start();
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
							System.out.println("Faça um barulho");
						}
						
					}
				}
			}
		}
	}
	
	public TabRecognitionListener(Tab t){
		this.tab = t;
		listening = false;
	}
	
	public void play() {
		listening = true;
		new Thread(this).start();
	}
}
