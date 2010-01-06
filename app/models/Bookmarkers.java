package models;

import java.util.*;

import siena.*;

public class Bookmarkers extends Model{
	private static Query<Bookmarker> all() {
        return Model.all(Bookmarker.class);
    }
    
    public static Bookmarker get(String name)  {
        return all().filter("name", name).get();
    }
    
    public static List<Bookmarker> getAll() {
    	return all().fetch();
    } 
    
    public static  Bookmarker increaseOneHit(String key) {
    	Bookmarker b =  all().filter("key", key).get();
    	if(b != null){
    		b.hit = b.hit + 1;
	    	b.update();
		}
	    return b;
    }
    
    public static Bookmarker add(String key, String name,String url){
    	Bookmarker b = new Bookmarker(key,name,url) ;
    	//b.description = description;
    	//b.tag = tag;
    	b.insert();
    	return b;
    }
}