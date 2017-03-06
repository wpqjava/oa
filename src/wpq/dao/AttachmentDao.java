package wpq.dao;



import org.springframework.stereotype.Repository;

import wpq.model.Attachment;
@Repository("attachmentDao")
public class AttachmentDao extends BaseDao<Attachment> implements IAttachmentDao {

	@Override
	public void deleteByMsgId(int mid) {
		getSession().createQuery("delete Attachment where message.id=?0").setParameter("0", mid).executeUpdate();
	}

	@Override
	public void clearSeesion() {
		getSession().clear();
	}

	@Override
	public void deleteByDocId(int docId) {
		getSession().createQuery("delete Attachment where document.id=?0").setParameter("0", docId).executeUpdate();
	}


}
