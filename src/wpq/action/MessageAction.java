package wpq.action;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import wpq.dto.AttachDto;
import wpq.model.Message;
import wpq.model.SystemContext;
import wpq.service.IMessageService;
import wpq.util.ActionUtil;

@Controller("messageAction")
@Scope("prototype")
public class MessageAction extends ActionSupport implements ModelDriven<Message> {
	private Message message;
	private IMessageService messageService;
	private int isRead;
	private String con;
	private Integer[] sendUserIds;
	private File[] attach;
	private String[] attachContentType;
	private String[] attachFileName;

	@Override
	public Message getModel() {
		if (message == null)
			message = new Message();
		return message;
	}

	public File[] getAttach() {
		return attach;
	}

	public void setAttach(File[] attach) {
		this.attach = attach;
	}

	public String[] getAttachContentType() {
		return attachContentType;
	}

	public void setAttachContentType(String[] attachContentType) {
		this.attachContentType = attachContentType;
	}

	public String[] getAttachFileName() {
		return attachFileName;
	}

	public void setAttachFileName(String[] attachFileName) {
		this.attachFileName = attachFileName;
	}

	public Integer[] getSendUserIds() {
		return sendUserIds;
	}

	public void setSendUserIds(Integer[] sendUserIds) {
		this.sendUserIds = sendUserIds;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	public String getCon() {
		return con;
	}

	public void setCon(String con) {
		this.con = con;
	}

	@Resource
	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	public String addInput() {
		int userId = SystemContext.getLoginUser().getId();
		ActionContext.getContext().put("canSendUsers", messageService.getCanSendUsers(userId));
		return SUCCESS;
	}

	public String add() throws IOException {
		if(attach==null||attach.length<=0){
			messageService.add(message, sendUserIds,new AttachDto(false));
		}else{
			messageService.add(message, sendUserIds,new AttachDto(attach, attachContentType, attachFileName));
		}
		ActionContext.getContext().put("url", "/message_listSend");
		return ActionUtil.REDIRECT;
	}

	public void validateAdd() {
		if (message.getTitle() == null || "".equals(message.getTitle().trim())) {
			this.addFieldError("title", "标题不能为空");
		}
		if (sendUserIds == null || sendUserIds.length < 1) {
			this.addFieldError("sendUserIds", "必须选择要发送的用户");
		}
		if (this.hasFieldErrors()) {
			addInput();
		}
	}

	public String listSend() {
		ActionContext.getContext().put("pager", messageService.findSendMsg(con));
		return SUCCESS;
	}

	public String listReceive() {
		ActionContext.getContext().put("pager", messageService.findReceiveMsg(con, isRead));
		return SUCCESS;
	}

	public String deleteSend() {
		messageService.deleteSendMsg(message.getId());
		ActionContext.getContext().put("url", "message_listSend");
		return ActionUtil.REDIRECT;
	}

	public String deleteReceive() {
		messageService.deteleReceiveMsg(message.getId());
		ActionContext.getContext().put("url", "message_listReceive");
		return ActionUtil.REDIRECT;
	}

	public String showReceive() throws IllegalAccessException, InvocationTargetException {
		Message msg = messageService.loadReceive(message.getId(), isRead);
		ActionContext.getContext().put("attaches", messageService.listAttachmentByMsgId(message.getId()));
		BeanUtils.copyProperties(message, msg);
		return SUCCESS;
	}

	public String showSend() throws IllegalAccessException, InvocationTargetException {
		Message msg = messageService.loadSend(message.getId());
		ActionContext.getContext().put("attaches", messageService.listAttachmentByMsgId(message.getId()));
		BeanUtils.copyProperties(message, msg);
		return SUCCESS;
	}

}
