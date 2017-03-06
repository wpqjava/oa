package wpq.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import wpq.model.DepScope;
import wpq.model.Department;
@Repository("depScopeDao")
public class DepScopeDao extends BaseDao<DepScope> implements IDepScopeDao {

	@Override
	public void deleteByDepIds(int dId,int toDelete) {
		this.getSession().createQuery("delete from DepScope ds where ds.depId=? and ds.toDep.id=?")
		.setParameter(0, dId).setParameter(1, toDelete).executeUpdate();
	}

	@Override
	public void addByDepIds(int dId, int toAdd) {
		DepScope ds = null;
		ds = new DepScope();
		ds.setDepId(dId);
		ds.setToDep(new Department(toAdd));
		this.add(ds);
	}

}
