package junit_testing.part2_junit_challenges;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UtilitiesTest {

    //private Utilities utils;

    @Before
    public void setUp() throws Exception {
         //utils = new Utilities();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void everyNthChar() {
        fail("Yet to be implemented");

    }

    @Test
    public void removePairs() {
       // fail("Yet to be implemented");
        Utilities utils = new Utilities();
        assertEquals("ABCDEF",utils.removePairs("AABCDDEFF"));
    }
 @Test
    public void removePairs_example2() {
        Utilities utils = new Utilities();
        assertEquals("ABCABDEF",utils.removePairs("ABCCABDEEF"));
    }

    @Test
    public void converter() {
        fail("Yet to be implemented");
    }

    @Test
    public void nullIfOddLength() {
        fail("Yet to be implemented");
    }
}