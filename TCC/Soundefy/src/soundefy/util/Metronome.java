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
				long then = System.currentTimeMillis();
				ToneMaker.tone(440, 200);
				long after = System.currentTimeMillis();
				long elapsed = after - then;
				if((sleepTime*2-elapsed) > 0)
					Thread.sleep((sleepTime*2-elapsed));
			} catch (LineUnavailableException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
