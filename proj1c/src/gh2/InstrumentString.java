package gh2;

import deque.ArrayDeque;
import deque.Deque;

public class InstrumentString {
    private static final int SR = 44100;      // Sampling Rate
    private final double DECAY; // energy decay factor

    /* Buffer for storing sound data. */
    private Deque<Double> buffer;

    /* Create a harp string of the given frequency.  */
    public InstrumentString(double frequency, double decay) {
        DECAY = decay;
        buffer = new ArrayDeque<>();
        int capacity = (int) Math.round(SR / frequency);
        for (int i = 0; i < capacity; i++) {
            buffer.addLast(0.0);
        }
    }

    /* Pluck the harp string by replacing the buffer with white noise. */
    public void pluck() {
        //       Make sure that your random numbers are different from each
        //       other. This does not mean that you need to check that the numbers
        //       are different from each other. It means you should repeatedly call
        //       Math.random() - 0.5 to generate new random numbers for each array index.
        for (int i = 0; i < buffer.size(); i++) {
            buffer.removeFirst();
            buffer.addLast(Math.random() - 0.5);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        double val = getNewVal();
        addLast(val);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.get(0);
    }
    public double getNewVal() {
        double first = buffer.removeFirst();
        double second = buffer.get(0);
        return (first + second) / 2.0 * DECAY;
    }
    public void addLast(double val) {
        buffer.addLast(val);
    }
}
