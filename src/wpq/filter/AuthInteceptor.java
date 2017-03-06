package wpq.filter;

import java.util.List;

import org.dom4j.DocumentException;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import wpq.model.User;
import wpq.util.ActionUtil;
@Component("authInteceptor")
public class AuthInteceptor extends AbstractInterceptor {


	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		User u = (User) ActionContext.getContext().getSession().get("loginUser");
		String an = arg0.getProxy().getActionName();
		/*if(!an.startsWith("login")){
			if(u==null){
				return "loginInput";
			}
		}*/
		if(u==null){
			return "loginInput";
		}else{
			List<String> auth = (List<String>) ActionContext.getContext().getSession().get("auth");
			if(!ActionUtil.checkAuth(auth, an)) {
				ActionContext.getContext().put("errorMsg", "没有权限");
				return "error";
			}
		}
		return arg0.invoke();
	}

}
