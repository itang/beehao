package models;

import java.util.*;

import siena.*;

 /**
 * 书签Entity.
 *
 */
public class Bookmarker extends Model{
		@Id public Long id;
		public String key;
		public String name;
		public String url;
		public Integer sort =0;
		public String description  = "";
		
		public Date createDate  = new Date();
		public int hit = 0;
		public String tag  = "default";
		public String user = "live.tang@gmail.com";
		
		public Bookmarker(){
			
		}
		
		public Bookmarker(String key, String name, String url){
			this.key = key;
			this.name = name;
			this.url = url;
		}
		
		public Bookmarker(String key, String name, String url, String user){
			this.key = key;
			this.name = name;
			this.url = url;
			this.user = user;
		}
		
		public String toString() {
			return  name + "("  + id + ", " +  key + ")";
		}
		
		/////////////////////////////////////////////////////
		
		public static BookmarkerViewer viewer(String user){
      return new BookmarkerViewer(user);
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
    
    private static Query<Bookmarker> all() {
        return Model.all(Bookmarker.class);
    }
}
