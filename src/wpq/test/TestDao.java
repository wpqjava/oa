package wpq.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import wpq.dao.IDepScopeDao;
import wpq.dao.IDepartmentDao;
import wpq.dao.IUserDao;
import wpq.dao.UserDao;
import wpq.model.DepScope;
import wpq.model.Department;
import wpq.model.Pager;
import wpq.model.SystemContext;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class TestDao {
	@Resource
	private IDepartmentDao departmentDao;
	@Resource
	private IDepScopeDao depScopeDao;
	@Resource
	private IUserDao userDao;
	
	

	@Test
	public void testAddDep(){
		Department dep = new Department();
		dep.setName("待修改");
		departmentDao.add(dep);
	}
	
	@Test
	public void testAddDelete(){
		Department dep = new Department();
		dep.setId(5);
		departmentDao.delete(dep);
	}
	
	@Test
	public void testAddUpdate(){
		Department dep = new Department();
		dep.setId(7);
		dep.setName("网络中心");
		departmentDao.delete(dep);
	}
	
	@Test
	public void testAddLoad(){
		Department dep = departmentDao.load(Department.class, 1);
		System.out.println(dep.getName());
	}
	
	@Test
	public void testAddList(){
		List<Department> ls = departmentDao.list("from Department");
		for(Department d:ls){
			System.out.println(d.getName());
		}
	}
	
	@Test
	public void testAddFind(){
		SystemContext.setPageOffset(0);
		SystemContext.setPageSize(3);
		Pager<Department> pager = departmentDao.find("from Department");
		List<Department> ls = pager.getDatas();
		for(Department d:ls){
			System.out.println(d.getName());
		}
	}
	
	@Test
	public void testAddDS(){
		DepScope ds = new DepScope();
		ds.setDepId(1);
		ds.setToDep(new Department(2));
		depScopeDao.add(ds);
	}
	
	@Test
	public void testAddDSs(){
		List<Integer> ls = new ArrayList<Integer>();
		ls.add(8);
	}
	
	@Test
	public void testdeleteDSs(){
		List<Integer> ls = new ArrayList<Integer>();
		ls.add(4);
	}
	
	@Test
	public void testSetDSs(){
		List<Integer> toAdd = new ArrayList<Integer>();
		List<Integer> toDelete = new ArrayList<Integer>();
		toAdd.add(8);
		toDelete.add(4);
	}
	
	@Test
	public void testlistSendMail(){
		List<String> ls = userDao.listEmails(new Integer[]{new Integer(8),new Integer(9),new Integer(10)});
		for(String s:ls){
			System.out.println(s);
		}
	}
}
