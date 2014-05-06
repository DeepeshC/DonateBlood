/**
 * 
 */
package com.sc.donateblood.navigationcontroller;

import java.util.HashMap;
import java.util.Map;

import roboguice.inject.InjectResource;

import com.sc.donateblood.R;

/**
 * @author sreekumarku
 * 
 */
public abstract class DBNavigationConfigBase {
	private Map<String, DBNavigationElement> map = new HashMap<String, DBNavigationElement>();
	/** The duplicate key msg. */
	@InjectResource(R.string.app_name)
	private String duplicateKeyMsg;

	/** The nav not found msg. */
	@InjectResource(R.string.home_search_bg)
	private String navNotFoundMsg;

	/**
	 * Configure.
	 */
	public abstract void configure();

	/**
	 * On new key.
	 * 
	 * @param key
	 *            the key
	 * @return the nav element
	 */
	protected DBNavigationElement on(String key) {
		return new DBNavigationElement(key, this);
	}

	/**
	 * Gets the class object.
	 * 
	 * @param key
	 *            the key
	 * @return the class object
	 */
	public Class<?> getClassObject(String key) {
		if (!map.containsKey(key)) {
			throw new IllegalArgumentException(String.format(navNotFoundMsg,
					key));
		}
		DBNavigationElement navElement = map.get(key);
		return navElement.getClassObject();
	}

	/**
	 * Gets the class object.
	 * 
	 * @param key
	 *            the key
	 * @return the class object
	 */
	public Class<?> getFragmentObject(String key) {
		if (!map.containsKey(key)) {
			throw new IllegalArgumentException(String.format(navNotFoundMsg,
					key));
		}
		DBNavigationElement navElement = map.get(key);
		return navElement.getFragmentObject();
	}

	/**
	 * Adds the nav element.
	 * 
	 * @param navElement
	 *            the nav element
	 */
	public void add(DBNavigationElement navElement) {
		String key = navElement.getKey();
		if (map.containsKey(key)) {
			throw new IllegalArgumentException(String.format(duplicateKeyMsg,
					key));
		}

		map.put(key, navElement);
	}
}
