package wpq.service;

import java.io.IOException;
import java.util.List;

import wpq.dto.AttachDto;
import wpq.model.Attachment;
import wpq.model.Department;
import wpq.model.Document;
import wpq.model.Pager;
import wpq.model.UserDocument;

public interface IDocumentService {
	public void add(Document doc,Integer[] depIds,AttachDto ad)throws IOException;
	
	public void delete(int id);
	
	public void updateIsRead(int docId);
	
	public Document load(int id);
	
	public Pager<Document> findSendDocument(String con);
	
	public Pager<UserDocument> findReceiveDocument(String con,int isRead,int depId);
	
	public List<Attachment> getDocAttach(int docId);
	
	public List<Department> getCanSendDeps();
	
	public List<Department> getSendedDepsByDocId(int docId);
}
