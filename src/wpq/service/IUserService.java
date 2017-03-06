package wpq.service;

import org.dom4j.DocumentException;

import wpq.model.Pager;
import wpq.model.User;
import wpq.model.UserEmail;

public interface IUserService {
	public void add(User user,int depId);
	
	public void addUe(UserEmail ue);
	
	public void delete(int id);
	
	public void update(User user);
	public void updateUe(UserEmail ue);
	public User load(int id);
	
	public UserEmail loadUeByUserId(int id);
	
	public Pager<User> findByDepId(int depId);
	
	public User login(String username,String password) throws DocumentException;
}
