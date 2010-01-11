package controllers;

import java.io.*;
import java.net.URL;
import java.text.MessageFormat;
import java.io.*;

import java.util.*;
import java.net.*;

import play.*;
import play.mvc.*;
import play.data.validation.Validation;
import play.data.validation.Required;

import models.Bookmarker;
import models.Bookmarkers;

import myutils.AjaxResultJSON;
import myutils.ProxyUrl;


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
	  //clear data first
    for(Bookmarker bm :	Bookmarkers.getAll()){
      bm.delete();
    }
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
		
		//倒墙?
		if(key!=null && key.startsWith("http")){
		  proxy(key);
		  return;
		}

		renderArgs.put ("googleurl", "http://www.google.cn/search?q=" + key);
		renderArgs.put ("bingurl", "http://www.bing.com/search?q=" + key);
		render();
	}
	
	/**
	 * 翻墙.
	 *
	 */
	public static void proxy(final String url){
	  HttpURLConnection conn = null; 
	  InputStream is = null; 
	  StringWriter writer = new StringWriter();
	  final String targetURL = ProxyUrl.real(url);
	  System.out.println("target url:" + targetURL);
	   
	  try{
	    conn = (HttpURLConnection) new URL(targetURL).openConnection();
	    conn.setConnectTimeout(15000);
	    conn.setReadTimeout(15000); 
	    conn.setInstanceFollowRedirects(true);  
      conn.setUseCaches(false); 
      
	    is = conn.getInputStream();
	    byte[] buff = new byte[1024];  
      int size = 0;  

      while ((size = is.read(buff)) != -1) {
            writer.write(new String(buff, 0, size));  
      }
	  }catch(Exception e){
	    e.printStackTrace();
	    writer.write("发生错误!" + e.getMessage());
	  
	  }finally{
	    if(is!=null) try{is.close();}catch(Exception e){}
	    
	    writer.flush();
	    
	    final String content = writer.toString().replaceAll("href","value").replaceAll("src","value");
      
      renderArgs.put ("content", content);
      render();
	  } 
	
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
