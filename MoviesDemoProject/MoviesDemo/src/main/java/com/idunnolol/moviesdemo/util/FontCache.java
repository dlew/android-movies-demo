package com.idunnolol.moviesdemo.util;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

public class FontCache {

	private static Map<String, Typeface> sCachedFonts = new HashMap<String, Typeface>();

	public static Typeface getTypeface(Context context, String assetPath) {
		if (!sCachedFonts.containsKey(assetPath)) {
			Typeface tf = Typeface.createFromAsset(context.getAssets(), assetPath);
			sCachedFonts.put(assetPath, tf);
		}

		return sCachedFonts.get(assetPath);
	}
}
