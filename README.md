# w3w-Proj4J

[![Build Status](https://travis-ci.org/tsamaya/w3w-Proj4J.svg?branch=master)](https://travis-ci.org/tsamaya/w3w-Proj4J)


This repository show how to use [what3words](https://what3words.com) [API](https://what3words.com/api) and project coordinates into a specific coordinate system with [Proj4J](https://trac.osgeo.org/proj4j/).

This sample projects the coordinates from WGS84 into [UTM-K](http://spatialreference.org/ref/sr-org/7308/) .

## prerequisite
- java 1.8
- optional : gradle 3. _this repository contains an embedded gradle wrapper_
    
## usage

  - clone this repo

  - then

    `$ cd /path/to/repo`

  - test
    - if you have gradle
    
            `$ gradle check`
    
    - without gradle, using the embedded gradle wrapper
    
            `$ gradlew check`
            
            or
            
            `c:\> gradle.bat check`

## code

- `com.what3words.sample.W3WtoUTMKTest`

The Object instance `W3WtoUTMK` has a method `geocodeUTMK`, it takes a String argument containing a 3 word address and returns coordinates in UTM-K:  

```java
...
		W3WtoUTMK geocoder = W3WtoUTMK.getInstance();
		Coordinates coords = geocoder.geocodeUTMK("factor.dribble.purest");
		assertEquals("X", 954015.8361943123,  coords.getX(), DELTA);
		assertEquals("Y", 1947363.9696324933,  coords.getY(), DELTA);
...
```

- `com.what3words.sample.W3WtoUTMK`

```java
package com.what3words.sample;

import com.what3words.Coordinates;
import com.what3words.LatLng;
import com.what3words.What3WordsException;
import org.osgeo.proj4j.CRSFactory;
import org.osgeo.proj4j.CoordinateReferenceSystem;
import org.osgeo.proj4j.CoordinateTransform;
import org.osgeo.proj4j.CoordinateTransformFactory;
import org.osgeo.proj4j.ProjCoordinate;

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

```

## license

Licensed under the MIT License

A copy of the license is available in the repository's [LICENSE](LICENSE.md) file.

  