
package com.juzicool.search;

import com.jfinal.config.Routes;
import com.juzicool.search.controller.SearchController;


/**
 * 前台路由
 */
public class FrontRoutes extends Routes {

	public void config() {
		setBaseViewPath("/_view2");
		
		add("/", SearchController.class, "/");
	}
}
