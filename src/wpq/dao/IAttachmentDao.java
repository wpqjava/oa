package wpq.dao;

import wpq.model.Attachment;

public interface IAttachmentDao extends IBaseDao<Attachment> {
	public void deleteByMsgId(int mid);
	public void deleteByDocId(int docId);
	public void clearSeesion();
	
}
