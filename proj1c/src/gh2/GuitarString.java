package gh2;

import deque.ArrayDeque;
import deque.Deque;
// TODO: maybe more imports

//Note: This file will not compile until you complete the Deque implementations
public class GuitarString extends InstrumentString {
    private static final double DECAY = 0.996;
    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        super(frequency, DECAY);
    }
}
