package com.what3words;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * what3words Geocoder
 *
 * Created by arnaud on 29/07/17.
 */
public class Geocoder {
	/**
	 * what3words API key
	 */
	private String apiKey;
	/**
	 * what3words API end point
	 */
	private String baseUrl = "https://api.what3words.com/v2";

	public static final String API_KEY_PROPERTY = "W3W_API_KEY";


	/**
	 * Constructor
	 *
	 * creates Geocoder
	 */
	public Geocoder() {
		apiKey = System.getProperty(API_KEY_PROPERTY);
		if(apiKey == null) {
			apiKey = System.getenv(API_KEY_PROPERTY);
		}
		assert apiKey != null;
	}

	/**
	 * Constructor
	 *
	 * creates Geocoder instance using the given API <code>key</code>
	 */
	public Geocoder(String key) {
		apiKey = key;
	}

	public LatLng geocode(String a3wordaddress) throws What3WordsException {
		ThreeWordAddress addr = new ThreeWordAddress(a3wordaddress);
		return geocode(addr);
	}

	public LatLng geocode(ThreeWordAddress addr) throws What3WordsException {
		String url = String.format("%s/forward?key=%s&addr=%s" ,this.baseUrl, this.apiKey, addr.get3WordAddress());

		try {
			String response = this.sendRequest(url);
			// parse the coordinates out of the JSON
			JSONObject json = new JSONObject(response);
			if (json.has("geometry")) {
				JSONObject jsonCoords = (JSONObject) json.get("geometry");
				double lat = (Double) jsonCoords.get("lat");
				double lng = (Double) jsonCoords.get("lng");

				return new LatLng(lat, lng);

			} else if (json.has("code")) {

				throw new What3WordsException("Error returned from w3w API: "
						+ json.getString("message"));

			} else if (json.has("status")) {

				if(json.get("status") instanceof JSONObject) {
					JSONObject status = json.getJSONObject("status");
					throw new What3WordsException("Error returned from w3w API: "
							+ status.getString("message"));
				} else {
					throw new What3WordsException(
							"Undefined error while fetching words by position");
				}

			} else {
				throw new What3WordsException(
						"Undefined error while fetching words by position");
			}

		} catch (Exception e) {
			throw new What3WordsException(e.getMessage(), e);
		}


	}
	/**
	 * Performs a HTTP GET request with the given URL.
	 *
	 * @param url
	 * @return
	 * @throws IOException
	 */
	private String sendRequest(String url) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// use HTTP GET
		con.setRequestMethod("GET");
		StringBuffer response = new StringBuffer();
		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		} catch (IOException e) {
			// HTTP status codes other than 2xx
			in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		}
		// Read response body
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return response.toString();
	}

}
