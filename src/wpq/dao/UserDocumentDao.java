package wpq.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import wpq.model.Pager;
import wpq.model.SystemContext;
import wpq.model.UserDocument;
@Repository("userDocumentDao")
public class UserDocumentDao extends BaseDao<UserDocument> implements IUserDocumentDao {

	@Override
	public void updateIsRead(int id) {
		getSession().createQuery("update UserDocument ud set ud.isRead=1 where ud.id=?0").setParameter("0", id).executeUpdate();
	}

}
