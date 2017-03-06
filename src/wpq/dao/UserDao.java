package wpq.dao;


import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import wpq.model.User;
@Repository("userDao")
public class UserDao extends BaseDao<User> implements IUserDao {

	@Override
	public long countNumByDepId(int did) {
		return (long) this.queryByHql("select count(*) from User user where user.department.id=?",did);
	}

	@Override
	public User loadByUsername(String username) {
		User u = (User)this.queryByHql("select u from User u left join fetch u.department where u.username=?", username);
		return u;
	}

	@Override
	public List<User> loadByIds(Integer[] ids) {
		Query q = this.getSession().createQuery("select new User(u.id) from User u where u.id in (:ids)");
		q.setParameterList("ids", ids);
		return q.getResultList();
	}

	@Override
	public List<User> loadCanSendUserByUserId(int userId) {
		String sql = "select t3.id,t3.nickname from t_user t1 left join t_dep_scope t2 on (t1.did=t2.dep_id) right join t_user t3 on (t2.to_dep_id=t3.did) where t1.id=?0";
		return this.getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(User.class)).setParameter("0", userId).getResultList();
	}

	@Override
	public List<String> listEmails(Integer[] ids) {
		return getSession().createQuery("select u.email from User u where u.id in :ids and u.email is not null").setParameterList("ids", ids).getResultList();
	}
	
	

}
