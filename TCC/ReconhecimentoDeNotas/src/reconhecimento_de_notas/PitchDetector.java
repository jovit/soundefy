package reconhecimento_de_notas;

/** Copyright (C) 2009 by Aleksey Surkov.
 **
 ** Permission to use, copy, modify, and distribute this software and its
 ** documentation for any purpose and without fee is hereby granted, provided
 ** that the above copyright notice appear in all copies and that both that
 ** copyright notice and this permission notice appear in supporting
 ** documentation.  This software is provided "as is" without express or
 ** implied warranty.
 */
import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

public class PitchDetector implements Runnable {
	// Currently, only this combination of rate, encoding and channel mode
	// actually works.
	private ListaDeNotas listaDeNotas;
	Nota[] notas = {
	        new Nota("C1", 30.88, 34.65, 32.70),
	        new Nota("C#1", 32.70, 36.71, 34.65),
	        new Nota("D1", 34.65, 38.89, 36.71),
	        new Nota("D#1", 36.71, 41.20, 38.89),
	        new Nota("E1", 38.89, 43.65, 41.20),
	        new Nota("F1", 41.20, 46.25, 43.65),
	        new Nota("F#1", 43.65, 49.00, 46.25),
	        new Nota("G1", 46.25, 51.91, 49.00),
	        new Nota("G#1", 49.00, 55.00, 51.91),
	        new Nota("A1", 51.91, 58.27, 55.00),
	        new Nota("A#1", 55.00, 61.74, 58.27),
	        new Nota("B1", 58.27, 65.41, 61.74),
	        new Nota("C2", 61.74, 69.30, 65.41),
	        new Nota("C#2", 65.41, 73.42, 69.30),
	        new Nota("D2", 69.30, 77.78, 73.42),
	        new Nota("D#2", 73.42, 82.41, 77.78),
	        new Nota("E2", 77.78, 87.31, 82.41),
	        new Nota("F2", 82.41, 92.50, 87.31),
	        new Nota("F#2", 87.31, 98.00, 92.50),
	        new Nota("G2", 92.50, 103.83, 98.00),
	        new Nota("G#2", 98.00, 110.00, 103.83),
	        new Nota("A2", 103.83, 116.54, 110.00),
	        new Nota("A#2", 110.00, 123.47, 116.54),
	        new Nota("B2", 116.54, 130.81, 123.47),
	        new Nota("C3", 123.47, 138.59, 130.81),
	        new Nota("C#3", 130.81, 146.83, 138.59),
	        new Nota("D3", 138.59, 155.56, 146.83),
	        new Nota("D#3", 146.83, 164.81, 155.56),
	        new Nota("E3", 155.56, 174.61, 164.81),
	        new Nota("F3", 164.81, 185.00, 174.61),
	        new Nota("F#3", 174.61, 196.00, 185.00),
	        new Nota("G3", 185.00, 207.65, 196.00),
	        new Nota("G#3", 196.00, 220.00, 207.65),
	        new Nota("A3", 207.65, 233.08, 220.00),
	        new Nota("A#3", 220.00, 246.94, 233.08),
	        new Nota("B3", 233.08, 261.63, 246.94),
	        new Nota("C4", 246.94, 277.18, 261.63),
	        new Nota("C#4", 261.63, 293.66, 277.18),
	        new Nota("D4", 277.18, 311.13, 293.66),
	        new Nota("D#4", 293.66, 329.63, 311.13),
	        new Nota("E4", 311.13, 349.23, 329.63),
	        new Nota("F4", 329.63, 369.99, 349.23),
	        new Nota("F#4", 349.23, 392.00, 369.99),
	        new Nota("G4", 369.99, 415.30, 392.00),
	        new Nota("G#4", 392.00, 440.00, 415.30),
	        new Nota("A4", 415.30, 466.16, 440.00),
	        new Nota("A#4", 440.00, 493.88, 466.16),
	        new Nota("B4", 466.16, 523.25, 493.88),
	        new Nota("C5", 493.88, 554.37, 523.25),
	        new Nota("C#5", 523.25, 587.33, 554.37),
	        new Nota("D5", 554.37, 622.25, 587.33),
	        new Nota("D#5", 587.33, 629.25, 622.25),
	        new Nota("E5", 622.25, 698.46, 629.25),
	        new Nota("F5", 629.25, 739.99, 698.46),
	        new Nota("F#5", 698.46, 783.99, 739.99),
	        new Nota("G5", 739.99, 830.61, 783.99),
	        new Nota("G#5", 783.99, 880.00, 830.61),
	        new Nota("A5", 830.61, 932.33, 880.00),
	        new Nota("A#5", 880.00, 987.77, 932.33),
	        new Nota("B5", 932.33, 1046.50, 987.77),};
	
