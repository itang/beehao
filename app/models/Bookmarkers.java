package models;

import java.util.*;

import siena.*;

public class Bookmarkers{
	  private static Query<Bookmarker> all() {
        return Model.all(Bookmarker.class);
    }
    
    public static Bookmarker getById(Long id)  {
        return all().filter("id", id).get();
    }  
    
    public static Bookmarker getByName(String name)  {
        return all().filter("name", name).get();
    }
    
    public static List<Bookmarker> getAll() {
    	return all().order("-hit").fetch();
    } 
    
    public static List<Bookmarker> findByUser(String user){
      return all().filter("user", user).order("-hit").fetch();
    }
    
    public static  Bookmarker increaseOneHit(Bookmarker b) {
    	b.hit = b.hit + 1;
	    b.update();

	    return b;
    }
    
    public static Bookmarker add(String key, String name, String url, String user){
    	Bookmarker b = new Bookmarker(key, name, url, user) ;
    	b.insert();
    	
    	return b;
    }
}
