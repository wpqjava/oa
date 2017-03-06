package wpq.action;

import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import wpq.model.Department;
import wpq.model.SystemContext;
import wpq.model.User;
import wpq.model.UserEmail;
import wpq.service.IDepartmentService;
import wpq.service.IUserService;
import wpq.util.ActionUtil;

@Controller
@Scope("prototype")
public class UserAction extends ActionSupport implements ModelDriven<User> {
	private User user;
	private UserEmail ue;
	private IUserService userService;
	private IDepartmentService departmentService;
	private int depId;
	
	
	public UserEmail getUe() {
		return ue;
	}

	public void setUe(UserEmail ue) {
		this.ue = ue;
	}

	public Integer getDepId() {
		return depId;
	}

	public void setDepId(Integer depId) {
		this.depId = depId;
	}
	@Resource
	public void setDepartmentService(IDepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@Resource
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	@Override
	public User getModel() {
		if (user == null)
			user = new User();
		return user;
	}
	
	public String list(){
		ActionContext.getContext().put("deps", departmentService.listAll());
		ActionContext.getContext().put("pager", userService.findByDepId(depId));
		return SUCCESS;
	}
	
	public String addInput(){
		ActionContext.getContext().put("deps", departmentService.listAll());
		return SUCCESS;
	}
	
	public String add(){
		System.out.println(user);
		userService.add(user, depId);
		ActionContext.getContext().put("url", "/user_list");
		return ActionUtil.REDIRECT;
	}
	
	public void validateAdd(){
		if(user.getUsername()==null||"".equals(user.getUsername().trim())){
			this.addFieldError("username", "用户名不能为空");
		}
		if(user.getPassword()==null||"".equals(user.getPassword().trim())){
			this.addFieldError("password", "密码不能为空");
		}
		if(user.getNickname()==null||"".equals(user.getNickname().trim())){
			this.addFieldError("nickname", "用户昵称不能为空");
		}
		if(!vEmail()){
			this.addFieldError("email", "EMAIL格式不正确");
		}
		if(this.hasFieldErrors()){
			addInput();
		}
	}
	
	private boolean vEmail(){
		Pattern p = Pattern.compile("(\\w)+(\\.\\w+)*@(\\w)+(\\.\\w+)+");
		Matcher m = p.matcher(user.getEmail());
		return m.matches();
	}
	
	public String delete(){
		userService.delete(user.getId());
		ActionContext.getContext().put("url", "/user_list");
		return ActionUtil.REDIRECT;
	}
	
	public String updateInput() throws IllegalAccessException, InvocationTargetException{
		User u = userService.load(user.getId());
		ActionContext.getContext().put("deps", departmentService.listAll());
		BeanUtils.copyProperties(user, u);
		return SUCCESS;
	}
	
	public String update(){
		User ou = userService.load(user.getId());
		ou.setNickname(user.getNickname());
		ou.setEmail(user.getEmail());
		ou.setType(user.getType());
		ou.setDepartment(new Department(depId));
		userService.update(ou);
		ActionContext.getContext().put("url", "/user_list");
		return ActionUtil.REDIRECT;
	}
	
	public void validateUpdate(){
		ActionContext.getContext().put("deps", departmentService.listAll());
		if(user.getNickname()==null||"".equals(user.getNickname().trim())){
			this.addFieldError("nickname", "用户昵称不能为空");
		}
		if(!vEmail()){
			this.addFieldError("email", "EMAIL格式不正确");
		}
	}
	
	public String show() throws IllegalAccessException, InvocationTargetException{
		User u = userService.load(user.getId());
		BeanUtils.copyProperties(user, u);
		return SUCCESS;
	}
	
	public String showSelf() throws IllegalAccessException, InvocationTargetException{
		User u = userService.load(user.getId());
		ue = userService.loadUeByUserId(user.getId());
		BeanUtils.copyProperties(user, u);
		return SUCCESS;
	}
	
	public String updateSelfInput() throws IllegalAccessException, InvocationTargetException {
		User u = userService.load(user.getId());
		BeanUtils.copyProperties(user, u);
		return SUCCESS;
	}
	
	public String updateSelf(){
		User ou = userService.load(user.getId());
		ou.setNickname(user.getNickname());
		ou.setEmail(user.getEmail());
		ou.setPassword(user.getPassword());
		ou.setDepartment(new Department(depId));
		userService.update(ou);
		ActionContext.getContext().put("url", "/user_showSelf?id="+user.getId());
		return ActionUtil.REDIRECT;
	}
	
	public void validateUpdateSelf(){
		if(user.getNickname()==null||"".equals(user.getNickname().trim())){
			this.addFieldError("nickname", "用户昵称不能为空");
		}
		if(!vEmail()){
			this.addFieldError("email", "EMAIL格式不正确");
		}
	}
	
	public String addUeInput(){
		User u  = SystemContext.getLoginUser();
		try {
			BeanUtils.copyProperties(user, u);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String addUe(){
		userService.addUe(ue);
		ActionContext.getContext().put("url", "/user_showSelf?id="+user.getId());
		return ActionUtil.REDIRECT;
	}
	
	public void validateAddUe(){
		if(ue.getHost()==null||"".equals(ue.getHost().trim())){
			this.addFieldError("ue.host", "邮箱主机不能为空");
		}
		if(ue.getUsername()==null||"".equals(ue.getUsername().trim())){
			this.addFieldError("ue.username", "邮箱用户名不能为空");
		}
		if(ue.getPassword()==null||"".equals(ue.getPassword().trim())){
			this.addFieldError("ue.password", "邮箱密码不能为空");
		}
		if(this.hasFieldErrors()){
			addUeInput();
		}
	}
	
	public String updateUeInput(){
		User u  = SystemContext.getLoginUser();
		ue = userService.loadUeByUserId(u.getId());
		try {
			BeanUtils.copyProperties(user, u);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String updateUe(){
		userService.updateUe(ue);
		ActionContext.getContext().put("url", "/user_showSelf?id="+SystemContext.getLoginUser().getId());
		return ActionUtil.REDIRECT;
	}
	
	public void validateUpdateUe(){
		if(ue.getHost()==null||"".equals(ue.getHost().trim())){
			this.addFieldError("ue.host", "邮箱主机不能为空");
		}
		if(ue.getUsername()==null||"".equals(ue.getUsername().trim())){
			this.addFieldError("ue.username", "邮箱用户名不能为空");
		}
		if(ue.getPassword()==null||"".equals(ue.getPassword().trim())){
			this.addFieldError("ue.password", "邮箱密码不能为空");
		}
		if(this.hasFieldErrors()){
			updateUeInput();
		}
	}

}
