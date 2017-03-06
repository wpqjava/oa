package wpq.dao;

import java.util.List;

import wpq.model.User;

public interface IUserDao extends IBaseDao<User> {
	public long countNumByDepId(int did);
	
	public User loadByUsername(String username);
	/**
	 * 通过一组id获得用户列表用于发送MSG
	 * @param ids 
	 * @return
	 */
	public List<User> loadByIds(Integer[] ids);
	
	public List<User> loadCanSendUserByUserId(int userId);
	/**
	 * 通过一组ID获得对应用户的EMAIL
	 * @param ids
	 * @return
	 */
	public List<String> listEmails(Integer[] ids);
}
