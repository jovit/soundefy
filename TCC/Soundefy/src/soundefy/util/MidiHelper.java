package soundefy.util;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Instrument;

public class MidiHelper {
	public static void play(int midiFreq, int velocity) throws MidiUnavailableException{
		Synthesizer synth = MidiSystem.getSynthesizer();
		long startTime = System.nanoTime();
		synth.open();
		long estimatedTime = System.nanoTime() - startTime;
		
		MidiChannel[] midiChannel = synth.getChannels();
		Instrument[] instruments = synth.getDefaultSoundbank().getInstruments();
		boolean successLoadingInstrument = synth.loadInstrument(instruments[1]);
		
		midiChannel[0].noteOn(midiFreq, velocity);
	}
}
