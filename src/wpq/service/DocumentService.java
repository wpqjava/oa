package wpq.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import wpq.dao.IAttachmentDao;
import wpq.dao.IDepartmentDao;
import wpq.dao.IDocumentDao;
import wpq.dao.IUserDao;
import wpq.dao.IUserDocumentDao;
import wpq.dto.AttachDto;
import wpq.model.Attachment;
import wpq.model.DepDocument;
import wpq.model.Department;
import wpq.model.Document;
import wpq.model.Pager;
import wpq.model.SystemContext;
import wpq.model.User;
import wpq.model.UserDocument;
import wpq.util.AttachUtil;
@Service("documentService")
public class DocumentService implements IDocumentService {
	private IUserDao userDao;
	private IDepartmentDao departmentDao;
	private IDocumentDao documentDao;
	private IUserDocumentDao userDocumentDao;
	private IAttachmentDao attachmentDao;
	@Resource
	public void setAttachmentDao(IAttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}
	@Resource
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	@Resource
	public void setDepartmentDao(IDepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}
	@Resource
	public void setDocumentDao(IDocumentDao documentDao) {
		this.documentDao = documentDao;
	}
	@Resource
	public void setUserDocumentDao(IUserDocumentDao userDocumentDao) {
		this.userDocumentDao = userDocumentDao;
	}

	@Override
	public void add(Document doc, Integer[] depIds, AttachDto ad) throws IOException {
		//添加公文
		doc.setCreateDate(new Date());
		doc.setUser(SystemContext.getLoginUser());
		documentDao.add(doc);
		//添加userDocument
		UserDocument ud = null;
		List<User> users = null;
		for(Integer i:depIds){
			users = userDao.list("select new User(u.id) from User u where u.department.id=?", i);
			for(User u :users){
				ud = new UserDocument();
				ud.setDocument(doc);
				ud.setIsRead(-1);
				ud.setType(1);
				ud.setUser(u);
				userDocumentDao.add(ud);
			}
		}
		//添加DepDocument
		DepDocument dd = null;
		for(int i:depIds){
			dd = new DepDocument();
			dd.setDepartment(new Department(i));
			dd.setDocument(doc);
			documentDao.addObject(dd);
		}
		//添加附件
		AttachUtil.addAttach(ad, null, doc, attachmentDao);
		
	}
	
	@Override
	public void delete(int id) {
		AttachUtil.deleteDocumentAttach(id, attachmentDao);
		documentDao.deleteSendDoc(id);
	}
	
	@Override
	public void updateIsRead(int docId) {
		int userId = SystemContext.getLoginUser().getId();
		UserDocument ud = (UserDocument) userDocumentDao.queryByHql("select new UserDocument(ud.id,id.isRead) from UserDocument ud where ud.document.id=? and ud.user.id=?", new Object[]{docId,userId});
		int isRead = ud.getIsRead();
		if(isRead!=1){
			userDocumentDao.updateIsRead(ud.getId());
		}
	}
	
	@Override
	public Document load(int id) {
		return documentDao.load(Document.class, id);
	}

	@Override
	public Pager<Document> findSendDocument(String con) {
		int userId = SystemContext.getLoginUser().getId();
		String hql = "select new Document(doc.id,doc.title,doc.content,doc.createDate) from Document doc  where doc.user.id=?";
		if(con==null||"".equals(con.trim())){
			hql += " order by doc.createDate desc";
			return documentDao.find(hql, userId);
		}else{
			hql += " and (doc.title like ? or doc.content like ?) order by doc.createDate desc";
			return documentDao.find(hql, new Object[]{userId,"%"+con+"%","%"+con+"%"});
		}
	}

	@Override
	public Pager<UserDocument> findReceiveDocument(String con,int isRead,int depId) {
		int userId = SystemContext.getLoginUser().getId();
		String hql = "from UserDocument ud left join fetch ud.document left join fetch ud.document.user left join fetch ud.document.user.department where ud.user.id=?";
		if(isRead==1){
			hql += " and ud.isRead=1";
		}else if(isRead==-1){
			hql += " and ud.isRead=-1";
		}
		if(depId>0){
			hql += " and ud.document.user.department.id="+depId;
		}
		if(con==null||"".equals(con.trim())){
			hql += " order by ud.document.createDate desc";
			return userDocumentDao.find(hql, userId);
		}else{
			hql += " and (ud.document.title like ? or ud.document.content like ?) order by ud.document.createDate desc";
			return userDocumentDao.find(hql, new Object[]{userId,"%"+con+"%","%"+con+"%"});
		}
	}
	@Override
	public List<Attachment> getDocAttach(int docId) {
		return AttachUtil.listAttachByDocId(docId, attachmentDao);
	}
	@Override
	public List<Department> getCanSendDeps() {
		int depId = SystemContext.getLoginUser().getDepartment().getId();
		return departmentDao.list("select ds.toDep from DepScope ds left join ds.toDep where ds.depId=?", depId);
	}
	
	@Override
	public List<Department> getSendedDepsByDocId(int docId) {
		List<Department> ls = departmentDao.list("select dd.department from DepDocument dd left join dd.department where dd.document.id=?", docId);
		return ls;
	}

}
