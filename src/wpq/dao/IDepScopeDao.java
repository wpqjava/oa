package wpq.dao;

import java.util.List;

import wpq.model.DepScope;
import wpq.model.Department;

public interface IDepScopeDao extends IBaseDao<DepScope> {
	/**
	 * 
	 * @param dId 发文部门
	 * @param depIds 需删除的收文部门
	 */
	public void addByDepIds(int dId,int toAdd);
	/**
	 * 
	 * @param dId 发文部门
	 * @param depIds 需删除的收文部门
	 */
	public void deleteByDepIds(int dId,int toDelete);
	
}
