/**
 * 
 */
package com.sc.donateblood.navigationcontroller;

import com.sc.donateblood.view.DBHomeScreen;
import com.sc.donateblood.view.DBRegistrationScreen;
import com.sc.donateblood.view.DBSearchScreen;
import com.sc.donateblood.view.DBSplashScreen;
import com.sc.donateblood.view.DBUserDetailsScreen;

/**
 * @author Deepesh
 * 
 */
public class DBNavigationConfiguration extends DBNavigationConfigBase {

	@Override
	public void configure() {
		on(DBNavigationConstants.SPLASH_SCREEN).goTo(DBSplashScreen.class);
		on(DBNavigationConstants.HOME_SCREEN).goTo(DBHomeScreen.class);
		on(DBNavigationConstants.SEARCH_SCREEN).goTo(DBSearchScreen.class);
		on(DBNavigationConstants.USER_DETAILS_SCREEN).goTo(
				DBUserDetailsScreen.class);
		on(DBNavigationConstants.USER_REGISTER_SCREEN).goTo(
				DBRegistrationScreen.class);

	}

	// /**
	// * Create a key for Repository.
	// *
	// * @param <T>
	// * the generic type
	// * @param type
	// * the type
	// * @return the key
	// */
	// @SuppressWarnings("unchecked")
	// static <T> Key<Repository<T>> repositoryOf(Class<T> type, String name) {
	// return (Key<Repository<T>>) Key.get(
	// Types.newParameterizedType(Repository.class, type),
	// Names.named(name));
	// }
	//
	// /**
	// * Create a key for Repository.
	// *
	// * @param <T>
	// * the generic type
	// * @param type
	// * the type
	// * @return the key
	// */
	// @SuppressWarnings("unchecked")
	// static <T> Key<Repository<T>> repositoryOf(Class<T> type) {
	// return (Key<Repository<T>>) Key.get(Types.newParameterizedType(
	// Repository.class, type));
	// }
}
