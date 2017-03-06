package wpq.dao;

import wpq.model.UserDocument;

public interface IUserDocumentDao extends IBaseDao<UserDocument> {
	public void updateIsRead(int id);
}
