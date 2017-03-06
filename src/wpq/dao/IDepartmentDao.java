package wpq.dao;

import java.util.List;

import wpq.model.Department;

public interface IDepartmentDao extends IBaseDao<Department> {
	/**
	 * 通过部门ID获得该部门的可发文部门的对象
	 * @param did
	 * @return
	 */
	public List<Department> listByDepId(int did);
}
