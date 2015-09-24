package soundefy.util;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Instrument;

public class MidiHelper {
	Synthesizer synth;
	boolean playing = false;
	MidiChannel[] midiChannel;

	public MidiHelper() throws MidiUnavailableException {
		synth = MidiSystem.getSynthesizer();
		long startTime = System.nanoTime();
		synth.open();
		long estimatedTime = System.nanoTime() - startTime;
		midiChannel = synth.getChannels();
		Instrument[] instruments = synth.getDefaultSoundbank().getInstruments();
		boolean successLoadingInstrument = synth
				.loadInstrument(instruments[30]);
midiChannel[0].programChange(30);
	}

	public void play(int midiFreq, int velocity, int noteDuration)
			throws MidiUnavailableException, InterruptedException {
			midiChannel[0].noteOn(midiFreq, velocity);		playing = true;
	}

	public void pause(int midiFreq, int velocity) {
		if (playing) {
			midiChannel[0].noteOff(midiFreq, velocity);
		}
	}

}
