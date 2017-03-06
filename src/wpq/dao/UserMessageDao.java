package wpq.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import wpq.model.Pager;
import wpq.model.UserMessage;
@Repository("userMessageDao")
public class UserMessageDao extends BaseDao<UserMessage> implements IUserMessageDao {

}
