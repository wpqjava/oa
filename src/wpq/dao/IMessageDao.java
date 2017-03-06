package wpq.dao;

import wpq.model.Message;

public interface IMessageDao extends IBaseDao<Message> {
	public void updateIsRead(int id,int userId);
	public void deleteReceiveMsg(int uid,int mid);
	public void deleteSendMsg(int mid);
}	
