package wpq.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import wpq.model.Department;
import wpq.service.IDepartmentService;
import wpq.util.ActionUtil;
@Controller("departmentAction")
@Scope("prototype")
public class DepartmentAction extends ActionSupport implements ModelDriven<Department> {
	private Department department;
	private IDepartmentService departmentService;
	private int[] aidd;
	
	public int[] getAidd() {
		return aidd;
	}

	public void setAidd(int[] aidd) {
		this.aidd = aidd;
	}

	@Resource
	public void setDepartmentService(IDepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@Override
	public Department getModel() {
		if(department==null) department = new Department();
		return department;
	}
	
	public String addInput(){
		return SUCCESS;
	}
	
	public String add(){
		departmentService.add(department);
		ActionContext.getContext().put("url", "/department_list");
		return ActionUtil.REDIRECT;
	}
	
	public void validateAdd(){
		if(department.getName()==null||"".equals(department.getName().trim())){
			this.addFieldError("name", "部门名称不能为空");
		}
	}
	
	public String delete(){
		try {
			departmentService.delete(department.getId());
		} catch (Exception e) {
			ActionContext.getContext().put("errorMsg", e.getMessage());
			return "error";
		}
		ActionContext.getContext().put("url", "/department_list");
		return ActionUtil.REDIRECT;
	}
	
	public String updateInput(){
		Department dep = departmentService.load(department.getId());
		department.setName(dep.getName());
		department.setId(dep.getId());
		return SUCCESS;
	}
	
	public String update(){
		Department odep = departmentService.load(department.getId());
		odep.setName(department.getName());
		departmentService.update(odep);
		ActionContext.getContext().put("url", "/department_list");
		return ActionUtil.REDIRECT;
	}
	
	public void validateUpdate(){
		if(department.getName()==null||"".equals(department.getName().trim())){
			this.addFieldError("name", "部门名称不能为空");
		}
	}
	
	public String list(){
		List<Department> ls = departmentService.listAll();
		ActionContext.getContext().put("ls", ls);
		return SUCCESS;
	}
	
	public String show(){
		this.setModelDepByDepId();
		List<Department> ls = departmentService.getInformableDepByDepId(department.getId());
		if(ls.size()>0){
			ActionContext.getContext().put("ls", ls);	
		}
		return SUCCESS;
	}
	
	public String setDepScopeInput(){
		this.setModelDepByDepId();
		List<Department> allDeps = departmentService.listAll();
		List<Integer> availableInformDep = dep2Interger(departmentService.getInformableDepByDepId(department.getId()));
		ActionContext.getContext().put("allDeps", allDeps);
		ActionContext.getContext().put("availableInformDep", availableInformDep);
		return SUCCESS;
	}
	
	public String setDepScope(){
		departmentService.setDepScope(department.getId(), aidd);
		ActionContext.getContext().put("url", "/department_show?id="+department.getId());
		return ActionUtil.REDIRECT;
	}

	private List<Integer> dep2Interger(List<Department> informableDepByDepId) {
		List<Integer> ls = new ArrayList<Integer>();
		for(Department dep:informableDepByDepId){
			ls.add(dep.getId());
		}
		return ls;
	}
	
	private void setModelDepByDepId(){
		Department dep = departmentService.load(department.getId());
		department.setName(dep.getName());
		department.setId(dep.getId());
	}

}
