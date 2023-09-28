package ngordnet.main;

import ngordnet.browser.NgordnetQuery;
import ngordnet.ngrams.NGramMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

class HistoryTextHandlerTest {
    static HistoryTextHandler shortHth;
    static HistoryTextHandler hth;
    static NGramMap smallNgm;
    static NGramMap ngm;
    @BeforeAll
    public static void init() {
        smallNgm = new NGramMap("./data/ngrams/very_short.csv",
                "./data/ngrams/total_counts.csv");
        shortHth = new HistoryTextHandler(smallNgm);

        ngm = new NGramMap("./data/ngrams/top_14377_words.csv",
                "./data/ngrams/total_counts.csv");
        hth = new HistoryTextHandler(ngm);
    }
    @Test
    public void handleTest() {
        int startYear = 2007;
        int endYear = 2008;
        String stringOfAirport = "airport: " + smallNgm.weightHistory("airport", startYear, endYear).toString();
        String stringOfRequest = "request: " + smallNgm.weightHistory("request", startYear, endYear).toString();
        System.out.println(smallNgm.weightHistory("airport"));
        System.out.println(smallNgm.weightHistory("request"));
        NgordnetQuery q = new NgordnetQuery(List.of("airport", "request"), startYear, endYear, 5);
        assertThat(shortHth.handle(q)).isEqualTo(stringOfAirport + "\n" + stringOfRequest);

        String catDogRelativePopulation = "cat: {2000=1.71568475416827E-5, 2001=1.6120939684412677E-5, 2002=1.61742010630623E-5, 2003=1.703155141714967E-5, 2004=1.7418408946715716E-5, 2005=1.8042211615010028E-5, 2006=1.8126126955841936E-5, 2007=1.9411504094739293E-5, 2008=1.9999492186117545E-5, 2009=2.1599428349729816E-5, 2010=2.1712564894218663E-5, 2011=2.4857238078766228E-5, 2012=2.4198586699546612E-5, 2013=2.3131865569578688E-5, 2014=2.5344693375481996E-5, 2015=2.5237182007765998E-5, 2016=2.3157514119191215E-5, 2017=2.482102172595473E-5, 2018=2.3556758130732888E-5, 2019=2.4581322086049953E-5}\n" +
                "dog: {2000=3.127517699525712E-5, 2001=2.99511426723737E-5, 2002=3.0283458650225453E-5, 2003=3.1470761877596034E-5, 2004=3.2890514515432536E-5, 2005=3.753038415155302E-5, 2006=3.74430614362125E-5, 2007=3.987077208249744E-5, 2008=4.267197824115907E-5, 2009=4.81026086549733E-5, 2010=5.30567576173992E-5, 2011=6.048536820577008E-5, 2012=5.179787485962082E-5, 2013=5.0225599367200654E-5, 2014=5.5575537540090384E-5, 2015=5.44261096781696E-5, 2016=4.805214145459557E-5, 2017=5.4171157785607686E-5, 2018=5.206751570646653E-5, 2019=5.5807040409297486E-5}";
        assertThat(hth.handle(new NgordnetQuery(List.of("cat", "dog"), 2000, 2020, 5))).isEqualTo(catDogRelativePopulation);
    }

}