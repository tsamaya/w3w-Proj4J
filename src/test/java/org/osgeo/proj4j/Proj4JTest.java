package org.osgeo.proj4j;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by arnaud on 29/07/17.
 */
public class Proj4JTest {
	private final static double DELTA = 0.0000001;

	@Test
	public void testWGS84toRGF93() {
		CRSFactory csFactory = new CRSFactory();
		CoordinateReferenceSystem epsg4326 = csFactory.createFromName("EPSG:4326");
		CoordinateReferenceSystem epsg2154 = csFactory.createFromName("EPSG:2154");

		CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
		CoordinateTransform trans = ctFactory.createTransform(epsg4326, epsg2154);

		ProjCoordinate latlng = new ProjCoordinate(-4.104408, 47.87124);
		ProjCoordinate xy = new ProjCoordinate();


		xy = trans.transform(latlng, xy);

		assertEquals("X", 169585.18686671508,  xy.x, DELTA);
		assertEquals("Y", 6776193.512560901,  xy.y, DELTA);
	}

	@Test
	public void testUTMK() {
		String utmkParams = "+proj=tmerc +lat_0=38 +lon_0=127.5 +k=0.9996 +x_0=1000000 +y_0=2000000 +ellps=GRS80 +units=m +no_defs";
		CRSFactory csFactory = new CRSFactory();
		CoordinateReferenceSystem epsg4326 = csFactory.createFromName("EPSG:4326");
		CoordinateReferenceSystem utmK = csFactory.createFromParameters("UTMK", utmkParams);

		CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
		CoordinateTransform trans = ctFactory.createTransform(epsg4326, utmK);
		ProjCoordinate latlng = new ProjCoordinate(126.979591, 37.524431);
		ProjCoordinate xy = new ProjCoordinate();


		xy = trans.transform(latlng, xy);

		assertEquals("X", 954015.8361943123,  xy.x, DELTA);
		assertEquals("Y", 1947363.9696324933,  xy.y, DELTA);


	}
}
