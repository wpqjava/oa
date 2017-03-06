package wpq.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import wpq.model.Department;
import wpq.model.Pager;
@Repository("departmentDao")
public class DepartmentDao extends BaseDao<Department> implements IDepartmentDao {

	@Override
	public List<Department> listByDepId(int did) {
		
		return this.list("select dep from DepScope ds left join ds.toDep dep where ds.depId=?", did);
	}
}
