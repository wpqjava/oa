package wpq.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import wpq.dao.IAttachmentDao;
import wpq.dao.IMessageDao;
import wpq.dao.IUserDao;
import wpq.dao.IUserMessageDao;
import wpq.dto.AttachDto;
import wpq.model.Attachment;
import wpq.model.Mail;
import wpq.model.Message;
import wpq.model.Pager;
import wpq.model.SystemContext;
import wpq.model.User;
import wpq.model.UserEmail;
import wpq.model.UserMessage;
import wpq.util.AttachUtil;
@Service("messageService")
public class MessageService implements IMessageService {
	private IUserDao userDao;
	private IMessageDao messageDao;
	private IUserMessageDao userMessageDao;
	private JavaMailSender mailSender;
	private TaskExecutor taskExecutor;
	private IAttachmentDao attachmentDao;
	@Resource
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	@Resource
	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
	@Resource
	public void setUserMessageDao(IUserMessageDao userMessageDao) {
		this.userMessageDao = userMessageDao;
	}
	@Resource
	public void setAttachmentDao(IAttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}
	@Resource
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	@Resource
	public void setMessageDao(IMessageDao messageDao) {
		this.messageDao = messageDao;
	}

	@Override
	public void add(Message msg, Integer[] ids,AttachDto ad) throws IOException {
		//添加信件
		msg.setCreateDate(new Date());
		msg.setUser(SystemContext.getLoginUser());
		messageDao.add(msg);
		//添加关联表
		List<User> users = userDao.loadByIds(ids);
		UserMessage um = null;
		for(User u :users){
			um = new UserMessage();
			um.setIsRead(-1);
			um.setMessage(msg);
			um.setUser(u);
			userMessageDao.add(um);
		}
		//添加附件
		String[] newNames = AttachUtil.addAttach(ad, msg, null, attachmentDao);
		//发送邮件
		List<String> toEmails = userDao.listEmails(ids);
		UserEmail ue = (UserEmail) userMessageDao.queryByHql("select ue from UserEmail ue left join ue.user where ue.user.id=?", SystemContext.getLoginUser().getId());
		taskExecutor.execute(new Mail(msg,ad,ue,toEmails,newNames,mailSender));
	}
	

	@Override
	public void deleteSendMsg(int mid) {
		AttachUtil.deleteMessageAttach(mid, attachmentDao);
		messageDao.deleteSendMsg(mid);
	}
	
	
	@Override
	public void deteleReceiveMsg(int mid) {
		int uid = SystemContext.getLoginUser().getId();
		messageDao.deleteReceiveMsg(uid, mid);
	}
	
	@Override
	public Pager<Message> findSendMsg(String con) {
		int uid = SystemContext.getLoginUser().getId();
		String hql = "select new Message(msg.id,msg.title,msg.contend,msg.createDate) from Message msg where msg.user.id=?";
		if(con==null||"".equals(con.trim())){
			hql += " order by msg.createDate desc";
			return messageDao.find(hql,new Object[]{uid});
		}else{
			hql +=" and (msg.title like ? or msg.contend like ?) order by msg.createDate desc";
			return messageDao.find(hql, new Object[]{uid,"%"+con+"%","%"+con+"%"});
		}
		
	}
	@Override
	public Pager<UserMessage> findReceiveMsg(String con,int isRead) {
		Pager<Object> po = null;
		int uid = SystemContext.getLoginUser().getId();
		if(isRead==0){
			String hql = "select um from UserMessage um left join um.message left join um.message.user left join um.message.user.department where um.user.id=?";
			if(con==null||"".equals(con.trim())){
				hql += " order by um.message.createDate desc"; 
				po = messageDao.findObject(hql,new Object[]{uid});
			}else{
				hql +=" and (um.message.title like ? or um.message.contend like ?) order by um.message.createDate desc";
				po = messageDao.findObject(hql, new Object[]{uid,"%"+con+"%","%"+con+"%"});
			}
		}else{
			String hql = "select um from UserMessage um left join um.message left join um.message.user left join um.message.user.department where um.isRead="+isRead+" and um.user.id=?";
			if(con==null||"".equals(con.trim())){
				hql += " order by um.message.createDate desc"; 
				po = messageDao.findObject(hql,new Object[]{uid});
			}else{
				hql +=" and (um.message.title like ? or um.message.contend like ?) order by um.message.createDate desc";
				po = messageDao.findObject(hql, new Object[]{uid,"%"+con+"%","%"+con+"%"});
			}
		}
		return po2pum(po);
	}
	
	
	private Pager<UserMessage> po2pum(Pager<Object> po){
		Pager<UserMessage> pum = new Pager<UserMessage>();
		List<UserMessage> ums = new ArrayList<UserMessage>();
		List<Object> os = po.getDatas();
		for(Object o:os){
			UserMessage um = (UserMessage)o;
			ums.add(um);
		}
		pum.setDatas(ums);
		pum.setPageOffset(po.getPageOffset());
		pum.setPageSize(po.getPageSize());
		pum.setTotalRecord(po.getTotalRecord());
		return pum;
	}
	
	@Override
	public Message loadSend(int id) {
		return messageDao.load(Message.class, id);
	}
	@Override
	public Message loadReceive(int id,int isRead) {
		if(isRead!=1){
			int userId = SystemContext.getLoginUser().getId();
			messageDao.updateIsRead(id, userId);
		}
		return messageDao.load(Message.class, id);
	}
	@Override
	public List<User> getCanSendUsers(int userId) {
		return userDao.loadCanSendUserByUserId(userId);	
	}
	@Override
	public List<Attachment> listAttachmentByMsgId(int msgId) {
		return AttachUtil.listAttachByMsgd(msgId, attachmentDao);
	}

}
