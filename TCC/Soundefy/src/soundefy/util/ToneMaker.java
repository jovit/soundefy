package soundefy.util;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class ToneMaker {
	public static float SAMPLE_RATE = 8000f;

	public static void tone(int hz, int msecs) throws LineUnavailableException {
		tone(hz, msecs, 1.0);
	}

	public static void tone(double hz, int msecs)
			throws LineUnavailableException {
		tone((int) hz, msecs, 1.0);
	}

	//public static void tone(double hz, int msecs, double vol)
	//		throws LineUnavailableException {
	//	tone((int) hz, msecs, vol);
	//}

	public static void tone(double hz, int msecs, double vol)
			throws LineUnavailableException {
		byte[] buf = new byte[1];
		AudioFormat af = new AudioFormat(SAMPLE_RATE, // sampleRate
				8, // sampleSizeInBits
				1, // channels
				true, // signed
				false); // bigEndian
		SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
		sdl.open(af);
		sdl.start();
		for (int i = 1; i < msecs * 8 + 1; i++) {
			double angle = i / (SAMPLE_RATE / hz) * 2.0 * Math.PI;
			buf[0] = (byte) (Math.sin(angle) * 127.0 * (vol / (i / 80)));
			sdl.write(buf, 0, 1);
		}
		/*for (int i = 1; i < msecs * 8 + 1; i++) {
			double angle = i / (SAMPLE_RATE / hz) * 2.0 * Math.PI;
			buf[0] = (byte) (Math.sin(angle) * 127.0 * (vol / (i / 80)));
			sdl.write(buf, 0, 1);
		}*/
		sdl.drain();
		sdl.stop();
		sdl.close();
	}
}
