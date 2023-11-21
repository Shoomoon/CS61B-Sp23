package ngordnet.main;

import org.junit.Test;

import java.util.Arrays;

import static com.google.common.truth.Truth.assertThat;

public class SplitTest {
    @Test
    public void rexTest() {
        String s = "a, b,c";
        String rex = ", ?";
        String[] splitRes = s.split(rex);
        assertThat(s.split(rex).length).isEqualTo(3);
        System.out.println(splitRes[0]);
        System.out.println(splitRes[1]);
        System.out.println(splitRes[2]);


    }
}
