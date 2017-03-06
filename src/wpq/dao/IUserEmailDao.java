package wpq.dao;

import wpq.model.UserEmail;

public interface IUserEmailDao extends IBaseDao<UserEmail> {
	public UserEmail loadByUseId(int id);
}
