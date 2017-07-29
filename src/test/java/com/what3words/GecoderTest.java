package com.what3words;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by arnaud on 29/07/17.
 */
public class GecoderTest {

	private final static double DELTA = 0.0000001;

	@Test
	public void geocodeIndexHomeRfat() throws What3WordsException {
		ThreeWordAddress addr = new ThreeWordAddress("index.home.raft");
		Geocoder geocoder = new Geocoder();
		LatLng coords = geocoder.geocode(addr);
		assertEquals("Latitude ", 51.521251,  coords.getLatitude(), DELTA);
		assertEquals("Longitude ", -0.203586,  coords.getLongitude(), DELTA);

	}
}