	private final static int RATE = 8000;
	private final static int CHANNEL_MODE = 1;
	private final static int ENCODING = 16;

	private final static int BUFFER_SIZE_IN_MS = 3000;
	private final static int CHUNK_SIZE_IN_SAMPLES_POW2 = 11;
	private final static int CHUNK_SIZE_IN_SAMPLES = 2048; // = 2 ^
															// CHUNK_SIZE_IN_SAMPLES_POW2
	private final static int CHUNK_SIZE_IN_MS = 1000 * CHUNK_SIZE_IN_SAMPLES
			/ RATE;
	private final static int BUFFER_SIZE_IN_BYTES = RATE * BUFFER_SIZE_IN_MS
			/ 1000 * 2;
	private final static int CHUNK_SIZE_IN_BYTES = RATE * CHUNK_SIZE_IN_MS
			/ 1000 * 2;
	private final static int MIN_FREQUENCY = 50; // HZ
	private final static int MAX_FREQUENCY = 1000; // HZ - it's for guitar,
													// should be enough
	private final static int DRAW_FREQUENCY_STEP = 10;
	private final static int MIN_AMPLITUDE = 100;

	public PitchDetector() {
			listaDeNotas = new ListaDeNotas();
	}

	public void run() {
		try {
			AudioFormat audioFormat = new AudioFormat(8000.0F, ENCODING,
					CHANNEL_MODE, true, false);
			DataLine.Info dataLineInfo = new DataLine.Info(
					TargetDataLine.class, audioFormat);

			recorder_ = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
			recorder_.open(audioFormat);
			byte[] audio_data = new byte[BUFFER_SIZE_IN_BYTES / 2];
			double[] x_r = new double[CHUNK_SIZE_IN_SAMPLES];
			double[] x_i = new double[CHUNK_SIZE_IN_SAMPLES];
			FFT fft = new FFT(CHUNK_SIZE_IN_SAMPLES_POW2);
			final int min_frequency_fft = Math.round(MIN_FREQUENCY
					* CHUNK_SIZE_IN_SAMPLES / RATE);
			final int max_frequency_fft = Math.round(MAX_FREQUENCY
					* CHUNK_SIZE_IN_SAMPLES / RATE);
			while (!Thread.interrupted()) {
				recorder_.start();
				
				recorder_.read(audio_data, 0, CHUNK_SIZE_IN_BYTES / 2);
				recorder_.stop();
				for (int i = 0; i < CHUNK_SIZE_IN_SAMPLES; i++) {
					x_r[i] = audio_data[i];
					x_i[i] = 0;
				}
				fft.doFFT(x_r, x_i, false);
				double best_frequency = min_frequency_fft;
				double best_amplitude = 0;
				HashMap<Double, Double> frequencies = new HashMap<Double, Double>();
				final double draw_frequency_step = 1.0 * RATE
						/ CHUNK_SIZE_IN_SAMPLES;
				for (int i = min_frequency_fft; i <= max_frequency_fft; i++) {
					final double current_frequency = i * 1.0 * RATE
							/ CHUNK_SIZE_IN_SAMPLES;
					final double draw_frequency = Math
							.round((current_frequency - MIN_FREQUENCY)
									/ DRAW_FREQUENCY_STEP)
							* DRAW_FREQUENCY_STEP + MIN_FREQUENCY;
					final double current_amplitude = Math.pow(x_r[i], 2)
							+ Math.pow(x_i[i], 2);
					Double current_sum_for_this_slot = frequencies
							.get(draw_frequency);
					if (current_sum_for_this_slot == null)
						current_sum_for_this_slot = 0.0;
					frequencies.put(draw_frequency,
							Math.pow(current_amplitude, 0.5)
									/ draw_frequency_step
									+ current_sum_for_this_slot);
					if (current_amplitude > best_amplitude) {
						best_frequency = current_frequency;
						best_amplitude = current_amplitude;
					}
				}
				if (best_amplitude < MIN_AMPLITUDE){
					//retval = "<make a sound>";
				}else{
					
					for(int i = 0;i < notas.length;i++)
					{
						if (best_frequency <= notas[i].getFreqMax() && best_frequency >= notas[i].getFreqMin())
						{
							listaDeNotas.inserirNota(notas[i]);
							break;
						}
					}
				}
				//System.out.println(retval);
			}
		} catch (Exception ex) {
			System.out.println("Can't initialize recorder");
		}
	}

	public Nota getNotaMaisTocada()
	{
		Nota n =  listaDeNotas.notaMaisTocada();
		listaDeNotas = null;
		listaDeNotas = new ListaDeNotas();
		return n;
	}
	private TargetDataLine recorder_;
}
