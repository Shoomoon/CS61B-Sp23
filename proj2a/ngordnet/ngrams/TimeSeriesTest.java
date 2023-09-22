package ngordnet.ngrams;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TimeSeriesTest {
    TimeSeries catPopulation;
    TimeSeries dogPopulation;
    TimeSeries dog1;
    TimeSeries dog2;
    TimeSeries dogNull;
    @BeforeEach
    public void init() {
        catPopulation = new TimeSeries();
        catPopulation.put(1991, 1.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        dog1 = new TimeSeries();
        dog1.put(1991, 0.0);
        dog1.put(1992, 400.0);
        dog1.put(1994, 400.0);

        dog2 = new TimeSeries();
        dog2.put(1991, 1.0);
        dog2.put(1992, 400.0);
        dog2.put(1993, 1.0);
        dog2.put(1994, 400.0);
    }
    @Test
    public void testForConstructor() {
        TimeSeries ts0 = new TimeSeries(catPopulation, 1992, 1994);
        assertThat(ts0.years()).isEqualTo(List.of(1992, 1994));
        for (int year: ts0.years()) {
            assertThat(ts0.get(year)).isWithin(1E-10).of(catPopulation.get(year));
        }

        TimeSeries ts1 = new TimeSeries(catPopulation, 1990, 1994);
        assertThat(ts1.years()).isEqualTo(List.of(1991, 1992, 1994));
        for (int year: ts1.years()) {
            assertThat(ts1.get(year)).isWithin(1E-10).of(catPopulation.get(year));
        }

        TimeSeries ts2 = new TimeSeries(catPopulation, 1988, 1990);
        assertThat(ts2.years()).isEmpty();
    }
    @Test
    public void testFromSpec() {
        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 1,
        //           1992: 100
        //           1994: 600
        //           1995: 500

        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1992, 1994, 1995));

        assertThat(totalPopulation.years()).isEqualTo(expectedYears);

        List<Double> expectedTotal = new ArrayList<>
                (Arrays.asList(1.0, 100.0, 600.0, 500.0));

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(totalPopulation.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }
    }

    @Test
    void years() {
        List<Integer> catYearsExpected = List.of(1991, 1992, 1994);
        List<Integer> dogYearsExpected = List.of(1994, 1995);
        assertThat(catPopulation.years()).isEqualTo(catYearsExpected);
        assertThat(dogPopulation.years()).isEqualTo(dogYearsExpected);
    }

    @Test
    void data() {
        List<Double> catData = catPopulation.data();
        List<Double> catDataExpected = List.of(1.0, 100.0, 200.0);
        List<Double> dogData = dogPopulation.data();
        List<Double> dogDataExpected = List.of(400.0, 500.0);
        assertThat(catDataExpected.size()).isEqualTo(catData.size());
        for (int i = 0; i < catDataExpected.size(); i++) {
            assertThat(catData.get(i)).isWithin(1e-10).of(catDataExpected.get(i));
        }
        assertThat(dogDataExpected.size()).isEqualTo(dogData.size());
        for (int i = 0; i < dogDataExpected.size(); i++) {
            assertThat(dogData.get(i)).isWithin(1e-10).of(dogDataExpected.get(i));
        }
    }

    @Test
    void plus() {
        List<Integer> yearsExcepted = List.of(1991, 1992, 1994, 1995);
        List<Double> valuesExcepted = List.of(1.0, 100.0, 600.0, 500.0);
        TimeSeries total = catPopulation.plus(dogPopulation);
        List<Integer> totalYears = total.years();
        List<Double> totalValues = total.data();
        assertThat(totalYears).isEqualTo(yearsExcepted);
        for (int i = 0; i < yearsExcepted.size(); i++) {
            int year = yearsExcepted.get(i);
            assertThat(valuesExcepted.get(i)).isWithin(1E-10).of(total.get(year));
            assertThat(valuesExcepted.get(i)).isWithin(1E-10).of(totalValues.get(i));
        }

        TimeSeries catPlusDogNull = catPopulation.plus(dogNull);
        assertThat(catPlusDogNull.years()).isEqualTo(catPopulation.years());
        for (int year: catPlusDogNull.years()) {
            assertThat(catPlusDogNull.get(year)).isWithin(1E-10).of(catPopulation.get(year));
        }
    }

    @Test
    void dividedBy() {
        try {
            TimeSeries divideCatByDog = catPopulation.dividedBy(dogPopulation);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Year 1991 is missing!", e.getMessage());
        }

        try {
            TimeSeries divideCatByDog1 = catPopulation.dividedBy(dog1);
            fail();
        } catch (ArithmeticException e) {
            assertEquals("The value of year 1991 can not be 0.0!", e.getMessage());
        }

        try {
            TimeSeries divideCatByDogNull = catPopulation.dividedBy(dogNull);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Years are missing!", e.getMessage());
        }
        assertThat(0.0).isEqualTo(0);

        TimeSeries divideCayByDog2 = catPopulation.dividedBy(dog2);
        List<Double> divideCatByDog2ValueExcepted = List.of(1.0, 0.25, 0.5);
        List<Integer> yearsExcepted = List.of(1991, 1992, 1994);
        // year 1993 ignored
        assertThat(yearsExcepted).isEqualTo(divideCayByDog2.years());

        for (int i = 0; i < yearsExcepted.size(); i++) {
            int year = yearsExcepted.get(i);
            assertThat(divideCatByDog2ValueExcepted.get(i)).isWithin(divideCayByDog2.get(year));
        }
    }
}