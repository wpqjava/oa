package wpq.dao;


import org.springframework.stereotype.Repository;

import wpq.model.Message;
import wpq.model.UserMessage;
@Repository("messageDao")
public class MessageDao extends BaseDao<Message> implements IMessageDao {
	
	

	@Override
	public void deleteReceiveMsg(int uid, int mid) {
		getSession().createQuery("delete UserMessage where user.id=?0 and message.id=?1").setParameter("0", uid).setParameter("1", mid).executeUpdate();
	}

	@Override
	public void deleteSendMsg(int mid) {
		getSession().createQuery("delete UserMessage where message.id=?0").setParameter("0", mid).executeUpdate();
		Message m = new Message();
		m.setId(mid);
		this.delete(m);
	}

	@Override
	public void updateIsRead(int id,int userId) {
		/*if(isRead!=1){
			getSession().createQuery("update UserMessage set isRead=1 where message.id=? and user.id=?").setParameter(0, id).setParameter(1, userId).executeUpdate();
		}*/
		getSession().createQuery("update UserMessage set isRead=1 where message.id=?0 and user.id=?1").setParameter("0", id).setParameter("1", userId).executeUpdate();
	}

}
