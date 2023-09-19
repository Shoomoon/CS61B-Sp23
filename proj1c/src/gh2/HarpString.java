package gh2;

import deque.ArrayDeque;
import deque.Deque;

public class HarpString extends InstrumentString {
    private static final double DECAY = 0.998;
    /* Create a harp string of the given frequency.  */
    public HarpString(double frequency) {
        super(frequency * 2, DECAY);
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    @Override
    public void tic() {
        double val = super.getNewVal();
        super.addLast(-val);
    }
}
