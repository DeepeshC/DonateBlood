/**
 * 
 */
package com.sc.donateblood;

import com.google.inject.AbstractModule;
import com.sc.donateblood.navigationcontroller.DBNavigationConfigBase;
import com.sc.donateblood.navigationcontroller.DBNavigationConfiguration;
import com.sc.donateblood.navigationcontroller.DBNavigationController;
import com.sc.donateblood.navigationcontroller.DBNavigationControllerImpl;

/**
 * @author Deepesh
 * 
 */
public class DBModule extends AbstractModule {

	@Override
	protected void configure() {
		/* Add binding of Navigations here. */
		bind(DBNavigationConfigBase.class).to(DBNavigationConfiguration.class)
				.asEagerSingleton();
		bind(DBNavigationController.class).to(DBNavigationControllerImpl.class)
				.asEagerSingleton();
	}
	

}
