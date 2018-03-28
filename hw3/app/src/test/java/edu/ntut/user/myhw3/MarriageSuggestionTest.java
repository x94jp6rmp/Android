package edu.ntut.user.myhw3;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MarriageSuggestionTest {
    private   MarriageSuggestion ms;

    @Before public void setUp(){
        ms = new MarriageSuggestion();
    }

    @After
    public void tearDown(){
        ms = null;
    }

    @Test
    public void testGetSuggestion() {
        assertEquals("建議：還不急", ms.getSuggestion("male", 1, 12));
        assertEquals("建議：開始找對象", ms.getSuggestion("male", 2, 5));
        assertEquals("建議：開始找對象", ms.getSuggestion("male", 3, 3));
        assertEquals("建議：趕快結婚", ms.getSuggestion("female", 1, 3));
        assertEquals("建議：開始找對象", ms.getSuggestion("female", 2, 5));
        assertEquals("建議：趕快結婚", ms.getSuggestion("female", 3, 8));
    }
}