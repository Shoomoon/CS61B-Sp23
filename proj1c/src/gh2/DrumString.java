package gh2;

public class DrumString extends InstrumentString {

    private static final double DECAY = 1.0;

    public DrumString(double frequency) {
        super(frequency, DECAY);
    }
    @Override
    public void tic() {
        double val = super.getNewVal();
        if (Math.random() < 0.5) {
            val = -val;
        }
        super.addLast(val);
    }

}
