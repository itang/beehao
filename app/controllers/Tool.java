package controllers;

import java.io.*;
import java.net.URL;
import java.text.MessageFormat;
import java.io.*;

import java.util.*;

import play.*;
import play.mvc.*;
import play.data.validation.Validation;
import play.data.validation.Required;

import models.Bookmarker;
import models.Bookmarkers;

import myutils.AjaxResultJSON;


/**
 * 工具.
 *
 */
public class Tool extends Controller {
	/**
	 * 工具主页.
	 *
	 */
	public static void index(){
		render();
	}
	
	/**
	 * 初始化站点数据.
	 */
	public static void init_mysite_datas(){
		//默认收藏站点列表
		LinkedHashMap<String, Site> sites = new LinkedHashMap<String, Site>();
		Bookmarkers.add("javaeye" ,"JavaEye", "http://www.javaeye.com") ;
		Bookmarkers.add("infoq" ,"infoq","http://www.infoq.com");
		Bookmarkers.add("infoqcn" ,"info中国","http://www.infoq.com/cn");
		Bookmarkers.add("github" ,"github","http://github.com");
		Bookmarkers.add("pragrammingscala","pragrammingscala", "http://programming-scala.labs.oreilly.com");
		Bookmarkers.add("tianya","天涯房产", "http://www.tianya.cn/publicforum/articleslist/0/house.shtml");
		Bookmarkers.add("tieba","湖南贴吧","http://tieba.baidu.com/f?kw=%BA%FE%C4%CF%CE%C0%CA%D3");
		Bookmarkers.add("sina","新浪","http://news.sina.com.cn");
		
		renderJSON("{msg:'success!'}");
	}
	
	/**
	 * 我的搜索.
	 *
	 */
	public static void search () {
		String key = params.get("key");
		renderArgs .put ("key", key);
		renderArgs .put ("googleurl", "http://www.google.cn/search?q=" + key);
		renderArgs. put ("bingurl", "http://www.bing.com/search?q=" + key);
		render();
	}
	
	/**
	 * 添加书签.
	 *
	 */
	public static void add_bookmarker() {
	 	String key = params.get("key");
	 	String name = params.get("name");
	 	String url = params.get("url");
	 	Bookmarkers.add(key, name, url);
	 	renderJSON(new AjaxResultJSON(true,"操作成功").toJson());
	 }
}