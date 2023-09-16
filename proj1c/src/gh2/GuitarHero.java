package gh2;

import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class GuitarHero {
    private final List<GuitarString> guitarStrings = new ArrayList<>();
    private final String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    public GuitarHero() {
        double freqCenterC = 440.0;
        for (int i = 0; i < keyboard.length(); i++) {
            double freq = freqCenterC * Math.pow(2, (i - 24) / 12.0);
            guitarStrings.add(new GuitarString(freq));
        }
    }
    public void keyTyped(char key) {
        int i = keyboard.indexOf(key);
        if (i >= 0) {
            guitarStrings.get(i).pluck();
        }
    }
    public double getSample() {
        double sample = 0;
        for (GuitarString guitarString: guitarStrings) {
            sample += guitarString.sample();
        }
        return sample;
    }

    public void ticAll() {
        for (GuitarString guitarString: guitarStrings) {
            guitarString.tic();
        }
    }
    public static void main(String[] args) {
        GuitarHero guitar = new GuitarHero();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                guitar.keyTyped(c);
            }
            double sample = guitar.getSample();
            StdAudio.play(sample);
            guitar.ticAll();
        }
    }
}
