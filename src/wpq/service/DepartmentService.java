package wpq.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;

import wpq.dao.IDepScopeDao;
import wpq.dao.IDepartmentDao;
import wpq.dao.IUserDao;
import wpq.exception.DocumentException;
import wpq.model.Department;
@Service("departmentService")
public class DepartmentService implements IDepartmentService {
	private IDepartmentDao departmentDao;
	private IDepScopeDao depScopreDao;
	private IUserDao userDao;
	
	@Resource
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	@Resource
	public void setDepartmentDao(IDepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}
	@Resource
	public void setDepScopreDao(IDepScopeDao depScopreDao) {
		this.depScopreDao = depScopreDao;
	}
	
	@Override
	public void add(Department dep) {
		departmentDao.add(dep);
	}

	@Override
	public void delete(int id) {
		long num = userDao.countNumByDepId(id);
		if(num>0) throw new DocumentException("部门内还有员工不能删除");
		departmentDao.delete(new Department(id));
	}

	@Override
	public void update(Department dep) {
		departmentDao.update(dep);
	}

	@Override
	public Department load(int id) {
		return departmentDao.load(Department.class, id);
	}

	@Override
	public List<Department> listAll() {
		return departmentDao.list("from Department");
	}

	@Override
	public List<Department> listByUserId(int uid) {
		int depId = userDao.load(User.class, uid).getDepartment().getId();
		return departmentDao.list("select ds from DepScope ds where ds.depId=?", depId);
	}
	@Override
	public List<Department> getInformableDepByDepId(int did) {
		return departmentDao.listByDepId(did);
	}
	@Override
	public void setDepScope(int depId,int[] aidd) {
		List<Integer> aiddls = new ArrayList<Integer>();//新修改的可发文部门ID列表
		Arrays2List(aidd, aiddls);
		List<Integer> oiddls = new ArrayList<Integer>();//已经存在的可发文部门ID列表
		List<Integer> toAdd = new ArrayList<Integer>();//待增加的
		List<Integer> toDelete = new ArrayList<Integer>();//待删除的
		List<Department> ls = departmentDao.listByDepId(depId);//已经存在的可发文部门对象
		for(Department dep:ls){
			oiddls.add(dep.getId());
		}
		for(int dId:aiddls){//遍历新的可发文部门ID
			if(!oiddls.contains(dId)){
				toAdd.add(dId);
				depScopreDao.addByDepIds(depId, dId);
			}
		}
		for(int dId:oiddls){
			if(!aiddls.contains(dId)){
				toDelete.add(dId);
				depScopreDao.deleteByDepIds(depId, dId);
			}
		}
	}
	
	private void Arrays2List(int[] arrays,List<Integer> list){
		for(int i=0;i<arrays.length;i++){
			list.add(arrays[i]);
		}
	}
	
	private void showList(String str,List<Integer> ls){
		System.out.println("------显示LIST-------start");
		for(int i:ls){
			System.out.println(str+":"+i);
		}
		System.out.println("------显示LIST-------end");
	}
	

}
