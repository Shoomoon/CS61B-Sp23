package gh2;

import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class Instrument {
    private final List<InstrumentString> instrumentStrings;
    private final String keyboard;

    public Instrument(List<InstrumentString> customInstrumentStrings, String customKeyboard) {
        if (customInstrumentStrings.size() != customKeyboard.length()) {
            throw new IllegalArgumentException("Size of InstrumentStrings must match keyboard's size!");
        }
        this.instrumentStrings = customInstrumentStrings;
        this.keyboard = customKeyboard;
    }
    public void keyTyped(char key) {
        int i = keyboard.indexOf(key);
        if (i >= 0) {
            instrumentStrings.get(i).pluck();
        }
    }
    public double getSample() {
        double sample = 0;
        for (InstrumentString instrumentString: instrumentStrings) {
            sample += instrumentString.sample();
        }
        return sample;
    }

    public void ticAll() {
        for (InstrumentString instrumentString: instrumentStrings) {
            instrumentString.tic();
        }
    }
    public static void main(String[] args) {
        double freqCenterC = 440.0;
        // initialize guitar
        List<InstrumentString> guitarStrings = new ArrayList<>();
        String guitarKeyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        for (int i = 0; i < guitarKeyboard.length(); i++) {
            guitarStrings.add(new GuitarString(freqCenterC * Math.pow(2, (i - 24) / 12.0)));
        }
        Instrument guitar = new Instrument(guitarStrings, guitarKeyboard);

        // initialize harp
        List<InstrumentString> harpStrings = new ArrayList<>();
        String harpKeyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        for (int i = 0; i < harpKeyboard.length(); i++) {
            harpStrings.add(new HarpString(freqCenterC * Math.pow(2, (i - 24) / 12.0)));
        }
        Instrument harp = new Instrument(harpStrings, harpKeyboard);

        // initialize drum
        List<InstrumentString> drumStrings = new ArrayList<>();
        String drumKeyboard = "135";
        for (int i = 0; i < drumKeyboard.length(); i++) {
            drumStrings.add(new DrumString(freqCenterC * Math.pow(2, 2 * i / 12.0)));
        }
        Instrument drum = new Instrument(drumStrings, drumKeyboard);

        // select an instrument ans play
        Instrument curInstrument = harp;
        if (curInstrument.equals(guitar)) {
            System.out.println("Guitar keyboard: " + guitarKeyboard);
        } else if (curInstrument.equals(harp)) {
            System.out.println("Harp keyboard: " + harpKeyboard);
        } else {
            System.out.println("Drum keyboard: " + drumKeyboard);
        }
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                curInstrument.keyTyped(c);
            }
            double sample = curInstrument.getSample();
            StdAudio.play(sample);
            curInstrument.ticAll();
        }
    }
}
