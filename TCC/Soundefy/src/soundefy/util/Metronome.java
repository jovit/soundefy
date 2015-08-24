package soundefy.util;

import javax.sound.sampled.LineUnavailableException;

public class Metronome implements Runnable{
	
	private boolean playing;
	private long sleepTime;
	
	public Metronome(int tempo){
		sleepTime = 60000/tempo;
		playing = true;
	}
	
	public void stop(){
		playing = false;
	}
	
	@Override
	public void run() {
		while (playing){
			try {
				ToneMaker.tone(440, 200);
				Thread.sleep((sleepTime*2-270));
			} catch (LineUnavailableException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
