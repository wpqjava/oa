package wpq.dao;

import wpq.model.Document;

public interface IDocumentDao extends IBaseDao<Document> {
	public void deleteSendDoc(int docId);
}
