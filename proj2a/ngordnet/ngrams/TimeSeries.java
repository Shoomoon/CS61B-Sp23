package ngordnet.ngrams;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    private static final int MIN_YEAR = 1400;
    private static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        if (ts != null) {
            int start = Math.max(startYear, ts.firstKey());
            int end = Math.min(endYear, ts.lastKey());
            for (int i = start; i <= end; i++) {
                if (ts.containsKey(i)) {
                    this.put(i, ts.get(i));
                }
            }
        }
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        return new ArrayList<>(this.keySet());
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        List<Double> res = new ArrayList<>();
        for (int i: this.years()) {
            res.add(this.get(i));
        }
        return res;
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries res = new TimeSeries();
        for (int year: this.keySet()) {
            res.put(year, this.get(year));
        }
        if (ts != null) {
            for (int year : ts.keySet()) {
                res.put(year, this.getOrDefault(year, 0.0) + ts.get(year));
            }
        }
        return res;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries res = new TimeSeries();
        if (ts == null) {
            if (!this.isEmpty()) {
                throw new IllegalArgumentException("Years are missing!");
            }
            return res;
        }
        for (int year: this.keySet()) {
            if (!ts.containsKey(year)) {
                throw new IllegalArgumentException(String.format("Year %d is missing!", year));
            }
            if (ts.get(year) == 0) {
                throw new ArithmeticException(String.format("The value of year %d can not be 0.0!", year));
            }
            res.put(year, this.get(year) / ts.get(year));
        }
        return res;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.

    /**
     * sum up of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from TS to the data from this TimeSeries. Modify this TimeSeries
     * directly.
     */
    public void roughPlus(TimeSeries ts) {
        if (ts != null) {
            for (int year: ts.keySet()) {
                this.put(year, this.getOrDefault(year, 0.0) + ts.get(year));
            }
        }
    }
}
