package wpq.util;

import java.util.ArrayList;
import java.util.List;

public class ActionUtil {
	public static final String REDIRECT = "redirect";
	
	public static List<String> getUserAuth(){
		String[] strs = PropertiesUtil.getProp("auth.properties").getProperty("user").split(",");
		List<String> ls = new ArrayList<String>();
		for(int i=0;i<strs.length;i++){
			ls.add(strs[i]);
		}
		return ls;
	}
	
	public static List<String> getAdminAuth(){
		String[] strs = PropertiesUtil.getProp("auth.properties").getProperty("admin").split(",");
		List<String> ls = new ArrayList<String>();
		for(int i=0;i<strs.length;i++){
			ls.add(strs[i]);
		}
		return ls;
	}
	
	public static boolean checkAuth(List<String> al,String an){
		for(String str:al){
			if(an.startsWith(str))return true;
		}
		return false;
	}
}
