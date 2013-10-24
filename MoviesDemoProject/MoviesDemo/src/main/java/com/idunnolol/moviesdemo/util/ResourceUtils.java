package com.idunnolol.moviesdemo.util;

import android.util.Log;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Utilities relating to resources (anything under /res/ or deals
 * with the Resources class).
 */
public class ResourceUtils {

	/** Cache of resources ids, for speed */
	private static Map<Class<?>, Map<String, Integer>> mIdentifierCache = new ConcurrentHashMap<Class<?>, Map<String, Integer>>();

	/**
	 * Retrieves a resource id dynamically, via reflection.  It's much faster
	 * than Resources.getIdentifier(), however it only allows you to get
	 * identifiers from your own package. 
	 * 
	 * Note that this method is still slower than retrieving resources
	 * directly (e.g., R.drawable.MyResource) - it should only be used
	 * when dynamically retrieving ids.
	 * 
	 * @param type the type of resource (e.g. R.drawable.class, R.layout.class, etc.)
	 * @param name the name of the resource
	 * @return the resource id, or -1 if not found
	 */
	public static int getIdentifier(Class<?> type, String name) {
		// See if the cache already contains this identifier
		Map<String, Integer> typeCache;
		if (!mIdentifierCache.containsKey(type)) {
			typeCache = new ConcurrentHashMap<String, Integer>();
			mIdentifierCache.put(type, typeCache);
		}
		else {
			typeCache = mIdentifierCache.get(type);
		}

		if (typeCache.containsKey(name)) {
			return typeCache.get(name);
		}

		// Retrieve the identifier
		try {
			Field field = type.getField(name);
			int resId = field.getInt(null);

			if (resId != -1) {
				typeCache.put(name, resId);
			}

			return resId;
		}
		catch (Exception e) {
			Log.e("MovieDemo", "Failed to retrieve identifier: type=" + type + " name=" + name, e);
			return -1;
		}
	}
}
