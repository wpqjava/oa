package wpq.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import wpq.model.Pager;
import wpq.model.UserEmail;
@Repository("userEmailDao")
public class UserEmailDao extends BaseDao<UserEmail> implements IUserEmailDao {

	@Override
	public UserEmail loadByUseId(int id) {
		return (UserEmail) this.queryByHql("from UserEmail ue left join fetch ue.user where ue.user.id=?", id);
	}


}
