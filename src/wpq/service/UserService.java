package wpq.service;

import javax.annotation.Resource;

import org.dom4j.DocumentException;
import org.springframework.stereotype.Service;

import wpq.dao.IDepartmentDao;
import wpq.dao.IUserDao;
import wpq.dao.IUserEmailDao;
import wpq.model.Department;
import wpq.model.Pager;
import wpq.model.SystemContext;
import wpq.model.User;
import wpq.model.UserEmail;
@Service("userService")
public class UserService implements IUserService {
	private IUserDao userDao;
	private IUserEmailDao userEmailDao;
	private IDepartmentDao departmentDao;
	@Resource
	public void setUserEmailDao(IUserEmailDao userEmailDao) {
		this.userEmailDao = userEmailDao;
	}

	@Resource
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	
	@Resource
	public void setDepartmentDao(IDepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	@Override
	public void add(User user, int depId) {
		Department dep = departmentDao.load(Department.class, depId);
		user.setDepartment(dep);
		userDao.add(user);
	}

	@Override
	public void delete(int id) {
		// TODO 删除相关信息
		User user = new User();
		user.setId(id);
		userDao.delete(user);
	}

	@Override
	public void update(User user) {
		userDao.update(user);
	}

	@Override
	public User load(int id) {
		return userDao.load(User.class, id);
	}

	@Override
	public Pager<User> findByDepId(int depId) {
		Pager<User> pager = null;
		if(depId<=0){
			pager = userDao.find("select u from User u left join u.department");
		}else{
			pager = userDao.find("select u from User u left join u.department where u.department.id=?", depId);
		}
		return pager;
	}

	@Override
	public User login(String username, String password) throws DocumentException {
		User u = userDao.loadByUsername(username);
		if(u==null)throw new DocumentException("用户信息输入有误");
		if(!u.getPassword().equals(password))throw new DocumentException("用户信息输入有误");
		return u;
	}

	@Override
	public void addUe(UserEmail ue) {
		User u = SystemContext.getLoginUser();
		ue.setUser(u);
		userEmailDao.add(ue);
	}

	@Override
	public UserEmail loadUeByUserId(int id) {
		return userEmailDao.loadByUseId(id);
	}
	
	@Override
	public void updateUe(UserEmail ue) {
		ue.setUser(SystemContext.getLoginUser());
		userEmailDao.update(ue);
	}
}
