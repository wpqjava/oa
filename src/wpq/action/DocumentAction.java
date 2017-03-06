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
import wpq.model.Document;
import wpq.service.IDepartmentService;
import wpq.service.IDocumentService;
import wpq.util.ActionUtil;
@Controller("documentAction")
@Scope("prototype")
public class DocumentAction extends ActionSupport implements ModelDriven<Document> {
	private Document document;
	private int isRead;
	private int depId;
	private String con;
	private Integer[] depIds;
	private File[] attach;
	private String[] attachContentType;
	private String[] attachFileName;
	
	
	private IDocumentService documentService;
	private IDepartmentService departmentService;
	
	
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
	public String getCon() {
		return con;
	}
	public void setCon(String con) {
		this.con = con;
	}
	public Document getDocument() {
		return document;
	}
	public void setDocument(Document document) {
		this.document = document;
	}
	public int getIsRead() {
		return isRead;
	}
	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}
	public int getDepId() {
		return depId;
	}
	public void setDepId(int depId) {
		this.depId = depId;
	}
	public Integer[] getDepIds() {
		return depIds;
	}
	public void setDepIds(Integer[] depIds) {
		this.depIds = depIds;
	}
	@Resource
	public void setDocumentService(IDocumentService documentService) {
		this.documentService = documentService;
	}
	@Resource
	public void setDepartmentService(IDepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	@Override
	public Document getModel() {
		if(document==null) document = new Document();
		return document;
	}
	
	public String listSend(){
		ActionContext.getContext().put("pager", documentService.findSendDocument(con));
		return SUCCESS;
	}
	
	public String listReceive(){
		ActionContext.getContext().put("deps", departmentService.listAll());
		ActionContext.getContext().put("pager", documentService.findReceiveDocument(con, isRead,depId));
		return SUCCESS;
	}
	
	public String delete(){
		documentService.delete(document.getId());
		ActionContext.getContext().put("url", "/document_listSend");
		return ActionUtil.REDIRECT;
	}
	
	public String showSend() throws IllegalAccessException, InvocationTargetException{
		Document d = documentService.load(document.getId());
		BeanUtils.copyProperties(document, d);
		ActionContext.getContext().put("attaches", documentService.getDocAttach(document.getId()));
		ActionContext.getContext().put("sendedDeps", documentService.getSendedDepsByDocId(document.getId()));
		return SUCCESS;
	}
	
	public String showReceive() throws IllegalAccessException, InvocationTargetException{
		Document d = documentService.load(document.getId());
		BeanUtils.copyProperties(document, d);
		documentService.updateIsRead(document.getId());
		ActionContext.getContext().put("attaches", documentService.getDocAttach(document.getId()));
		return SUCCESS;
	}
	
	public String addInput(){
		ActionContext.getContext().put("canSendDeps", documentService.getCanSendDeps());
		return SUCCESS;
	}
	
	public void validateAdd(){
		if(document.getTitle()==null||"".equals(document.getTitle().trim())){
			this.addFieldError("title","公文标题不能为空");
		}
		if(document.getContent()==null||"".equals(document.getContent().trim())){
			this.addFieldError("content","公文正文不能为空");
		}
		if(depIds.length<1){
			this.addFieldError("depIds", "必须选择发文部门");
		}
		if(this.hasFieldErrors()){
			addInput();
		}
	}
	
	public String add() throws IOException{
		if(attach!=null&&attach.length>0){
			documentService.add(document, depIds, new AttachDto(attach, attachContentType, attachFileName));
		}else{
			documentService.add(document, depIds, new AttachDto(false));
		}
		ActionContext.getContext().put("url","/document_listSend");
		return ActionUtil.REDIRECT;
	}
}
