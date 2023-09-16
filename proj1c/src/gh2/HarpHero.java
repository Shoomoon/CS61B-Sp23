package gh2;

import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class HarpHero {
    private final List<HarpString> harpStrings = new ArrayList<>();
    private final String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    public HarpHero() {
        double freqCenterC = 440.0;
        for (int i = 0; i < keyboard.length(); i++) {
            double freq = freqCenterC * Math.pow(2, (i - 24) / 12.0);
            harpStrings.add(new HarpString(freq));
        }
    }
    public void keyTyped(char key) {
        int i = keyboard.indexOf(key);
        if (i >= 0) {
            harpStrings.get(i).pluck();
        }
    }
    public double getSample() {
        double sample = 0;
        for (HarpString harp: harpStrings) {
            sample += harp.sample();
        }
        return sample;
    }

    public void ticAll() {
        for (HarpString harp: harpStrings) {
            harp.tic();
        }
    }
    public static void main(String[] args) {
        HarpHero harp = new HarpHero();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                harp.keyTyped(c);
            }
            double sample = harp.getSample();
            StdAudio.play(sample);
            harp.ticAll();
        }
    }
}
