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
		
		public Bookmarker(){
			
		}
		
		public Bookmarker(String key, String name, String url){
			this.key = key;
			this.name = name;
			this.url = url;
		}
		
		public String toString() {
			return  name + "("  + key + ")";
		}
}
