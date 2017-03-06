package wpq.util;

import java.io.File;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import wpq.dao.IAttachmentDao;
import wpq.dto.AttachDto;
import wpq.model.Attachment;
import wpq.model.Document;
import wpq.model.Message;

public class AttachUtil {

	public static String[] addAttach(AttachDto ad,Message msg,Document doc,IAttachmentDao attachmentDao) throws IOException{
		if(ad.isHasAttach()){
			File[] files = ad.getAttach();
			String[] cts = ad.getAttachContentType();
			String[] fns = ad.getAttachFileName();
			String[] newNames = new String[files.length];
			for(int i=0;i<files.length;i++){
				Attachment a = new Attachment();
				File f = files[i];
				String name = fns[i];
				a.setContentType(cts[i]);
				a.setCreateDate(new Date());
				if(msg==null&&doc!=null){
					a.setDocument(doc);
				}else{
					a.setMessage(msg);
				}
				a.setOldName(name);
				a.setSize(f.length());
				a.setType(FilenameUtils.getExtension(name));
				String newName = getFileNewName(name);
				newNames[i] = newName;
				a.setNewName(newName);
				FileUtils.copyFile(f, new File("D:/CodeSoftware/STS/workspace/oa_document01/WebContent/upload/"+newName));
				attachmentDao.add(a);
			}
			return newNames;
		}
		return null;
	}
	
	private static String getFileNewName(String name){
		String n = new Long(new Date().getTime()).toString();
		return n+"."+FilenameUtils.getExtension(name);
	}
	
	public static void deleteMessageAttach(int msgId,IAttachmentDao attachmentDao){
		List<Attachment> attaches = attachmentDao.list("select new Attachment(a.newName) from Attachment a where a.message.id=?", msgId);
		if(attaches!=null){
			for(Attachment a : attaches){
				String name = "D:/CodeSoftware/STS/workspace/oa_document01/WebContent/upload/"+a.getNewName();
				File f = new File(name);
				f.delete();
			}
			attachmentDao.deleteByMsgId(msgId);
		}
		attachmentDao.clearSeesion();
	}
	
	public static void deleteDocumentAttach(int docId,IAttachmentDao attachmentDao){
		List<Attachment> attaches = attachmentDao.list("select new Attachment(a.newName) from Attachment a where a.document.id=?", docId);
		if(attaches!=null){
			for(Attachment a : attaches){
				String name = "D:/CodeSoftware/STS/workspace/oa_document01/WebContent/upload/"+a.getNewName();
				File f = new File(name);
				f.delete();
			}
			attachmentDao.deleteByMsgId(docId);
		}
		attachmentDao.clearSeesion();
	}
	
	public static List<Attachment> listAttachByMsgd(int msgId,IAttachmentDao attachmentDao){
		return attachmentDao.list("from Attachment where message.id=?", msgId);
	}
	
	public static List<Attachment> listAttachByDocId(int docId,IAttachmentDao attachmentDao){
		return attachmentDao.list("from Attachment where document.id=?", docId);
	}
}
