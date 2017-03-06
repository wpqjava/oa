package wpq.dao;

import java.util.List;

import org.hibernate.query.Query;
import wpq.model.Pager;

@SuppressWarnings("rawtypes")
public interface IBaseDao<T> {
	public void clear();
	public void add(T t);
	public void addObject(Object obj);
	public void delete(T t);
	public void update(T t);
	public T load(Class clz,int id);
	
	public List<T> list(String hql,Object[] args);
	public List<T> list(String hql,Object arg);
	public List<T> list(String hql);
	
	public Pager<T> find(String hql,Object[] args);
	public Pager<T> find(String hql,Object arg);
	public Pager<T> find(String hql);
	
	public Pager<Object> findObject(String hql,Object[] args);
	public Pager<Object> findObject(String hql,Object arg);
	public Pager<Object> findObject(String hql);
	
	public Object queryByHql(String hql,Object[] args);
	public Object queryByHql(String hql,Object arg);
	public Object queryByHql(String hql);
	
	public void excuteByHql(String hql,Object[] args);
	public void excuteByHql(String hql,Object arg);
	public void excuteByHql(String hql);
}
