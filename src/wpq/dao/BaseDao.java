package wpq.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import wpq.model.Pager;
import wpq.model.SystemContext;
@SuppressWarnings("unchecked")
public class BaseDao<T> implements IBaseDao<T> {
	private SessionFactory sessionfactory;
	
	@Resource
	public void setSessionfactory(SessionFactory sessionfactory) {
		this.sessionfactory = sessionfactory;
	}
	
	public  Session getSession(){
		return sessionfactory.getCurrentSession();
	}
	

	@Override
	public void add(T t) {
		getSession().save(t);
	}

	@Override
	public void delete(T t) {
		getSession().delete(t);
	}

	@Override
	public void update(T t) {
		getSession().update(t);
	}

	@Override
	public T load(Class clz,int id) {
		return (T) getSession().get(clz, id);
	}
	
	protected Query createQuery(String hql,Object[] args){
		Query q = getSession().createQuery(hql);
		if(args!=null){
			for(int i=0;i<args.length;i++){
				q.setParameter(i, args[i]);
			}
		}
		return q;
	}

	@Override
	public List<T> list(String hql, Object[] args) {
		return createQuery(hql, args).getResultList();
	}

	@Override
	public List<T> list(String hql, Object arg) {
		return this.list(hql, new Object[]{arg});
	}

	@Override
	public List<T> list(String hql) {
		return this.list(hql, null);
	}
	
	private String countByHql(String hql){
		String s = hql.substring(0,hql.indexOf("from"));
		if(s==null||"".equals(s.trim())){
			hql = "select count(*) "+hql;
		}else{
			hql = hql.replace(s,"select count(*) ");
		}
		hql = hql.replace("fetch", " ");
		return hql;
	}

	@Override
	public Pager<T> find(String hql, Object[] args) {
		int pageOffset = SystemContext.getPageOffset();
		int pageSize = SystemContext.getPageSize();
		List<T> datas = createQuery(hql, args).setFirstResult(pageOffset).setMaxResults(pageSize).getResultList();
		long totalRecord = (Long) createQuery(countByHql(hql), args).uniqueResult();
		Pager<T> pager = new Pager<T>();
		pager.setDatas(datas);
		pager.setPageOffset(pageOffset);
		pager.setPageSize(pageSize);
		pager.setTotalRecord(totalRecord);
		return pager;
	}

	@Override
	public Pager<T> find(String hql, Object arg) {
		return find(hql, new Object[]{arg});
	}

	@Override
	public Pager<T> find(String hql) {
		return find(hql, null);
	}
	
	@Override
	public Pager<Object> findObject(String hql, Object[] args) {
		int pageOffset = SystemContext.getPageOffset();
		int pageSize = SystemContext.getPageSize();
		List<Object> datas = createQuery(hql, args).setFirstResult(pageOffset).setMaxResults(pageSize).getResultList();
		long totalRecord = (Long) createQuery(countByHql(hql), args).uniqueResult();
		Pager<Object> pager = new Pager<Object>();
		pager.setDatas(datas);
		pager.setPageOffset(pageOffset);
		pager.setPageSize(pageSize);
		pager.setTotalRecord(totalRecord);
		return pager;
	}

	@Override
	public Pager<Object> findObject(String hql, Object arg) {
		return findObject(hql, new Object[]{arg});
	}

	@Override
	public Pager<Object> findObject(String hql) {
		return findObject(hql, null);
	}

	@Override
	public Object queryByHql(String hql, Object[] args) {
		return createQuery(hql, args).uniqueResult();
	}

	@Override
	public Object queryByHql(String hql, Object arg) {
		return queryByHql(hql, new Object[]{arg});
	}

	@Override
	public Object queryByHql(String hql) {
		return queryByHql(hql, null);
	}

	@Override
	public void excuteByHql(String hql, Object[] args) {
		Query q = createQuery(hql, args);
		q.executeUpdate();
	}

	@Override
	public void excuteByHql(String hql, Object arg) {
		Query q = createQuery(hql, new Object[]{arg});
		q.executeUpdate();
	}

	@Override
	public void excuteByHql(String hql) {
		Query q = createQuery(hql, null);
		q.executeUpdate();
	}

	@Override
	public void addObject(Object obj) {
		getSession().save(obj);
	}

	@Override
	public void clear() {
		getSession().clear();
	}

}
