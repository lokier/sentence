
package com.juzicool.search;

import com.jfinal.config.Routes;
import com.juzicool.search.controller.LoginController;
import com.juzicool.search.controller.SearchController;
import com.juzicool.search.controller.UserController;


/**
 * 前台路由
 */
public class FrontRoutes extends Routes {

	public void config() {
		setBaseViewPath("/_view2");
		
		add("/", SearchController.class, "/");
		add("/user", UserController.class, "/");
		add("/login", LoginController.class, "/");

		//add("/search", SearchController.class, "/");
	}
}
