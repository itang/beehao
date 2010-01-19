package controllers;

import play.*;
import play.mvc.*;
import play.mvc.Http.*;
import play.mvc.results.*;

import play.modules.gae.*;
import play.data.validation.*;

import models.*;

/**
 * 我的主页Action.
 *
 */
public class Site extends Controller{
  @Before
  static void checkConnected() {
      if(GAE.getUser() == null) {
          Application.login();
      } else {
          renderArgs.put("user", GAE.getUser().getEmail());
      }
  }
  
	/**
	 * 主页.
	 *
	 */
	public static void index() {
	  String homepage = Config.getHomepage(getUser());
	  if(homepage == null) homepage = "http://www.javaeye.com";

		renderArgs.put("homepage", homepage);
		renderArgs.put("bookmarkers",  Bookmarker.viewer(getUser()).getAll());
		render();
	}  
	
	/**
	 * 更新bookmarker点击量.
	 *
	 */
	public static void update_hit(Long id){
		if(Request.current().method.equals( "POST")){
			Bookmarker bookmarker = Bookmarker.getById(id);
			
			notFoundIfNull(bookmarker);
			checkOwner(bookmarker);
			
			Bookmarker b = Bookmarker.increaseOneHit(bookmarker);
			
			renderJSON( "{msg:'ok',success:true,currHit: " + b.hit + "}" );
		} else{  
			throw new Forbidden("不允许get请求!");
		}
	}
	
	static String getUser() {
     return renderArgs.get("user", String.class);
  }
  
  static void checkOwner(Bookmarker bookmarker) {
      if(!getUser().equals(bookmarker.user)) {
          forbidden();
      }
  }
}
