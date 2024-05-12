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
       // fail("Yet to be implemented");
        Utilities utils = new Utilities();
        char[] output = utils.everyNthChar(new char[] {'h','e','l','l','o'}, 2);
        //assertEquals(new char[]{'e','l'}, output);
        // above fails because the 2 arrays are diff & assertEquals only check to see if the arrays are the exact same instance
        assertArrayEquals(new char[]{'e','l'}, output);

    }

    @Test
    public void everyNthChar_n_is_greater_than_array_length() {
        // fail("Yet to be implemented");
        Utilities utils = new Utilities();
        char[] output = utils.everyNthChar(new char[] {'h','e','l','l','o'}, 9);
        //assertEquals(new char[]{'e','l'}, output);
        // above fails because the 2 arrays are diff & assertEquals only check to see if the arrays are the exact same instance
        assertArrayEquals(new char[]{'h','e','l','l','o'}, output);

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
    public void removePairs_test_values_less_than_2() {
        Utilities utils = new Utilities();
        assertEquals("A",utils.removePairs("A"));
    }

    @Test
    public void removePairs_test_null_values() {
        Utilities utils = new Utilities();
        assertNull("Did not get null returned when arg passed was null",utils.removePairs(null));
    }

    @Test
    public void removePairs_test_empty_string() {
        Utilities utils = new Utilities();
        assertEquals("",utils.removePairs(""));
    }

    @Test
    public void converter() {
       /// fail("Yet to be implemented");
        Utilities utils = new Utilities();
        assertEquals(300,utils.converter(10,5));
    }

    @Test
    public void nullIfOddLength() {
        //fail("Yet to be implemented");
        Utilities utils = new Utilities();
        assertNotNull(utils.nullIfOddLength("even"));
    }

    @Test
    public void nullIfOddLength_odd() {
        //fail("Yet to be implemented");
        Utilities utils = new Utilities();
        assertNull(utils.nullIfOddLength("odd"));
    }
}