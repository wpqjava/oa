package wpq.dao;


import org.springframework.stereotype.Repository;

import wpq.model.Document;
@Repository("documentDao")
public class DocumentDao extends BaseDao<Document> implements IDocumentDao {

	@Override
	public void deleteSendDoc(int docId) {
		getSession().createQuery("delete UserDocument where document.id=?0").setParameter("0", docId).executeUpdate();
		Document doc = new Document();
		doc.setId(docId);
		this.delete(doc);
	}

}
