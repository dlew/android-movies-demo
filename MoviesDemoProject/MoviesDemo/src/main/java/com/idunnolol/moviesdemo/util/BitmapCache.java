package com.idunnolol.moviesdemo.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.LruCache;


public class BitmapCache {

	private static Context sAppContext;

	private static LruCache<Integer, Bitmap> sCache;

	private static final int SCALE = 1024; // Measure everything in KB

	public static void init(Context context) {
		sAppContext = context.getApplicationContext();

		// Use 1/4th of the available memory for this memory cache.
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / SCALE);
		final int cacheSize = maxMemory / 4;

		Log.i("MovieDemo", "Creating Bitmap cache size of: " + cacheSize + " (total memory " + maxMemory + ")");

		sCache = new LruCache<Integer, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(Integer key, Bitmap bitmap) {
				return bitmap.getByteCount() / SCALE;
			}
		};
	}

	public static Bitmap getBitmap(int drawableResId) {
		Bitmap bitmap = sCache.get(drawableResId);
		if (bitmap == null) {
			bitmap = BitmapFactory.decodeResource(sAppContext.getResources(), drawableResId);
			sCache.put(drawableResId, bitmap);
		}
		return bitmap;
	}
}
