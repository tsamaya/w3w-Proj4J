package com.what3words.sample;
import com.what3words.What3WordsException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by arnaud on 29/07/17.
 */
public class W3WtoUTMKTest {
	private final static double DELTA = 0.0000001;

	@Test
	public void geocodeToUTMK() throws What3WordsException {
		W3WtoUTMK geocoder = W3WtoUTMK.getInstance();
		Coordinates coords = geocoder.geocodeUTMK("factor.dribble.purest");
		assertEquals("X", 954015.8361943123,  coords.getX(), DELTA);
		assertEquals("Y", 1947363.9696324933,  coords.getY(), DELTA);
	}
}
