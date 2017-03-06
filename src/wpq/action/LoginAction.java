package wpq.action;

import javax.annotation.Resource;

import org.dom4j.DocumentException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import wpq.model.User;
import wpq.service.IUserService;
import wpq.util.ActionUtil;
@Controller
@Scope("prototype")
public class LoginAction extends ActionSupport {
	private String username;
	private String password;
	private IUserService userService;
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Resource
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	public String loginInput(){
		return "loginInput";
	}
	
	public String login(){
		User u = null;
		try {
			u = userService.login(username, password);
		} catch (DocumentException e) {
			ActionContext.getContext().put("errorMsg", e.getMessage());
			return "error";
		}
		ActionContext.getContext().getSession().put("loginUser", u);
		ActionContext.getContext().put("url", "/user_showSelf?id="+u.getId());
		if(u.getType()==0){
			ActionContext.getContext().getSession().put("auth", ActionUtil.getAdminAuth());
		}else{
			ActionContext.getContext().getSession().put("auth", ActionUtil.getUserAuth());
		}
		return ActionUtil.REDIRECT;
	}
	
	public String logout(){
		ActionContext.getContext().getSession().remove("loginUser");
		return "loginInput";
	}
	
}
