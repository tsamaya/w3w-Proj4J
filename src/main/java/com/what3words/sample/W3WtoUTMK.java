package com.what3words.sample;

import com.what3words.LatLng;
import com.what3words.What3WordsException;
import org.osgeo.proj4j.CRSFactory;
import org.osgeo.proj4j.CoordinateReferenceSystem;
import org.osgeo.proj4j.CoordinateTransform;
import org.osgeo.proj4j.CoordinateTransformFactory;
import org.osgeo.proj4j.ProjCoordinate;

/**
 * Created by arnaud on 29/07/17.
 */
public class W3WtoUTMK {
	private static W3WtoUTMK instance;

	private com.what3words.Geocoder geocoder;
	private String utmkParams = "+proj=tmerc +lat_0=38 +lon_0=127.5 +k=0.9996 +x_0=1000000 +y_0=2000000 +ellps=GRS80 +units=m +no_defs";
	private CoordinateTransform transform;

	private W3WtoUTMK() {
		geocoder = new com.what3words.Geocoder();
		CRSFactory csFactory = new CRSFactory();
		CoordinateReferenceSystem epsg4326 = csFactory.createFromName("EPSG:4326");
		CoordinateReferenceSystem utmK = csFactory.createFromParameters("UTMK", utmkParams);

		CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
		transform = ctFactory.createTransform(epsg4326, utmK);
	}

	public static W3WtoUTMK getInstance() {
		if (instance == null) {
			instance = new W3WtoUTMK();
		}
		return instance;
	}

	public Coordinates geocodeUTMK(String threeWordAddress) throws What3WordsException {
		LatLng latlng = geocoder.geocode(threeWordAddress);
		ProjCoordinate coordinatesWGS84 = new ProjCoordinate(latlng.getLongitude(), latlng.getLatitude());
		ProjCoordinate coordinatesUTMK = new ProjCoordinate();
		coordinatesUTMK = transform.transform(coordinatesWGS84, coordinatesUTMK);
		Coordinates coords = new Coordinates(coordinatesUTMK.x, coordinatesUTMK.y);
		return coords;
	}

}
