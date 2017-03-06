package wpq.test;

import java.util.Random;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import wpq.model.Department;
import wpq.model.User;
import wpq.service.IUserService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class TestUserService {
	Random ra = new Random();
	@Resource
	private IUserService userService;
	
	private  String[] firstName = new String[]{"赵","钱","孙","李","周","吴",
			"郑","王","冯","陈","楚","魏","蒋","沈","韩","杨"};
	
	private  String[] lastName = new String[]{"安邦","宾白","才捷","德本","飞昂","刚豪","晗昱","智琳",
			"晓洁","婷昱","婉婷","新颖","玉妍","月玲","炜婷","翰颖","冰洁"};
	
	public  String getName(){
		String name = null;
		name=firstName[ra.nextInt(firstName.length)]+lastName[ra.nextInt(lastName.length)];
		return name;
	}
	
	@Test
	public void testAdd(){
		User u = null;
		for(int i=1;i<=100;i++){
			u = new User();
			u.setUsername("wpq"+i);
			u.setPassword("wpq"+i);
			u.setNickname(getName());
			u.setType(1);
			u.setEmail(u.getUsername()+"@163.com");
			userService.add(u, ra.nextInt(7)+1);
		}
		/*u=new User();
		u = new User();
		u.setUsername("admin");
		u.setPassword("admin");
		u.setNickname("admin");
		u.setType(0);
		u.setEmail("admin@admin.com");
		userService.add(u, 1);*/
	}
	
	

	
}
