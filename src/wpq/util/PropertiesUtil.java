package wpq.util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
	private static Properties prop;
	
	public static Properties getProp(String str){
		if(prop==null){
			prop = new Properties();
			try {
				prop.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(str));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return prop;
	}
}
