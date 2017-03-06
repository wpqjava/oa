package wpq.test;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import wpq.dto.AttachDto;
import wpq.model.Department;
import wpq.model.Document;
import wpq.model.Message;
import wpq.model.Pager;
import wpq.model.SystemContext;
import wpq.model.User;
import wpq.model.UserDocument;
import wpq.model.UserMessage;
import wpq.service.IDepartmentService;
import wpq.service.IDocumentService;
import wpq.service.IMessageService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class TestService {
	@Resource
	private IDepartmentService departmentService;
	
	@Resource
	private IMessageService messageService;
	@Resource
	private IDocumentService documentService;
	
	

	@Test
	public void testAddDep(){
		Department dep = new Department();
		dep.setName("财务处");
		departmentService.add(dep);
	}
	
	/*@Test
	public void testAddDelete(){
		Department dep = new Department();
		dep.setId(5);
		departmentService.delete(dep);
	}*/
	
	
	@Test
	public void testAddList(){
		List<Department> ls = departmentService.listAll();
		for(Department d:ls){
			System.out.println(d.getName());
		}
	}
	
	@Test
	public void testSetDS(){
		departmentService.setDepScope(1, new int[]{2,3,4,8,30});
	}
	
	@Test
	public void testListByDepId(){
		List<Department> ls = departmentService.getInformableDepByDepId(1);
		for(Department d:ls){
			System.out.println(d.getName());
		}
	}
	
	@Test
	public void testdeleteValidateNum(){
		departmentService.delete(1);
	}
	
	@Test
	public void testAddMsg() throws IOException{
		Message msg = new Message();
		SystemContext.setLoginUser(new User(8));
		msg.setTitle("胡孔");
		msg.setContend("胡孔上马");
		messageService.add(msg, new Integer[]{16,22,28,29,115},new AttachDto(false));
	}
	
	@Test
	public void testDeleteReceiveMsg(){
		SystemContext.setLoginUser(new User(9));
		messageService.deteleReceiveMsg(120);
	}
	
	@Test
	public void testDeleteSendMsg(){
		messageService.deleteSendMsg(132);
	}
	
	@Test
	public void testFindSendMsg(){
		SystemContext.setLoginUser(new User(8));
		SystemContext.setPageOffset(0);
		SystemContext.setPageSize(10);
		Pager<Message> pager = messageService.findSendMsg("唐僧");
		for(Message m :pager.getDatas()){
			System.out.println(m.getContend());
		}
	}
	
	@Test
	public void testFindReceiveMsg(){
		SystemContext.setLoginUser(new User(16));
		SystemContext.setPageOffset(0);
		SystemContext.setPageSize(10);
		Pager<UserMessage> pager = messageService.findReceiveMsg("八戒",0);
		List<UserMessage> ls = pager.getDatas();
		for(UserMessage um:ls){
			System.out.println(um.getMessage().getTitle()+";"+um.getIsRead());
		}
	}
	
	@Test
	public void testLoadReceive(){
		SystemContext.setLoginUser(new User(115));
		Message msg = messageService.loadReceive(159, -1);
		System.out.println(msg.getContend());
	}
	
	@Test
	public void testGetCanSendUsers(){
		List<User> ls = messageService.getCanSendUsers(21);
		for(User u :ls){
			System.out.println(u.getId()+","+u.getNickname());
		}
	}
	
	@Test
	public void testAddDocument() throws IOException{
		SystemContext.setLoginUser(new User(8));
		Document d = new Document();
		d.setTitle("test05");
		d.setContent("test05");
		documentService.add(d, new Integer[]{3}, new AttachDto(false));
	}
	
	@Test
	public void testDeleteDocument(){
		documentService.delete(320);
	}
	
	@Test
	public void testFindSendDocument(){
		SystemContext.setPageOffset(0);
		SystemContext.setPageSize(15);
		SystemContext.setLoginUser(new User(8));
		List<Document> ls = documentService.findSendDocument("02").getDatas();
		for(Document d:ls){
			System.out.println(d.getTitle());
		}
	}
	
	@Test
	public void testFindReceiveDocument(){
		SystemContext.setPageOffset(0);
		SystemContext.setPageSize(15);
		SystemContext.setLoginUser(new User(19));
		List<UserDocument> ls = documentService.findReceiveDocument(null,1,0).getDatas();
		for(UserDocument d:ls){
			System.out.println(d.getDocument().getTitle()+","+d.getIsRead());
		}
	}
	
	@Test
	public void testUpdateIsReadDocument(){
		SystemContext.setLoginUser(new User(22));
		documentService.updateIsRead(358);
	}
	
	@Test
	public void testCanSendDeps(){
		User u = new User();
		u.setId(8);
		u.setDepartment(new Department(1));
		SystemContext.setLoginUser(u);
		List<Department> ls =documentService.getCanSendDeps();
		for(Department d:ls){
			System.out.println(d.getName());
		}
	}
	
	@Test
	public void testMail(){
		SystemContext.setLoginUser(new User(8));
		try {
			messageService.add(null, null,null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
