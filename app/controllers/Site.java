package controllers;


import play.*;
import play.mvc.*;
import play.mvc.Http.*;
import play.mvc.results.*;

import models.Bookmarker;
import models.Bookmarkers;


/**
 * 我的主页Action.
 *
 */
public class Site extends Controller{
	/**
	 * 主页.
	 *
	 */
	public static void index() {
		System.out.println("remoteAddress:" + Request.current().remoteAddress );
		Bookmarker homepage =  new Bookmarker("javaeye","JavaEye","http://www.javaeye.com");
		renderArgs.put("homepage", homepage);
		renderArgs.put("bookmarkers",  Bookmarkers.getAll() );
		render();
	}  
	
	/**
	 * 更新bookmarker点击量.
	 *
	 */
	public static void update_hit(){
		if(Request.current().method.equals( "POST")){
			String key = params.get("key");
			Bookmarker b = Bookmarkers.increaseOneHit(key);
			renderJSON( "{msg:'ok',success:true,currHit: " + b.hit + "}" );
		} else{  
			throw new Forbidden("不允许get请求!");
		}
	}
}