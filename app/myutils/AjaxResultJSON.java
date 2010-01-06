package myutils;

public class AjaxResultJSON implements Jsonable{
	public Boolean success ;
	public String msg;
	public String description="";
	public AjaxResultJSON(Boolean success, String msg){
		this.success = success;
		this.msg = msg;
	}
	public String toJson() {
		return String.format("{success:%s,msg:'%s',description:'%s'}", success.toString(), msg, description);
	}
}