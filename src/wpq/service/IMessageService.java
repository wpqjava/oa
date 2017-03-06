package wpq.service;

import java.io.IOException;
import java.util.List;

import wpq.dto.AttachDto;
import wpq.model.Attachment;
import wpq.model.Message;
import wpq.model.Pager;
import wpq.model.User;
import wpq.model.UserMessage;

public interface IMessageService {
	public void add(Message msg,Integer[] ids,AttachDto ad) throws IOException;
	
	public void deleteSendMsg(int mid);
	
	public void deteleReceiveMsg(int mid);
	
	public Message loadSend(int id);
	
	public Message loadReceive(int id,int isRead);
	
	public List<User> getCanSendUsers(int userId);
	
	public List<Attachment> listAttachmentByMsgId(int msgId);
	
	public Pager<Message> findSendMsg(String con);
	
	public Pager<UserMessage> findReceiveMsg(String con,int isRead);
}
