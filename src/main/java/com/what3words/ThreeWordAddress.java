package com.what3words;

import java.util.List;

/**
 * Created by arnaud on 29/07/17.
 */
public class ThreeWordAddress {
	public static final int TOTAL_WORDS = 3;
	public static final String SEPARATOR = ".";

	private String address;

	private ThreeWordAddress() {}

	public ThreeWordAddress(String threeWordAddres) {
		this.address = threeWordAddres;
	}

	public ThreeWordAddress(String word1, String word2, String word3) {
		this(String.format("%s%s%s%s%s",word1, SEPARATOR, word2, SEPARATOR, word3));
	}

	public String get3WordAddress() {
		return address;
	}

}
