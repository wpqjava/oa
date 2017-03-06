package wpq.service;

import java.util.List;

import wpq.model.Department;

public interface IDepartmentService {
	public void add(Department dep);
	public void delete(int id);
	public void update(Department dep);
	public Department load(int id);
	
	public List<Department> listAll();
	/**
	 * 通过userID列出用户可发文的部门
	 * @param uid
	 * @return
	 */
	public List<Department> listByUserId(int uid);
	/**
	 * 通过部门ID列出可发文部门
	 * @param did
	 * @return
	 */
	public List<Department> getInformableDepByDepId(int did);
	/**
	 * 通过传入一组新的可发文部门的ID数组来设置可发文部门
	 * @param aidd 新的可发文部门的ID
	 */
	public void setDepScope(int depId,int[] aidd);
}
